using DIUTask.student;
using DIUTask.core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DIUTask.admin;
using DIUTask.publicUser;

namespace DIUTask
{
    class Program
    {

        [STAThreadAttribute] // User for multi thread use in app
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            //Application.Run(new StudentView());
            //Application.Run(new AdminView());
            Application.Run(new Login());
            //DBConnect.getConnect(); 
        }
    }
}
