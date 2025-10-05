using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Security;
using System.Text;
using System.Transactions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Media;
using System.Reflection;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Text.RegularExpressions;
using System;
using System.Windows.Media.Animation;
using System.Collections;

namespace ChemEditorApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class Main : Window
    {
        /// <summary>
        /// Returns a value which determines if any changes have been made to the <see cref="QuestionModel"/> array since the last save.
        /// <para>
        /// Setting this value to <see langword="true"/> will save the array to the file where the questions were initially loaded from.
        /// </para>
        /// </summary>
        public bool Saved
        {
            get => saved;
            set
            {
                if (value && saved) return;
                saved = value;

                if (saved) SaveQuestions();
                Title = "Editeaza intrebari" + (!saved ? " (NESALVAT)" : "");
                saveBtn.Background = saved ? SystemColors.ControlBrush : new SolidColorBrush(Colors.PaleVioletRed);
            }
        }

        #region Fields

        Timeline<(int Count, QuestionTimelineData Question)> timeline;
        Dictionary<KeyGesture, ExecutedRoutedEventHandler> hotkeys = new(); // manages all the hotkeys of this window
        LinkedList<QuestionModel> questions;
        LinkedListNode<QuestionModel> currentQuestion;
        List<LinkedListNode<QuestionModel>> questionArr;
        IInputElement[] selectionDict;
        StreamWriter stream, descStream;
        Stream dataFileStream;
        string path, descPath;
        int autoSaveCalls = 0;

        bool saved = true, advancing = false, doTimelineChanges = false;

        #endregion

        public Main()
        {
            InitializeComponent();

            // declare the selection dictionary. this is used when (de)serializing to know which element has/had focus.

            selectionDict = [questionBox, ans0c, ans1c, ans2c, ans3c, opt0, opt1, opt2, opt3];

            // declare all hotkey handlers and load them.

            hotkeys.Add(new KeyGesture(Key.Delete), (_, _) => { if (IsActive) DeleteCurrentQuestion(true); });
            hotkeys.Add(new KeyGesture(Key.S, ModifierKeys.Control), (_, _) => { if (IsActive) Saved = true; });
            hotkeys.Add(new KeyGesture(Key.N, ModifierKeys.Control), (_, _) => { if (IsActive) AddTemplateQuestion(); });
            //hotkeys.Add(new KeyGesture(Key.Z, ModifierKeys.Control), (_, _) => UndoRedo(false));
            //hotkeys.Add(new KeyGesture(Key.Z, ModifierKeys.Control | ModifierKeys.Shift), (_, _) => UndoRedo(true));
            Loaded += (_, _) =>
            {
                for (int i = 0; i < hotkeys.Count; i++)
                {
                    var hotkey = hotkeys.ElementAt(i).Key;
                    RegisterHotKey(new WindowInteropHelper(this).Handle, i, (int)hotkey.Modifiers | 0x4000, KeyInterop.VirtualKeyFromKey(hotkey.Key));
                }

                var src = PresentationSource.FromVisual(this) as HwndSource;
                src.AddHook(HotkeyHook);
            };
            Closing += (_, e) =>
            {
                if (!Saved && !autoSaveBox.IsChecked.Value)
                {
                    var message = MessageBox.Show("Intrebarile nu au fost salvate. Vrei sa fie salvate inainte de a iesi?", "Atentie!", MessageBoxButton.YesNoCancel, MessageBoxImage.Warning);

                    if (message == MessageBoxResult.Cancel) { e.Cancel = true; return; }
                    Saved = message == MessageBoxResult.Yes;
                }

                descStream?.Close();
                descStream?.Dispose();

                stream?.Close();
                stream?.Dispose();
            };

            // set autosave
            string dataDir = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + @"\DeCeEu";

            if (!Directory.Exists(dataDir))
            {
                Directory.CreateDirectory(dataDir);
                File.Create(dataDir + @"\data").Close();
            }

            var data = File.ReadAllText(dataDir + @"\data");
            autoSaveBox.IsChecked = bool.Parse(data == "" ? "false" : data);
            if (autoSaveBox.IsChecked.Value) DoAutosave();

            // load the question file.
            // i'll declare the list and array here since declaring it in the try block its scope does not extend to the whole method
            // it will never be null: either its declared, or an exception is thrown

            (LinkedList<QuestionModel>, List<LinkedListNode<QuestionModel>>) q = (null, null);
            try
            {
                q = QuestionModel.LinkedParse(File.ReadAllLines(path = App.Arguments[0].Replace('|', ' ')),
                                                  File.ReadAllLines(descPath = App.Arguments[1].Replace('|', ' ')));
            }
            catch
            {
                MessageBox.Show("A fost o problema la pornirea editorului.\nNu au fost gasite caile pentru fisierele de intrebari si descrieri.\nAcestea trebuie specificate in argumentele programului.",
                                "Eroare de initializare",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
            }

            questions = q.Item1;
            questionArr = q.Item2;
            currentQuestion = questions.First;

            File.WriteAllText(path, "");
            stream = new StreamWriter(path);

            File.WriteAllText(descPath, "");
            descStream = new StreamWriter(descPath);

            // display the first question.

            if (questions.Count == 0) AddTemplateQuestion();
            else
            {
                SetQuestionIndex(0, questions.Count);
                AdvanceQuestion(false, true);
            }

            // set the tags for every text box.

            for (int i = 0; i <= 4; i++)
            {
                var inputBox = selectionDict[i] as TextBox;
                inputBox.Tag = new TextBoxTagData(inputBox.Text, inputBox.Text.Length, true, Key.None, inputBox.Text.Length);
            }

            // set up trash button and its icon.

            trashBtn.IsEnabled = questions.Count > 1;
            (trashBtn.Content as Image).Opacity = trashBtn.IsEnabled ? 1 : 0.5;

            nextBtn.IsEnabled = questions.Count > 1;
            lastBtn.IsEnabled = questions.Count > 1;

            // set the respective click event for every button.

            saveBtn.Click += (_, _) => Saved = true;
            addBtn.Click += (_, _) => AddTemplateQuestion();
            trashBtn.Click += (_, _) => DeleteCurrentQuestion(true);
            nextBtn.Click += (_, _) => AdvanceQuestion();
            prevBtn.Click += (_, _) => AdvanceQuestion(true);
            firstBtn.Click += (_, _) => extremeMovement(true);
            lastBtn.Click += (_, _) => extremeMovement(false);

            previewBtn.PreviewMouseLeftButtonDown += (_, _) => animatePreviewHandler(false);
            previewBtn.PreviewMouseLeftButtonUp += (_, _) => animatePreviewHandler(true);

            // set the jump box's events

            jumpBox.Text = "1";
            jumpBox.ContextMenu = null;
            CommandManager.AddPreviewExecutedHandler(jumpBox, (_, e) => { if (e.Command == ApplicationCommands.Cut || e.Command == ApplicationCommands.Paste) e.Handled = true; });
            jumpBox.PreviewKeyDown += (_, e) => 
            {
                if (e.Key != Key.Return) return;

                int index = int.Parse(Regex.Replace(jumpBox.Text.TrimStart('0'), "^$", "1")) - 1;
                index = index >= questions.Count ? questions.Count - 1 : index;
                jumpBox.Text = (index + 1).ToString();

                JumpTo(index);
            };
            jumpBox.TextChanged += (_, e) =>
            {
                var change = e.Changes.First();
                if (change.AddedLength == 0) return;

                jumpBox.Text = Regex.Replace(jumpBox.Text, "[^0-9]", "");
                jumpBox.CaretIndex = jumpBox.Text.Length;
            };

            // set every q&a text box's events.

            for (int i = 0; i <= 4; i++)
            {
                // apply timeline handlers to text boxes.

                var inputBox = selectionDict[i] as TextBox;
                //inputBox.PreviewKeyDown += OnInputBoxPreviewKeyDown;
                //inputBox.TextChanged += OnInputBoxTextChanged;
                //DataObject.AddPastingHandler(inputBox, OnInputBoxDataPaste);

                if (i == 0) inputBox.TextChanged += (_, _) =>
                {
                    if (questions == null || advancing) return;

                    currentQuestion.ValueRef.Question = questionBox.Text;
                    Saved = false;
                };

                // separate question box from answer box
                
                if (i < 1) continue;

                // this handler applies to all answer boxes.
                // when changing the text, update the current question's answer options. it's not that hard.

                inputBox.TextChanged += (sender, _) =>
                {
                    if (questions == null || advancing) return;

                    currentQuestion.ValueRef.Answers[selectionDict.TakeWhile(x => x != sender).Count() - 1] = (sender as TextBox).Text;
                    Saved = false;
                };
            }

            descBox.TextChanged += (_, _) =>
            {
                if (questions == null || advancing) return;

                currentQuestion.ValueRef.Description = descBox.Text;
                Saved = false;
            };

            // set every check box's events.

            for (int i = 5; i <= 8; i++)
            {
                var checkBox = selectionDict[i] as CheckBox;

                checkBox.Click += (sender, _) =>
                {
                    if (questions == null || advancing) return;

                    var box = sender as CheckBox;
                    int index = selectionDict.TakeWhile(x => x != box).Count() - 5;

                    // check if at least 1 box is checked except the one clicked;
                    // if not, do not change the clicked one's value.

                    if (Enumerable.Range(0, 3).Where(x => x != index).Select(x => selectionDict[x + 5] as CheckBox).Aggregate(true, (t, n) => t && !n.IsChecked.Value))
                    {
                        box.IsChecked = !box.IsChecked.Value;
                        return;
                    }

                    currentQuestion.Value.Validity[index] = box.IsChecked.Value;
                    Saved = false;
                };
            }

            // set auto save check box
            autoSaveBox.Click += (sender, _) =>
            {
                Saved = autoSaveBox.IsChecked.Value;
                if (autoSaveBox.IsChecked.Value) DoAutosave();
                File.WriteAllText(dataDir + @"\data", autoSaveBox.IsChecked.Value.ToString());
            };

            // declare and start the timeline. all text boxes have been fully initialized so i do not have to worry
            // about any unexpected timeline changes after declaring this.

            //timeline = new Timeline<(int, QuestionTimelineData)>((questions.Count, SerializePage()));
            //doTimelineChanges = false;

            // the handler for the first/last buttons.
            // name comes from moving to a question found at an extreme (either beginning or end)

            SaveQuestions();
            saved = true;

            void extremeMovement(bool first)
            {
                currentQuestion = first ? questions.First : questions.Last;
                AdvanceQuestion(false, true);
            };

            void animatePreviewHandler(bool fadeOut)
            {
                var anim = new DoubleAnimation(prev.Opacity, fadeOut ? 0 : 1, TimeSpan.FromMilliseconds(250), FillBehavior.HoldEnd);
                anim.EasingFunction = new SineEase() { EasingMode = EasingMode.EaseOut };

                prev.BeginAnimation(OpacityProperty, anim);
            }
        }

        private void SaveQuestions()
        {
            // very unefficient. would've figured out how to modify each modified question
            // individually, but i'm in a time crunch
            stream.Close();
            stream.Dispose();
            File.WriteAllText(path, "");
            stream = new StreamWriter(path);

            descStream.Close();
            descStream.Dispose();
            File.WriteAllText(descPath, "");
            descStream = new StreamWriter(descPath);

            for (int i = 0; i < questions.Count; i++)
            {
                descStream.WriteLine(questionArr[i].Value.Description);
                stream.WriteLine(questionArr[i].Value.Question);

                for (int a = 0; a < 4; a++) stream.WriteLine((questionArr[i].Value.Validity[a] ? "*" : "") + questionArr[i].Value.Answers[a]);
            }
        }

        private async void DoAutosave()
        {
            autoSaveCalls++;

            while (autoSaveBox.IsChecked.Value)
            {
                Saved = true;
                await Task.Delay(5000);
                if (autoSaveCalls > 1) break;
            }

            autoSaveCalls--;
        }

        private void UndoRedo(bool redo = false)
        {
            if (!redo) timeline.Undo();
            else timeline.Redo();

            doTimelineChanges = true;
            DeserializePage(timeline.Current.Question);
            doTimelineChanges = false;
        }

        #region Page Serialization

        private QuestionTimelineData SerializePage()
        {
            int selected = -1;
            bool[] validity = currentQuestion.Value.Validity.Select(x => x).ToArray();
            TextBoxTagData[] data = new object[] { questionBox.Tag, ans0c.Tag, ans1c.Tag, ans2c.Tag, ans3c.Tag }.Cast<TextBoxTagData>().ToArray();

            var focused = FocusManager.GetFocusedElement(this);
            for (int i = 0; i < selectionDict.Length; i++) if (focused == selectionDict[i]) selected = i;

            return new QuestionTimelineData(currentQuestion.Value.QuestionIndex, selected, validity, data);
        }

        private void DeserializePage(QuestionTimelineData data)
        {
            if (data.SelectedIndex >= 0) selectionDict[data.SelectedIndex].Focus();

            // ALWAYS. SET. CARET INDEX. AFTER. TEXT.
            int caret = data.InputBoxes[0].CaretIndex;
            questionBox.Text = data.InputBoxes[0].Text;
            questionBox.CaretIndex = data.InputBoxes[0].CaretIndex = caret;

            // no way i'm not using reflection. it's just 4 elements
            for (int i = 0; i < 4; i++)
            {
                var answerBox = typeof(Main).GetField("ans" + i + "c", BindingFlags.NonPublic | BindingFlags.Public | BindingFlags.Instance)
                                            .GetValue(this) as TextBox;

                answerBox.Text = data.InputBoxes[i + 1].Text;
                answerBox.CaretIndex = data.InputBoxes[i + 1].CaretIndex;
            }

            // ok look, at least these statements are just 1 line. i know i threw myself in shit the moment i decided to not put these in arrays
            opt0.IsChecked = data.ValidityBoxes[0];
            opt1.IsChecked = data.ValidityBoxes[1];
            opt2.IsChecked = data.ValidityBoxes[2];
            opt3.IsChecked = data.ValidityBoxes[3];

            SetQuestionIndex(data.PageIndex, questions.Count);

            bool higher = data.PageIndex > currentQuestion.Value.QuestionIndex;
            for (int i = currentQuestion.Value.QuestionIndex; higher ? i < data.PageIndex : i > data.PageIndex; i += (higher ? 1 : -1)) currentQuestion = higher ? currentQuestion.Next : currentQuestion.Previous;
        }

        #endregion

        #region Question Display

        private void JumpTo(int index)
        {
            currentQuestion = questionArr[index];
            AdvanceQuestion(false, true);
        }

        private void AdvanceQuestion(bool backward = false, bool identity = false)
        {
            // kind of a sloppy solution when it comes to preventing unnecessary saving prompts
            // but since i already wasn't assed to organise the answer & question boxes better, i'll do it like this
            advancing = true;
            currentQuestion = identity ? currentQuestion : (backward ? currentQuestion.Previous : currentQuestion.Next);

            SetQuestionIndex(currentQuestion.Value.QuestionIndex, questions.Count);
            questionBox.Text = currentQuestion.Value.Question;
            descBox.Text = currentQuestion.Value.Description;

            // also could do reflection here, but i won't complicate myself
            ans0c.Text = currentQuestion.Value.Answers[0];
            ans1c.Text = currentQuestion.Value.Answers[1];
            ans2c.Text = currentQuestion.Value.Answers[2];
            ans3c.Text = currentQuestion.Value.Answers[3];

            // here too
            opt0.IsChecked = currentQuestion.Value.Validity[0];
            opt1.IsChecked = currentQuestion.Value.Validity[1];
            opt2.IsChecked = currentQuestion.Value.Validity[2];
            opt3.IsChecked = currentQuestion.Value.Validity[3];

            // here too (this is the preview screen)
            questionPrev.Text = questionBox.Text;
            ans0prev.Text = ans0c.Text;
            ans1prev.Text = ans1c.Text;
            ans2prev.Text = ans2c.Text;
            ans3prev.Text = ans3c.Text;

            // navigator buttons are controlled here
            if (currentQuestion == questions.First || currentQuestion == questions.Last)
            {
                prevBtn.IsEnabled = firstBtn.IsEnabled = !(currentQuestion == questions.First);
                nextBtn.IsEnabled = lastBtn.IsEnabled = currentQuestion == questions.First;
            }
            else
            {
                prevBtn.IsEnabled = true;
                nextBtn.IsEnabled = true;
                firstBtn.IsEnabled = true;
                lastBtn.IsEnabled = true;
            }

            advancing = false;
        }

        private void SetQuestionIndex(int index, int total, int offset = 1)
        {
            index += offset;
            questionText.Text = $"{(index < 10 ? "0" : "") + index} din {(total < 10 ? "0" : "") + total}";
        }

        #endregion

        #region Question Addition/Deletion

        private void AddTemplateQuestion()
        {
            var template = new QuestionModel(currentQuestion is null ? 0 : currentQuestion.Value.QuestionIndex, "Intrebarea vine aici", "Descrierea vine aici", [true, false, false, false], "Varianta 1", "Varianta 2", "Varianta 3", "Varianta 4");
            if (currentQuestion is null) currentQuestion = questions.AddFirst(template);
            else questions.AddAfter(currentQuestion, template);

            questionArr.Insert(template.QuestionIndex + questions.Count == 1 ? 0 : 1, questions.Count == 1 ? currentQuestion : currentQuestion.Next);

            // update indices and move on to the newly created question
            if (questions.Count > 1)
            for (LinkedListNode<QuestionModel> q = currentQuestion.Next; ; q = q.Next) 
            {
                q.ValueRef.QuestionIndex++;
                if (q == questions.Last) break;
            }
            AdvanceQuestion(false, questions.Count == 1);

            //if (doTimelineChanges) timeline.Current = (questions.Count, currentQuestion.Value);
            Saved = false;
        }

        private void DeleteCurrentQuestion(bool ensure)
        {
            if (ensure && MessageBox.Show("Sigur vrei sa stergi aceasta intrebare?", "Avertizare", MessageBoxButton.YesNo, MessageBoxImage.Warning, MessageBoxResult.No) == MessageBoxResult.No) return;

            bool first = currentQuestion == questions.First;

            // move back (or forward if there is nowhere to go back to) and delete the current question
            var current = currentQuestion;
            AdvanceQuestion(backward: !first);
            questions.Remove(current);
            questionArr.RemoveAt(current.Value.QuestionIndex);

            // update indices and reflect it in front-end
            for (LinkedListNode<QuestionModel> q = first ? currentQuestion : currentQuestion.Next; ; q = q.Next)
            {
                q.ValueRef.QuestionIndex--;
                if (q == questions.Last) break;
            }
            AdvanceQuestion(false, true);

            trashBtn.IsEnabled = questions.Count > 1;
            (trashBtn.Content as Image).Opacity = trashBtn.IsEnabled ? 1 : 0.5;

            //if (doTimelineChanges) timeline.Current = (questions.Count, currentQuestion.Value);
            Saved = false;
        }

        #endregion

        #region Timeline Events

        private void OnInputBoxTextChanged(object sender, TextChangedEventArgs e)
        {
            /* HEADER EXPLANATION
            //
            // basically; do not handle text changes if the page or timeline is changing (undos/redos).
            //
            // EXPLANATIONS:
            //
            // > the page changing means that the text box will change to another question,
            //   thus triggering this handler, and changing the current text in the timeline.
            // 
            // > a page change should constitute an entirely new current state.
            //
            // > regarding timeline changes, undoing/redoing will change the contents of the text box,
            //   thus triggering this handler. i do not know what behavior it would cause, but it is not
            //   worth to try.
            //
            // > undoing/redoing to a previous/future state should change the Tag of the text box to that state --
            //   no other unforseen changes.
            */

            if (questions == null || advancing || doTimelineChanges) return;

            // declarations

            var change = e.Changes.First();
            var box = sender as TextBox;
            var data = (TextBoxTagData)box.Tag;
            int boxIndex = selectionDict.TakeWhile(x => x != box).Count();

            /* CONDITIONS EXPLANATION
            //
            // basically; this block, if executed, will update the timeline and will append the current state of the
            //            text box to it.
            //
            // EXPLANATIONS:
            // 
            // > `EligibleForChange` can only be set by this block and the `PreviewKeyDown`
            //   event handler. It should only be true after you press Backspace for the FIRST time, OR
            //   pressing any other key immediately after pressing Backspace.
            //
            // > if `EligibleForChange` was true before running the next statement, it means it has been set manually,
            //   supposedly for a good reason; in the case of this program, typing after the default or pasted text is set
            //   should always be considered a change. thus, `EligibleForChange` should remain true.
            //
            // > if `LastTextChangeWasPasted` is true, then it will remain true until `EligibleForChange` is false.
            //   if text was pasted, `EligibleForChange` will be true then turn false. so i needed to make sure that the
            //   pasted text is its own element in the timeline. typing after pasted text, originally wouldn't pass
            //   the change conditions. thus, the need for this extra value.
            //
            // > the third part of `EligibleForChange` is the one that judges if the change in the text box should count
            //   as an actual timeline change.
            // 
            // > the terms of this judgement are as follows; a change to a text box is counted on the timeline if:
            //
            //   - backspace was pressed after a non-backspace key;
            //   - a non-backspace key was pressed after backspace;
            //   - text was not pasted
            //   - the caret is not in it's natural positon after the text was changed.
            //     the natural positions of the caret are as follows:
            //       > if text was removed, the caret should be 1 positon to the LEFT of its last position.
            //         (in other words, a single backspace)
            //
            // > the caret conditions cover most edge cases imposed by selections.
            //
            // > if a selection is deleted, the second condition is broken. (unless only 1-wide selections are removed.
            //   again, i'm not gonna bother taking care of it; you're better off using backspace)
            //
            // > to reiterate, if any of these 3 general conditions are not met, the change to the text box will not
            //   permit new changes to the timeline.
            */

            data.EligibleForChange = data.EligibleForChange ||
                                     data.LastTextChangeWasPasted || 
                                     (change.RemovedLength > 0 && change.AddedLength == 0 && data.PreviousCaretIndex - 1 != box.CaretIndex) ||
                                     (change.AddedLength > 0 && change.RemovedLength == 0 && data.PreviousCaretIndex + 1 != box.CaretIndex) ||
                                     (data.LatestKey != Key.Back && data.PreviousLatestKey == Key.Back) || (data.LatestKey == Key.Back && data.PreviousLatestKey != Key.Back);
            
            data.PreviousLatestKey = data.LatestKey;

            if (data.EligibleForChange)
            {
                // setting these tag properties will ensure that the current text and caret position are saved on the
                // timeline before setting the next timeline element.

                data.Text = box.Text;
                data.CaretIndex = box.CaretIndex;

                /* CARET PLACEMENT EXPLANATION
                //
                // > before setting the new current element, the caret of the old element should be placed at the
                // beginning of the new element.
                //
                // > the reason for this is, when undoing the new element, the caret should not go directly to the end of the previous
                // element, but to the beginning of the undone element. (for convenience)
                //
                // EXAMPLE ("abc" is the text in the text box, '|' is the caret):
                //
                // 1. without the check:
                //
                // abc| --(move caret to 1)--> a|bc --(insert "AAA")--> aAAA|bc --(undo)--> abc|
                // (as you can see, since moving the caret is not a timeline change, it will go to the end of the old fragment "abc")
                //
                // 2. with the check:
                //
                // abc| --(move caret to 1)--> a|bc --(insert "AAA")--> aAAA|bc --(undo)--> a|bc
                // (here, even though moving the caret is not a timeline change, i moved it to the beginning of the undone fragment,
                //  BEFORE actually adding the undone fragment to the timeline)
                */

                timeline.Current.Question.InputBoxes[boxIndex].CaretIndex = box.CaretIndex - (change.RemovedLength > 0 ? -(change.RemovedLength - change.AddedLength) : change.AddedLength);
                timeline.Current = (questions.Count, SerializePage());
                MessageBox.Show($"timeline current state is now: {timeline.Current.Question.InputBoxes[0].Text}\n{timeline.Current.Question.InputBoxes[1].Text}\n{timeline.Current.Question.InputBoxes[2].Text}\n{timeline.Current.Question.InputBoxes[3].Text}\n{timeline.Current.Question.InputBoxes[4].Text}");

                data.EligibleForChange = false;
            }
            else data.LastTextChangeWasPasted = false;

            data.PreviousCaretIndex = box.CaretIndex;
            box.Tag = data;

            /* DISCREET CHANGES EXPLANATION 
            //
            // discreetly change the current element's properties.
            // this will ensure that no changes are pushed on the timeline.
            //
            // no, i cannot just change `data` since `box.Tag` is a value type, and its changes will not
            // be reflected on the timeline object.
            */

            timeline.Current.Question.InputBoxes[boxIndex].CaretIndex = questionBox.CaretIndex;
            timeline.Current.Question.InputBoxes[boxIndex].Text = questionBox.Text;
            Saved = false;
        }

        public void OnInputBoxPreviewKeyDown(object sender, KeyEventArgs e)
        {
            var box = sender as TextBox;
            var data = (TextBoxTagData)box.Tag;

            data.LatestKey = e.Key;
        }

        public void OnInputBoxDataPaste(object sender, DataObjectPastingEventArgs e)
        {
            if (!e.SourceDataObject.GetDataPresent(DataFormats.UnicodeText, true)) return;

            var box = sender as TextBox;
            var data = (TextBoxTagData)box.Tag;
            data.EligibleForChange = data.LastTextChangeWasPasted = true;

            box.Tag = data;
        }

        #endregion

        #region Imports

        private IntPtr HotkeyHook(IntPtr hwnd, int msg, IntPtr wParam, IntPtr lParam, ref bool handled)
        {
            // msg is WM_HOTKEY (0x0312) and it looks for the hotkey's ID in the hotkey dictionary
            if (msg == 0x0312) hotkeys.ElementAt(wParam.ToInt32()).Value(null, null);

            return IntPtr.Zero;
        }

        [DllImport("user32.dll")]
        static extern bool RegisterHotKey(IntPtr hwnd, int id, int fsModifiers, int vk);

        #endregion
    }
}