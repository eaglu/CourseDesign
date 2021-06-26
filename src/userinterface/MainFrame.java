package userinterface;

import userinterface.customermanage.CustomerManagePanel;
import userinterface.deskmanage.DeskManagePanel;
import userinterface.dishcategorymanage.DishCategoryManagePanel;
import userinterface.dishmanage.DishManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    protected JMenuBar jmb;
    protected JPanel jp,secondPanel;
    protected JMenu dishManage,deskManage,customerManage;
    protected JMenuItem dish,dishCategory,desk,customer;
    protected DefaultTableModel defaultTableModel = null;
    protected JLabel label;

    public MainFrame(){

        jp = new JPanel();

        jmb = new JMenuBar();

        jp.add(jmb);

        dishManage = new JMenu("餐品管理");
        jmb.add(dishManage);

        dish = new JMenuItem("菜品信息管理");
        dish.addActionListener(e -> {
            remove(jp);
            jp = new DishManagePanel();
            add(jp);
            validate();
        });
        dishCategory = new JMenuItem("菜品分类管理");
        dishCategory.addActionListener(e-> {
            remove(jp);
            jp = new DishCategoryManagePanel();
            add(jp);
            validate();
        });
        dishManage.add(dish);
        dishManage.add(dishCategory);

        deskManage = new JMenu("餐台管理");
        desk = new JMenuItem("餐台管理");
        desk.addActionListener(e->{
            remove(jp);
            jp = new DeskManagePanel();
            add(jp);
            validate();
        });
        deskManage.add(desk);
        jmb.add(deskManage);

        customerManage = new JMenu("客户管理");
        customer = new JMenuItem("客户管理");
        customer.addActionListener(e->{
            remove(jp);
            jp = new CustomerManagePanel();
            add(jp);
            validate();
        });
        customerManage.add(customer);
        jmb.add(customerManage);

        label = new JLabel("餐厅管理系统");
        Font f = new Font("宋体",Font.PLAIN,30);

        label.setFont(f);
        jp.add(label);

        add(jp);
        setJMenuBar(jmb);

        setTitle("主界面");
        setSize(1920,1080);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
