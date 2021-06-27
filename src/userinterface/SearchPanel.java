package userinterface;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JFrame {
    private JPanel panel,bottomPanel;
    private JButton confirm;
    private JLabel label;
    private JTextField content;
    private JTextArea info;
    public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        panel.add(c,constraints);
    }

    public SearchPanel(){

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints pos = new GridBagConstraints();

        panel = new JPanel();
        panel.setLayout(gridBagLayout);


        label = new JLabel("序号");

        content = new JTextField(20);


        confirm = new JButton("确 定");
        confirm.addActionListener(e->{dispose();});

        pos.weighty=1;
        gridBagLayout.addLayoutComponent(label,pos);
        gridBagLayout.addLayoutComponent(content,pos);

        pos.gridy=2;
        pos.gridx=1;
        gridBagLayout.addLayoutComponent(confirm,pos);

        panel.add(label);
        panel.add(content);
        panel.add(confirm);

        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,200);
        repaint();}

    public String getContent() {
        return content.getText();
    }

    public void searchById(){

    }
}