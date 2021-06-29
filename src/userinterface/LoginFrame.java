package userinterface;

import entity.Admin;
import entitydatabase.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final int HEIGHT = 200;
    private final int WIDTH  = 300;

    private JLabel usernameL,passwordL;
    private JTextField usernameTf;
    private JPasswordField passwordPf;
    private JButton loginB,regestB;
    private JPanel loginP,buttomP;

    public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        loginP.add(c,constraints);
    }

    public LoginFrame(){
        super("登陆");

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints pos = new GridBagConstraints();

        setLayout(new FlowLayout());

        loginP = new JPanel();
        loginP.setLayout(gridBagLayout);


        usernameL = new JLabel("用户名");

        usernameTf = new JTextField(20);

        passwordL = new JLabel("密码");

        passwordPf = new JPasswordField(20);

        buttomP = new JPanel(new FlowLayout());

        loginB = new JButton("登 陆");
        loginB.addActionListener(e->{
            if(verify()){
                new MainFrame();
                dispose();
            }else {
                JOptionPane.showMessageDialog(loginP,"密码或账号错误，请重新输入","错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        regestB = new JButton("注 册");
        regestB.addActionListener(e->new RegestFrame());

        pos.weighty=1;
        gridBagLayout.addLayoutComponent(usernameL,pos);
        gridBagLayout.addLayoutComponent(usernameTf,pos);

        pos.gridy=1;
        gridBagLayout.addLayoutComponent(passwordL,pos);
        gridBagLayout.addLayoutComponent(passwordPf,pos);

        loginP.add(usernameL);
        loginP.add(usernameTf);
        loginP.add(passwordL);
        loginP.add(passwordPf);
        buttomP.add(loginB);
        buttomP.add(regestB);

        add(loginP);
        add(buttomP);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        repaint();
    }

    public boolean verify(){
        String username = usernameTf.getText();
        String password = passwordPf.getText();

        boolean flag = false;

        for(Admin admin:new AdminDAO().getList()){
            System.out.println(AdminDAO.hashCode(username));
            System.out.println(AdminDAO.hashCode(password));
            System.out.println(admin.getUsername());
            System.out.println(admin.getPassword());
            if(admin.getUsername().equals(AdminDAO.hashCode(username))&&admin.getPassword().equals(AdminDAO.hashCode(password))){
                flag = true;
                break;
            }
        }

        return flag;
    }
}
