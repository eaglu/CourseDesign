package userinterface;

import edgeclass.LoReEdge;

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

        //触发器，用于实现登录验证功能
        loginB.addActionListener(e-> LoReEdge.verifyLogin(usernameTf.getText(),passwordPf.getText()));

        regestB = new JButton("注 册");
        regestB.addActionListener(e->new RegisterFrame());

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

}
