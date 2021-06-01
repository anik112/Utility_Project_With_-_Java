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
                cmd.CommandText = "INSERT INTO `tb_cartificate_tmp`( `reg_number`, `name`, `father_name`, `address`, `mobile`, `email`, `apply_date`, `tranit`, `status`) " +
                                                "VALUES (@reg_number,@name,@father_name,@address,@mobile,@email,@apply_date,@tranit,@status)";
                cmd.Parameters.AddWithValue("@reg_number", reg_number);
                cmd.Parameters.AddWithValue("@name", name);
                cmd.Parameters.AddWithValue("@father_name", father_name);
                cmd.Parameters.AddWithValue("@address", address);
                cmd.Parameters.AddWithValue("@mobile", mobile);
                cmd.Parameters.AddWithValue("@email", email);
                cmd.Parameters.AddWithValue("@apply_date", apply_date);
                cmd.Parameters.AddWithValue("@tranit", tranit);
                cmd.Parameters.AddWithValue("@status", status);

                cmd.ExecuteNonQuery();

                MessageBox.Show("Data Update Sucess!");
            }
            catch(Exception e)
            {
                MessageBox.Show(e.Message);
            }
        }

    }
}
