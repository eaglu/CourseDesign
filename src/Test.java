import entity.DishCategory;
import entitydatabase.DishCategoryDAO;
import userinterface.MainFrame;
import userinterface.deskmanage.DeskManagePanel;
import userinterface.dishcategorymanage.DishCategoryManagePanel;
import userinterface.dishmanage.DishManagePanel;

import javax.swing.*;
import java.util.Vector;

class Super{
    int i=1;

    public int getI() {
        return i;
    }
}

class sub extends Super{
    int i=2;

    @Override
    public int getI() {
        return i;
    }
}

class Table extends JFrame{
    JScrollPane jsp;
    JTable jTable;
    Table(){
        Vector columnData = new Vector();
        columnData.add("名称");
        columnData.add("描述");

        Vector rowData = new Vector();

        for(DishCategory dishCategory:new DishCategoryDAO().getList()) {
            Vector single = new Vector();
            single.add(dishCategory.getName());
            single.add(dishCategory.getDescribe());
            rowData.add(single);
        }
        jTable = new JTable(rowData,columnData);
        jsp = new JScrollPane(jTable);
        add(jsp);
        setSize(400,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

public class Test {
    public static void main(String[] args){
//        List<DishCategory> dishCategories = new ArrayList<>();
//        DishCategory a = new DishCategory();
//        a.setName("A");
//        a.setDescribe("a");
//        DishCategory b = new DishCategory();
//        b.setName("B");
//        b.setDescribe("b");
//        DishCategory c = new DishCategory();
//        c.setName("C");
//        c.setDescribe("c");
//        dishCategories.add(a);
//        dishCategories.add(b);
//        dishCategories.add(c);

        JFrame jf = new JFrame();
        jf.setVisible(true);
        jf.setTitle("Test");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1920,1080);
        jf.setLocationRelativeTo(null);
        jf.add(new DeskManagePanel());  DishCategoryDAO dishCategoryDAO = new DishCategoryDAO();
//
//        DishCategory d = new DishCategory();
//        d.setName("E");
//        d.setDescribe("e");
//        dishCategoryDAO.save(d);

//
//        new LoginFrame();



//        new Table();

//        new MainFrame();
//        System.out.println(new Super().getI());
//        System.out.println(new sub().getI());
    }
}
