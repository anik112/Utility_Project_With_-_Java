using MySqlConnector;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask.core
{
    class MainCartificateModel
    {

        public int id;
        public string reg_number;
        public string name;
        public string father_name;
        public string address;
        public string mobile;
        public string email;
        public DateTime apply_date;
        public string tranit;
        public string status;
        public string session;
        public string program;
        public float CGPA;

        public void insertMainCartificateData()
        {
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "INSERT INTO `tb_cartificate_main`(`reg_number`, `name`, `father_name`, `address`, `mobile`, `email`, `apply_date`, `tranit`, `status`, `session`, `program`, `CGPA`) " +
                                  "VALUES (@reg_number,@name,@father_name,@address,@mobile,@email,@apply_date,@tranit,@status,@session,@program,@CGPA)";
                cmd.Parameters.AddWithValue("@reg_number", reg_number);
                cmd.Parameters.AddWithValue("@name", name);
                cmd.Parameters.AddWithValue("@father_name", father_name);
                cmd.Parameters.AddWithValue("@address", address);
                cmd.Parameters.AddWithValue("@mobile", mobile);
                cmd.Parameters.AddWithValue("@email", email);
                cmd.Parameters.AddWithValue("@apply_date", apply_date);
                cmd.Parameters.AddWithValue("@tranit", tranit);
                cmd.Parameters.AddWithValue("@status", status);
                cmd.Parameters.AddWithValue("@session", session);
                cmd.Parameters.AddWithValue("@program",program);
                cmd.Parameters.AddWithValue("@CGPA",CGPA);

                cmd.ExecuteNonQuery();

                MessageBox.Show("Data Update Sucess!");
            }
            catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }


        public bool checkAvailable(string regNumber)
        {

            MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
            cmd.CommandText = "SELECT `file_location` FROM `tb_cartificate_main` WHERE reg_number=@regNum";
            cmd.Parameters.AddWithValue("@regNum", regNumber);
            MySqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                if (reader.GetString(0).Length > 1)
                {
                    return true;
                }
            }
            reader.Close();

            return false;
        }


        public MySqlDataReader showMainCartificateList()
        {
            MySqlDataReader reader = null;
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `reg_number`, `name`, `father_name`, `session`, `program`, `CGPA`, `apply_date`, `tranit`, `status` FROM `tb_cartificate_main` WHERE file_location='-'";
                reader = cmd.ExecuteReader();
                return reader;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }
            return reader;
        }

    }
}
