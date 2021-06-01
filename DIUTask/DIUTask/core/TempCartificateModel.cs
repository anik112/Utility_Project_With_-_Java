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
    class TempCartificateModel
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


        public void insertTempCartificateData()
        {
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "INSERT INTO `tb_cartificate_tmp`( `reg_number`, `name`, `father_name`, `address`, `mobile`, `email`, `apply_date`, `tranit`, `status`, `file_location`) " +
                                                "VALUES (@reg_number,@name,@father_name,@address,@mobile,@email,@apply_date,@tranit,@status, @fileLoc)";
                cmd.Parameters.AddWithValue("@reg_number", reg_number);
                cmd.Parameters.AddWithValue("@name", name);
                cmd.Parameters.AddWithValue("@father_name", father_name);
                cmd.Parameters.AddWithValue("@address", address);
                cmd.Parameters.AddWithValue("@mobile", mobile);
                cmd.Parameters.AddWithValue("@email", email);
                cmd.Parameters.AddWithValue("@apply_date", apply_date);
                cmd.Parameters.AddWithValue("@tranit", tranit);
                cmd.Parameters.AddWithValue("@status", status);
                cmd.Parameters.AddWithValue("@fileLoc", "-");
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

            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `file_location` FROM `tb_cartificate_tmp` WHERE reg_number=@regNum";
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
            }catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }

            return false;
        }

        public MySqlDataReader showTempCartificateList()
        {
            MySqlDataReader reader=null;
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `reg_number`, `name`, `father_name`, `mobile`, `email`, `apply_date`, `tranit`, `status` FROM `tb_cartificate_tmp` WHERE file_location='-'";
                reader = cmd.ExecuteReader();
                return reader;
            }
            catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }
            return reader;
        }


        public void uploadTempCartificateFileName(string fileName, string regNumber)
        {
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "UPDATE `tb_cartificate_tmp` SET `file_location`= @fileLoc WHERE reg_number=@regNum";
                cmd.Parameters.AddWithValue("@fileLoc", fileName);
                cmd.Parameters.AddWithValue("@regNum", regNumber);
                cmd.ExecuteNonQuery();

                MessageBox.Show("Data Update Sucess!");
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }

        public string getFileLocation(string regNumber)
        {

            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `file_location` FROM `tb_cartificate_tmp` WHERE reg_number=@regNum";
                cmd.Parameters.AddWithValue("@regNum", regNumber);
                MySqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    if (reader.GetString(0).Length > 1)
                    {
                        return reader.GetString(0);
                    }
                }
                reader.Close();
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }

            return "";
        }


    }
}
