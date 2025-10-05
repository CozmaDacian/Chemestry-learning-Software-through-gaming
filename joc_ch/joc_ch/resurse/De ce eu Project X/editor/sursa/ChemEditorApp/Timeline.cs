using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace ChemEditorApp
{
    /// <summary>
    /// Respresents a basic timeline of changes to an object.
    /// </summary>
    /// <typeparam name="T"></typeparam>
    public class Timeline<T> where T : struct
    {
        /// <summary>
        /// Gets or sets maxmimum number of recorded steps in a <see cref="Timeline{T}"/>. The default is 50.
        /// </summary>
        public int MaxRecordedSteps { get; set; } = 50;

        /// <summary>
        /// Gets the step where the timeline is currently at.
        /// </summary>
        public int CurrentStep { get => step; }

        /// <summary>
        /// Gets or sets the current element in the timeline. Setting a current object will append it to the timeline and removes the possibility to redo any undone changes.
        /// </summary>
        public T Current
        {
            get => current;
            set
            {
                current = value;
                backup.Clear();
                Advance();
            }
        }

        #region Fields

        T current;
        LinkedList<T> timeline = new();
        Stack<T> backup = new();
        int step = 1;

        #endregion

        /// <summary>
        /// Initializes the <see cref="Timeline{T}"/> class.
        /// <para>
        /// After this call, <paramref name="value"/> will be the current object.
        /// </para>
        /// </summary>
        /// <param name="value"></param>
        public Timeline(T value)
        {
            Current = value;
        }

        /// <summary>
        /// Undoes the last change to the timeline by removing the current object from the timeline. The element before it will be become the new current object.
        /// <para>
        /// If the timeline has 1 or less elements, this method does nothing.
        /// </para>
        /// </summary>
        public void Undo()
        {
            if (timeline.Count <= 1) return;

            backup.Push(Current);
            timeline.RemoveLast();
            current = timeline.Last.Value;
            step--;
        }

        /// <summary>
        /// Redoes the undoing of a change to the timeline by re-appending the previously removed current object to the timeline.
        /// <para>
        /// If there are no values in the backup stack, this mehtod will do nothing.
        /// </para>
        /// </summary>
        public void Redo()
        {
            if (backup.Count == 0) return;

            current = backup.Pop();
            Advance();
        }

        /// <summary>
        /// Clears out the timeline, including the backup stack. A new current element must be provided.
        /// </summary>
        public void Clear(T value)
        {
            timeline.Clear();
            backup.Clear();

            Current = value;
            step = 1;
        }

        /// <summary>
        /// Advances the timeline by appending the current object to the timeline.
        /// </summary>
        void Advance()
        {
            if (timeline.Count == 0) timeline.AddFirst(Current);
            else timeline.AddAfter(timeline.Last, Current);

            step++;

            if (timeline.Count <= MaxRecordedSteps) return;

            timeline.RemoveFirst();
            step--;
        }
    }
}
