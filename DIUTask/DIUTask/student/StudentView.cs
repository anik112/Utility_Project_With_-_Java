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
using MySqlConnector;

namespace DIUTask.student
{
    public partial class StudentView : Form
    {
        private string RegNumber;

        public StudentView(string regNumber)
        {
            InitializeComponent();
            this.RegNumber = regNumber;

            txtApplyDateMain.Text = System.DateTime.Now.ToString("dd/mm/yyyy"); 
            txtApplyDateTmp.Text = System.DateTime.Now.ToString("dd/mm/yyyy");
            lblRegNumber.Text = RegNumber;

            checkTempCartificateAvailable();
            checkMainCartificateAvailable();
            setDefultData();
        }

        private bool checkAllDataEantryOrNot(int fromIndex)
        {
            if (fromIndex == 1)
            {
                if(txtRegNumberTmp.Text != String.Empty
                   && txtNameTmp.Text != String.Empty
                   && txtFatherNameTmp.Text != String.Empty
                   && txtAddressTmp.Text != String.Empty
                   && txtMobileTmp.Text != String.Empty
                   && txtEmailTmp.Text != String.Empty
                   && txtApplyDateTmp.Text != String.Empty
                   && txtTranITTmp.Text != String.Empty
                   && txtStatusTmp.Text != String.Empty)
                {
                    return true;
                }
                else
                {
                    MessageBox.Show("Please fulfill all requirements first.");
                }
            }else if (fromIndex == 2)
            {
                if (txtRegNumberMain.Text != String.Empty
                   && txtNameMain.Text != String.Empty
                   && txtFatherNameMain.Text != String.Empty
                   && txtAddressMain.Text != String.Empty
                   && txtMobileMain.Text != String.Empty
                   && txtEmailMain.Text != String.Empty
                   && txtApplyDateMain.Text != String.Empty
                   && txtTranITMain.Text != String.Empty
                   && txtStatusMain.Text != String.Empty
                   && txtSessionMain.Text != String.Empty
                   && txtProgramMain.Text != String.Empty
                   && txtCGPAMain.Text != String.Empty)
                {
                    return true;
                }
                else
                {
                    MessageBox.Show("Please fulfill all requirements first.");
                }
            }

            return false;
        }

        private void label21_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            TempCartificateModel model = new TempCartificateModel();

            if (checkAllDataEantryOrNot(1))
            {
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

        private void button2_Click(object sender, EventArgs e)
        {
            MainCartificateModel model = new MainCartificateModel();

            if (checkAllDataEantryOrNot(2))
            {
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
            if (File.Exists(uploadedPath))
            {
                File.Move(uploadedPath, downloadPath);
                MessageBox.Show("File download in " + downloadPath);
            }
            else
            {
                MessageBox.Show("File not exists !");
            }
            
        }

        private void btnMainDownload_Click(object sender, EventArgs e)
        {
            string uploadedPath=new MainCartificateModel().getFileLocation(lblRegNumber.Text);
            string[] paths = uploadedPath.Split('\\');

            folderBrowserDialogBox.ShowDialog();

            string downloadPath = folderBrowserDialogBox.SelectedPath + "\\" + paths[paths.Length - 1];

            if (File.Exists(downloadPath))
            {
                File.Delete(downloadPath);
            }
            if (File.Exists(uploadedPath))
            {
                File.Move(uploadedPath, downloadPath);
                MessageBox.Show("File download in " + downloadPath);
            }
            else
            {
                MessageBox.Show("File not exists !");
            }
            
        }

        private void setDefultData()
        {
            MySqlDataReader readerMain = new MainCartificateModel().getSelectedData(RegNumber);
            //`name`, `father_name`, `address`, `mobile`, `email`
            while (readerMain.Read())
            {
                txtRegNumberMain.Text = RegNumber;
                txtNameMain.Text = readerMain.GetString(0);
                txtFatherNameMain.Text = readerMain.GetString(1);
                txtAddressMain.Text = readerMain.GetString(2);
                txtMobileMain.Text = readerMain.GetString(3);
                txtEmailMain.Text = readerMain.GetString(4);

                txtRegNumberTmp.Text = RegNumber;
                txtNameTmp.Text = readerMain.GetString(0);
                txtFatherNameTmp.Text = readerMain.GetString(1);
                txtAddressTmp.Text = readerMain.GetString(2);
                txtMobileTmp.Text = readerMain.GetString(3);
                txtEmailTmp.Text = readerMain.GetString(4);
                break;
            }
        }

        private void StudentView_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }
    }
}
