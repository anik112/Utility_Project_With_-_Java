using DIUTask.admin;
using DIUTask.core;
using DIUTask.student;
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

namespace DIUTask.publicUser
{
    public partial class Login : Form
    {
        public Login()
        {
            InitializeComponent();
            txtUserName.Text = "User Name";
            txtPassword.Text = "Password";
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {

            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `password`,`user_type`,`reg_number` FROM `tb_user_info` WHERE user_name=@userName and password=@password";
                cmd.Parameters.AddWithValue("@userName", txtUserName.Text);
                cmd.Parameters.AddWithValue("@password", txtPassword.Text);
                MySqlDataReader reader = cmd.ExecuteReader();

                bool isValid = false;
                string userType = "student";
                string regNumber = "";
                while (reader.Read())
                {
                    isValid = true;
                    userType = reader.GetString(1);
                    regNumber = reader.GetString(2);
                    break;
                }
                reader.Close();

                if (isValid)
                {
                    if (userType == "admin")
                    {
                        this.Hide();
                        new AdminView(regNumber).Show();

                    }
                    else if(userType == "teacher")
                    {
                        this.Hide();
                        new RegrastrationView(regNumber,"users").Show();
                    }
                    else
                    {
                        this.Hide();
                        new StudentView(regNumber).Show();
                    }
                }else if(txtUserName.Text=="admin" && txtPassword.Text == "admin")
                {
                    new UserInfoModel().addSuperAdmin();
                }
                else
                {
                    MessageBox.Show("User Name Or Password Invalid!");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
           
        }


        private void txtPassword_MouseClick(object sender, MouseEventArgs e)
        {
            txtPassword.Text = String.Empty;
        }

        private void txtUserName_MouseClick(object sender, MouseEventArgs e)
        {
            txtUserName.Text = String.Empty;
        }
    }
}
