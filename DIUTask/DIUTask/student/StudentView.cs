using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
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
            txtApplyDateMain.Text = System.DateTime.Now.ToString("dd/mm/yyyy"); 
            txtAddressTmp.Text = System.DateTime.Now.ToString("dd/mm/yyyy");
            lblRegNumber.Text = "123";
            checkTempCartificateAvailable();
            checkMainCartificateAvailable();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void tabCartificateStatus_SelectedIndexChanged(object sender, EventArgs e)
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

        private void button2_Click(object sender, EventArgs e)
        {
            MainCartificateModel model = new MainCartificateModel();

            model.reg_number = txtRegNumberMain.Text;
            model.name = txtNameMain.Text;
            model.father_name = txtFatherNameMain.Text;
            model.address = txtAddressMain.Text;
            model.mobile = txtMobileMain.Text;
            model.email = txtEmailMain.Text;
            model.apply_date = System.DateTime.Now;
            model.tranit = txtTranITMain.Text;
            model.status = txtStatusMain.Text;
            model.session = txtSessionMain.Text;
            model.program = txtSessionMain.Text;
            model.CGPA = float.Parse(txtCGPAMain.Text);

            model.insertMainCartificateData();
        }

        private void checkTempCartificateAvailable()
        {
            if (new TempCartificateModel().checkAvailable(lblRegNumber.Text))
            {
                checkBoxTempAvailable.Checked = true;
            }
            else
            {
                checkBoxTempNotAvailable.Checked = true;
            }
        }

        private void checkMainCartificateAvailable()
        {
            if (new MainCartificateModel().checkAvailable(lblRegNumber.Text))
            {
                checkBoxMainAvailable.Checked = true;
            }
            else
            {
                checkBoxMinNotAvailable.Checked = true;
            }
        }

        private void StudentView_Load(object sender, EventArgs e)
        {

        }

        private void btnTmpDownload_Click(object sender, EventArgs e)
        {
            string uploadedPath = new TempCartificateModel().getFileLocation(lblRegNumber.Text);
            string[] paths = uploadedPath.Split('\\');

            folderBrowserDialogBox.ShowDialog();

            string downloadPath = folderBrowserDialogBox.SelectedPath+"\\"+ paths[paths.Length-1];
            
            if (File.Exists(downloadPath))
            {
                File.Delete(downloadPath);
            }
            File.Move(uploadedPath, downloadPath);

            MessageBox.Show("File download in "+downloadPath);
        }
    }
}
