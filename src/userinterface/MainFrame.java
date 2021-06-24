package userinterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    protected JMenuBar jmb;
    protected JPanel jp;
    protected JMenu dishManage,dish,dishCategory;
    protected DefaultTableModel defaultTableModel = null;

    public MainFrame(){
        setTitle("主界面");
        setSize(1920,1080);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jp = new JPanel();

        jmb = new JMenuBar();
        dishManage = new JMenu("餐品管理");

        jp.add(jmb);

        jmb.add(dishManage);

        dish = new JMenu("菜品信息管理");
        dish.addActionListener(e ->{} );

        dishCategory = new JMenu("菜品分类管理");
        dishManage.add(dish);
        dishManage.add(dishCategory);
        add(jp);
        setJMenuBar(jmb);
    }
}
