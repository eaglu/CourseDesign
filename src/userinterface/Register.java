package userinterface;

import entity.Admin;
import entitydatabase.AdminDAO;

public class Register {
    private boolean verifyLogin(String username,String password){
        Admin admin = new AdminDAO().getByUsername(AdminDAO.hashCode(username));
        if(admin==null){
            return false;
        }

        boolean flag = false;

        if(admin.getUsername().equals(AdminDAO.hashCode(username))&&admin.getPassword().equals(AdminDAO.hashCode(password))) {
            flag = true;
        }

        return flag;
    }
}
