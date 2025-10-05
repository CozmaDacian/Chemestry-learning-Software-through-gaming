using System.Configuration;
using System.Data;
using System.Diagnostics;
using System.Printing;
using System.Windows;

namespace ChemEditorApp
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        public static string[] Arguments { get => args; }

        static string[] args;

        public App()
        {
            Startup += (s, e) =>
            {
                args = e.Args;
            };
        }
    }
}
