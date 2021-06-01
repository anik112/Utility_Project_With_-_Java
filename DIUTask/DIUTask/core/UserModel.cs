using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DIUTask.core
{
    class UserModel
    {
        public int id;
        public int regNumber;
        public string name;
        public string email;
        public string phoneNumber;
        public DateTime createDate;
        public string pass;
        public string userType;


        public void inserUserInfo()
        {
            string sql = "insert into tb_user_info ()";
        }


    }
}
