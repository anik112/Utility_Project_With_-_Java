using MySqlConnector;
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
using DIUTask.publicUser;
using DIUTask.student;

namespace DIUTask.admin
{
    public partial class AdminView : Form
    {
        private string RegNumber;
        public AdminView(string regNumber)
        {
            InitializeComponent();
            this.RegNumber = regNumber;

            showTempCartificateData();
            showMainCartificateData();
        }

        private void showTempCartificateData()
        {
            DataTable table = new DataTable();
            MySqlDataReader reader = new TempCartificateModel().showTempCartificateList();

            table.Load(reader);

            dataGridViewTempCartificate.DataSource = table;
        }

        private void showMainCartificateData()
        {
            DataTable table = new DataTable();
            MySqlDataReader reader = new MainCartificateModel().showMainCartificateList();

            table.Load(reader);

            dataGridViewMainCartificate.DataSource = table;
        }

        private void btnTmpUpload_Click(object sender, EventArgs e)
        {
            openFileDialogBox.ShowDialog();
            if (openFileDialogBox.CheckFileExists)
            {
                new TempCartificateModel().uploadTempCartificateFileName(openFileDialogBox.FileName, txtTmpRegNumber.Text);
            }
            
        }

        private void btnMainUpload_Click(object sender, EventArgs e)
        {
            openFileDialogBox.ShowDialog();
            if (openFileDialogBox.CheckFileExists)
            {
                new MainCartificateModel().uploadMainCartificateFileName(openFileDialogBox.FileName, txtMainRegNumber.Text);
            }
        }

        private void btnRefresh_Click(object sender, EventArgs e)
        {
            showTempCartificateData();
            showMainCartificateData();
        }

        private void AdminView_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

        private void btnPasswordChange_Click(object sender, EventArgs e)
        {
            if(txtOldPassword.Text != String.Empty
                && txtNewPassword.Text != String.Empty)
            {
                try
                {
                    MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                    cmd.CommandText = "UPDATE `tb_user_info` SET `password`= @newPass WHERE reg_number=@regNum and password=@oldPass";
                    cmd.Parameters.AddWithValue("@newPass", txtNewPassword.Text);
                    cmd.Parameters.AddWithValue("@regNum", RegNumber);
                    cmd.Parameters.AddWithValue("@oldPass", txtOldPassword.Text);
                    cmd.ExecuteNonQuery();

                    MessageBox.Show("Data Update Sucess!");

                    txtNewPassword.Text = "";
                    txtOldPassword.Text = "";
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void btnCreateUser_Click(object sender, EventArgs e)
        {
            new RegrastrationView(RegNumber,"admin").Show();
        }

        private void btnViewStudent_Click(object sender, EventArgs e)
        {
            DataTable table = new DataTable();
            table.Load(new AdminService().viewAllStudentList());
            new ReportView(table,"All Student List").Show();
        }

        private void btnViewTmpAplyList_Click(object sender, EventArgs e)
        {
            DataTable table = new DataTable();
            table.Load(new AdminService().viewAllApplyForTempCartificate());
            new ReportView(table, "Temporary Certificate Applicant List").Show();
        }

        private void btnViewMainAplyList_Click(object sender, EventArgs e)
        {
            DataTable table = new DataTable();
            table.Load(new AdminService().viewAllApplyForMainCartificate());
            new ReportView(table, "Main Certificate Applicant List").Show();
        }

        private void btnUploadedFileList_Click(object sender, EventArgs e)
        {
            DataTable table = new DataTable();
            table.Load(new AdminService().viewTempUploadedFileList());
            new ReportView(table, "Temporary Certificate File Uploaded List").Show();
        }

        private void btnUploadedFileListMain_Click(object sender, EventArgs e)
        {
            DataTable table = new DataTable();
            table.Load(new AdminService().viewMainUploadedFileList());
            new ReportView(table, "Main Certificate File Uploaded List").Show();
        }
    }
}
