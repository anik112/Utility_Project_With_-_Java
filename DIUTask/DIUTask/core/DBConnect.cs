using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using MySqlConnector;
using System.Windows.Forms;

namespace DIUTask.core
{
    class DBConnect
    {

        public static MySqlConnection getConnect()
        {
            MySqlConnection cnn = new MySqlConnection();
            try
            {
                //@"server=example.com;userid=example_user;password=example_password;database=example_database";
                string connectionString = @"server=localhost;port=3306;database=diutask;userid=root;default command timeout=20";
                cnn = new MySqlConnection(connectionString);
                cnn.Open();
                //MessageBox.Show("Connection Open");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return cnn;
        }
    }
}
