package userinterface.dishcategorymanage;

import userinterface.MainFrame;

import javax.swing.*;
import java.awt.*;

public class DishCategorySearchFrame extends JFrame {
    private JPanel panel,bottomPanel;
    private JButton confirm;
    private JLabel id,name;
    private JTextField idT,nameT;

    public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        panel.add(c,constraints);
    }

    public DishCategorySearchFrame(){
    super("搜索");

    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints pos = new GridBagConstraints();

    panel = new JPanel();
        panel.setLayout(gridBagLayout);


    id = new JLabel("序号");

    idT = new JTextField(20);

    name = new JLabel("名称");

    nameT = new JTextField(20);

    confirm = new JButton("确 定");
        confirm.addActionListener(e->{});

    pos.weighty=1;
        gridBagLayout.addLayoutComponent(id,pos);
        gridBagLayout.addLayoutComponent(idT,pos);

    pos.gridy=1;
        gridBagLayout.addLayoutComponent(name,pos);
        gridBagLayout.addLayoutComponent(nameT,pos);

    pos.gridy=2;
    pos.gridx=1;
        gridBagLayout.addLayoutComponent(confirm,pos);

        panel.add(id);
        panel.add(idT);
        panel.add(name);
        panel.add(nameT);
        panel.add(confirm);

    setContentPane(panel);
    setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(300,200);
    repaint();}

    public void searchById(){

    }
}
