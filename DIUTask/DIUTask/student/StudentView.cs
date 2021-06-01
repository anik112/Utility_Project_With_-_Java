using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DIUTask.core;

namespace DIUTask.student
{
    public partial class StudentView : Form
    {
        public StudentView()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void tabCartificateStatus_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void tabPage3_Click(object sender, EventArgs e)
        {

        }

        private void label21_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            TempCartificateModel model = new TempCartificateModel();

            model.reg_number = txtRegNumberTmp.Text;
            model.name = txtNameTmp.Text;
            model.father_name = txtFatherNameTmp.Text;
            model.address = txtAddressTmp.Text;
            model.mobile = txtMobileTmp.Text;
            model.email = txtEmailTmp.Text;
            model.apply_date = System.DateTime.Now;
            model.tranit = txtTranITTmp.Text;
            model.status = txtStatusTmp.Text;

            model.insertTempCartificateData();


        }
    }
}
