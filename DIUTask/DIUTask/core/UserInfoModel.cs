using MySqlConnector;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask.core
{
    class UserInfoModel
    {
        public int id;
        public string reg_number;
        public string name;
        public string father_name;
        public string address;
        public string mobile;
        public string email;
        public DateTime create_date;
        public string userName;
        public string password;
        public string userType;

        public void insertUserInfoData()
        {
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "INSERT INTO `tb_user_info`(`reg_number`, `name`, `father_name`, `address`, `mobile`, `email`, `create_date`, `user_name`, `password`, `user_type`) " +
                                  "VALUES (@reg_number,@name,@father_name,@address,@mobile,@email,@create_date,@user_name,@password,@user_type)";
                cmd.Parameters.AddWithValue("@reg_number", reg_number);
                cmd.Parameters.AddWithValue("@name", name);
                cmd.Parameters.AddWithValue("@father_name", father_name);
                cmd.Parameters.AddWithValue("@address", address);
                cmd.Parameters.AddWithValue("@mobile", mobile);
                cmd.Parameters.AddWithValue("@email", email);
                cmd.Parameters.AddWithValue("@create_date", create_date);
                cmd.Parameters.AddWithValue("@user_name", userName);
                cmd.Parameters.AddWithValue("@password", password);
                cmd.Parameters.AddWithValue("@user_type", userType);

                cmd.ExecuteNonQuery();

                MessageBox.Show("Data Save Sucess!");
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }


        public void updateUserInfoData(string reg_number)
        {
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "UPDATE `tb_user_info` SET " +
                    "`name`=@name," +
                    "`father_name`=@father_name," +
                    "`address`=@address," +
                    "`mobile`=@mobile," +
                    "`email`=@email," +
                    "`create_date`=@create_date," +
                    "`user_name`=@user_name," +
                    "`password`=@password," +
                    "`user_type`=@user_type" +
                    "WHERE `reg_number`=@regNum";
                
                cmd.Parameters.AddWithValue("@name", name);
                cmd.Parameters.AddWithValue("@father_name", father_name);
                cmd.Parameters.AddWithValue("@address", address);
                cmd.Parameters.AddWithValue("@mobile", mobile);
                cmd.Parameters.AddWithValue("@email", email);
                cmd.Parameters.AddWithValue("@create_date", create_date);
                cmd.Parameters.AddWithValue("@user_name", userName);
                cmd.Parameters.AddWithValue("@password", password);
                cmd.Parameters.AddWithValue("@user_type", userType);
                cmd.Parameters.AddWithValue("@reg_number", reg_number);

                cmd.ExecuteNonQuery();

                MessageBox.Show("Data Update Sucess!");
            }
            catch (Exception e)
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


        public void uploadMainCartificateFileName(string fileName, string regNumber)
        {
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "UPDATE `tb_cartificate_main` SET `file_location`= @fileLoc WHERE reg_number=@regNum";
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
                cmd.CommandText = "SELECT `file_location` FROM `tb_cartificate_main` WHERE reg_number=@regNum";
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


        public void addSuperAdmin()
        {
            reg_number = "112255";
            name="super admin";
            father_name="admin";
            address="test";
            mobile="11111111111";
            email="admin@gmail.com";
            create_date= System.DateTime.Now; 
            userName="admin";
            password="admin";
            userType="admin";

            insertUserInfoData();
        }

    }
}
