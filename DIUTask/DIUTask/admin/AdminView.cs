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

namespace DIUTask.admin
{
    public partial class AdminView : Form
    {
        public AdminView()
        {
            InitializeComponent();
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

        private void AdminView_Load(object sender, EventArgs e)
        {

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
    }
}
