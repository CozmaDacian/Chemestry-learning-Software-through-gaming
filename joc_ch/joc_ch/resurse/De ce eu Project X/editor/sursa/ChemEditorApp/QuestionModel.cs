using System.Diagnostics;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace ChemEditorApp
{
    /// <summary>
    /// Represents a quiz question. A <see cref="QuestionModel"/> contains a question and 4 possible answering options.
    /// </summary>
    public struct QuestionModel
    {
        public string Question { get; set; }

        public string Description { get; set; }

        public string[] Answers { get; internal set; } = new string[4];

        public bool[] Validity { get; internal set; } = new bool[4];

        public int QuestionIndex { get; internal set; }

        public QuestionModel()
        {
            // So apparently field initializers aren't run if the struct is not initialized with a manually defined constructor.
            // Since in Main.xaml.cs I call QuestionModel(), I'll have define it manually here.
        }

        /// <summary>
        /// Creates an instance of the <see cref="QuestionModel"/> structure using the specified question data.
        /// <para>
        /// To the caller, the <paramref name="validity"/> and <paramref name="answers"/> arrays are deep copied into the instance, so they can be modified after this call.
        /// </para>
        /// </summary>
        /// <param name="index">The index of the question in the context of multiple questions.</param>
        /// <param name="question">The question itself.</param>
        /// <param name="validity">An array of exactly 4 boolean values which represent the validity of each answer option.</param>
        /// <param name="answers">An array of exactly 4 answer options.</param>
        public QuestionModel(int index, string question, string desc, bool[] validity, params string[] answers)
        {
            Description = desc;
            QuestionIndex = index;
            Question = question;

            for (int i = 0; i < 4; i++)
            {
                Validity[i] = validity[i];
                Answers[i] = answers[i];
            }
        }

        /// <summary>
        /// Parses an array of questions and answers into a <see cref="LinkedList{T}"/> of <see cref="QuestionModel"/> objects.
        /// <para>
        /// <paramref name="questions"/> shall contain a question every 5 positions, starting from the first, followed by 4 answer options. For an answer option to be deemed valid, it should be preceded by a '*' character.
        /// </para>
        /// </summary>
        public static (LinkedList<QuestionModel>, List<LinkedListNode<QuestionModel>>) LinkedParse(string[] questions, string[] descriptions)
        {
            var question_list = new LinkedList<QuestionModel>();
            var node_list = new List<LinkedListNode<QuestionModel>>();

            var validity = new bool[4];
            var answers = new string[4];

            for (int i = 0; i < questions.Length; i++)
            {
                if (i % 5 == 0)
                {
                    question_list.AddLast(new QuestionModel(i / 5, questions[i], i / 5 >= descriptions.Length ? "Descrierea vine aici" : descriptions[i / 5], validity, answers));
                    node_list.Add(question_list.Last);
                    continue;
                }

                bool valid = questions[i].StartsWith('*');
                question_list.Last.Value.Validity[(i - 1 - i / 5) % 4] = valid;
                question_list.Last.Value.Answers[(i - 1 - i / 5) % 4] = questions[i][(valid ? 1 : 0)..]; 
            }

            return (question_list, node_list);
        }
    }

    // a true shame

    public struct TextBoxTagData
    {
        public string Text { get; set; }

        public int CaretIndex { get; set; }

        public Key PreviousLatestKey { get; set; }

        public Key LatestKey { get; set; }

        public bool LastTextChangeWasPasted { get; set; }

        public int PreviousCaretIndex { get; set; }

        public bool EligibleForChange { get; set; }

        public TextBoxTagData(string text, int currentCaret, bool eligible = false, Key latestKey = Key.None, int previousCaret = 0)
        {
            Text = text;
            LatestKey = latestKey;
            PreviousCaretIndex = previousCaret;
            CaretIndex = currentCaret;
            EligibleForChange = eligible;
        }
    }

    public struct QuestionTimelineData
    {
        public TextBoxTagData[] InputBoxes { get; set; } = new TextBoxTagData[5];

        public bool[] ValidityBoxes { get; set; } = new bool[4];

        public int SelectedIndex { get; set; }

        public int PageIndex { get; set; }

        public QuestionTimelineData()
        {

        }

        public QuestionTimelineData(int page, int selected, bool[] validity, params TextBoxTagData[] data)
        {
            PageIndex = page;
            SelectedIndex = selected;
            for (int i = 0; i < 5; i++)
            {
                if (i < 4) ValidityBoxes[i] = validity[i];
                InputBoxes[i] = data[i];
            }
        }
    }
}
