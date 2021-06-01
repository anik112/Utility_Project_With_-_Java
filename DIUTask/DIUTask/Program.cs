using DIUTask.student;
using DIUTask.core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask
{
    class Program
    {
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
             Application.Run(new StudentView());
            //DBConnect.getConnect(); 
        }
    }
}
