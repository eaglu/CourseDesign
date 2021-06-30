package userinterface;

import entity.Admin;
import entitydatabase.AdminDAO;

import javax.swing.*;

public class Edge {
    public static void verifyLogin(String username,String password){
        Admin admin = new AdminDAO().getByUsername(AdminDAO.hashCode(username));
        if(admin==null){
            ErrorPanel.ShowMessage("用户名或密码错误");
        }

        new MainFrame();
    }

    public static void verifyRegister(String username,String password,String confirmPassword) {

        if(username.length()<5){
            ErrorPanel.ShowMessage("用户名长度不得小于五个字符");
        }

        if(username.length()>10){
            ErrorPanel.ShowMessage("用户名长度不得大于十个字符");
            return;
        }

        if(password.length()>20||password.length()<6){
            ErrorPanel.ShowMessage("密码长度不得大于二十个字符或小于六个字符");
            return;
        }

        if(!password.equals(confirmPassword)){
            ErrorPanel.ShowMessage("两次输入的密码不同，请重新输入");
            return;
        }

        if(new AdminDAO().getByUsername(AdminDAO.hashCode(username)) != null){
            ErrorPanel.ShowMessage("用户名已存在");
            return;
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        new AdminDAO().save(admin);
        JOptionPane.showMessageDialog(null,"注册成功","成功", JOptionPane.INFORMATION_MESSAGE);
    }
}
