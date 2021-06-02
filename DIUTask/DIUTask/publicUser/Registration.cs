using DIUTask.core;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask.publicUser
{
    public partial class RegrastrationView : Form
    {
        private string RegNumber;
        private string UserType;

        public RegrastrationView(string regNumber, string userType)
        {
            InitializeComponent();
            if (userType == "admin")
            {
                comboBoxUserType.Items.Add("admin");
            }
            this.RegNumber = regNumber;
            this.UserType = userType;
            txtCreateDate.Text = System.DateTime.Now.ToString("dd/mm/yyyy");
        }

        private bool checkAllDataEantryOrNot(int fromIndex)
        {
            if (txtRegNumber.Text != String.Empty
            && txtName.Text != String.Empty
            && txtFatherName.Text != String.Empty
            && txtAddress.Text != String.Empty
            && txtMobile.Text != String.Empty
            && txtEmail.Text != String.Empty
            && txtCreateDate.Text != String.Empty
            && txtUserName.Text != String.Empty)
        {
            return true;
        }
        else
        {
            MessageBox.Show("Please fulfill all requirements first.");
        }
            return false;
        }


        private void btnRegistration_Click(object sender, EventArgs e)
        {
            UserInfoModel model = new UserInfoModel();

            if (checkAllDataEantryOrNot(1))
            {
                model.reg_number = txtRegNumber.Text;
                model.name = txtName.Text;
                model.father_name = txtFatherName.Text;
                model.address = txtAddress.Text;
                model.mobile = txtMobile.Text;
                model.email = txtEmail.Text;
                model.userName = txtUserName.Text;
                if (txtPassword.Text != String.Empty)
                {
                    model.password = txtPassword.Text;
                }
                else
                {
                    model.password = "123456";
                }
                model.userType = comboBoxUserType.SelectedItem.ToString();
                model.create_date = System.DateTime.Now;

                model.insertUserInfoData();
            }

        }

        private void RegrastrationView_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (UserType == "admin")
            {
                this.Hide();
            }
            else{
                Application.Exit();
            }
        }
    }
}
