package userinterface;

import entity.Admin;
import entitydatabase.AdminDAO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterFrame extends JFrame {
    private final int HEIGHT = 200;
    private final int WIDTH  = 300;

    private JLabel usernameL,passwordL,confirmPasswordL;
    private JTextField usernameTf,passwordPf,confirmPasswordT;
    private JButton regestB;
    private JPanel loginP,buttomP;

    public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        loginP.add(c,constraints);
    }

    public RegisterFrame(){
        super("注册 ");

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints pos = new GridBagConstraints();

        setLayout(new FlowLayout());

        loginP = new JPanel();
        loginP.setLayout(gridBagLayout);


        usernameL = new JLabel("用户名");

        usernameTf = new JTextField(20);

        passwordL = new JLabel("密码");

        passwordPf = new JTextField(20);

        confirmPasswordL = new JLabel("确认密码");
        confirmPasswordT = new JTextField(20);

        buttomP = new JPanel(new FlowLayout());

        regestB = new JButton("确 定");
        regestB.addActionListener(e->{
//            if(verify()){
//                registerUser();
//                dispose();
//            }
            Edge.verifyRegister(usernameTf.getText(),passwordPf.getText(),confirmPasswordT.getText());
        });

        pos.weighty=1;
        gridBagLayout.addLayoutComponent(usernameL,pos);
        gridBagLayout.addLayoutComponent(usernameTf,pos);

        pos.gridy=1;
        gridBagLayout.addLayoutComponent(passwordL,pos);
        gridBagLayout.addLayoutComponent(passwordPf,pos);

        pos.gridy=2;
        gridBagLayout.addLayoutComponent(confirmPasswordL,pos);
        gridBagLayout.addLayoutComponent(confirmPasswordT,pos);

        loginP.add(usernameL);
        loginP.add(usernameTf);
        loginP.add(passwordL);
        loginP.add(passwordPf);
        loginP.add(confirmPasswordL);
        loginP.add(confirmPasswordT);
        buttomP.add(regestB);

        add(loginP);
        add(buttomP);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        repaint();
    }

    public boolean verify() {
        String username = usernameTf.getText();
        String password = passwordPf.getText();

        if(username.length()>10){
            JOptionPane.showMessageDialog(loginP,"用户名长度不得大于十个字符","错误", JOptionPane.ERROR_MESSAGE);
        }

        if(password.length()>20||password.length()<6){
            JOptionPane.showMessageDialog(loginP,"密码长度不得大于二十个字符或小于6个字符","错误", JOptionPane.ERROR_MESSAGE);
        }

        if(!password.equals(confirmPasswordT.getText())){
            JOptionPane.showMessageDialog(loginP,"两次输入的密码不同，请重新输入","错误", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Admin admin = new AdminDAO().getByUsername(AdminDAO.hashCode(username));
        return admin == null;
    }

    public void registerUser(){
        String username = usernameTf.getText();
        String password = passwordPf.getText();

        List<Admin> adminList = new ArrayList<>();

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        adminList.add(admin);

        new AdminDAO().saveList(adminList);
    }
}
