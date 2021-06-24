package userinterface;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final int HEIGHT = 200;
    private final int WIDTH  = 300;

    private JLabel usernameL,passwordL;
    private JTextField usernameTf;
    private JPasswordField passwordPf;
    private JButton loginB;
    private JPanel loginP;

    public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        loginP.add(c,constraints);
    }

    public LoginFrame(){
        super("登陆");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints pos = new GridBagConstraints();

        loginP = new JPanel();
        loginP.setLayout(gridBagLayout);


        usernameL = new JLabel("用户名");

        usernameTf = new JTextField(20);

        passwordL = new JLabel("密码");

        passwordPf = new JPasswordField(20);

        loginB = new JButton("登 陆");

        pos.weighty=1;
        gridBagLayout.addLayoutComponent(usernameL,pos);
        gridBagLayout.addLayoutComponent(usernameTf,pos);

        pos.gridy=1;
        gridBagLayout.addLayoutComponent(passwordL,pos);
        gridBagLayout.addLayoutComponent(passwordPf,pos);

        pos.gridy=2;
        pos.gridx=1;
        gridBagLayout.addLayoutComponent(loginB,pos);

        loginP.add(usernameL);
        loginP.add(usernameTf);
        loginP.add(passwordL);
        loginP.add(passwordPf);
        loginP.add(loginB);

        setContentPane(loginP);
        setLocationRelativeTo(null);

    }
}
