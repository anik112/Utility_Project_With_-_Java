using DIUTask.core;
using MySqlConnector;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace DIUTask.admin
{
    class AdminService
    {

        public MySqlDataReader viewAllStudentList()
        {
            MySqlDataReader reader = null;
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT  `reg_number`, `name`, `father_name`, `address`, `mobile`, `email` FROM `tb_user_info` WHERE user_type=@userType";
                cmd.Parameters.AddWithValue("@userType", "student");
                reader = cmd.ExecuteReader();
                return reader;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }
            return reader;
        }


        public MySqlDataReader viewAllApplyForTempCartificate()
        {
            MySqlDataReader reader = null;
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `reg_number`, `name`, `father_name`, `address`, `mobile`, `email`, `apply_date`, `tranit`, `status` FROM `tb_cartificate_tmp`";
                reader = cmd.ExecuteReader();
                return reader;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
            }
            return reader;
        }


        public MySqlDataReader viewAllApplyForMainCartificate()
        {
            MySqlDataReader reader = null;
            try
            {
                MySqlCommand cmd = DBConnect.getConnect().CreateCommand();
                cmd.CommandText = "SELECT `reg_number`, `name`, `father_name`, `address`, `mobile`, `email`, `apply_date`, `tranit`, `status`, `session`, `program`, `CGPA` FROM `tb_cartificate_main`";
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
