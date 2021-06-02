using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask.admin
{
    public partial class ReportView : Form
    {
        public ReportView(DataTable myDataSet, string reportName)
        {
            InitializeComponent();
            dataGridReportView.DataSource = myDataSet;
        }

        private void ReportView_Load(object sender, EventArgs e)
        {
        }
    }
}
