package edgeclass;

import entity.DishCategory;
import entitydatabase.DishCategoryDAO;
import userinterface.ErrorPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

//中间层，用于实现界面层与服务层之间的操作
public class DishCategoryEdge {
    //对界面层数据进行查空
    private static boolean checkNull(JTable jTable, DefaultTableModel model){
        boolean flag = true;
        try{
            int row = jTable.getSelectedRow();
            for(int i= 1;i<model.getColumnCount();i++){
                String value = model.getValueAt(row,i).toString();
                if (value.equals("")){
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        if(!flag){
            ErrorPanel.ShowMessage("信息不完整");
        }
        return flag;
    }
    //对界面层数据进行查重
    private static boolean checkConflict(JTable jTable, DefaultTableModel model) {
        boolean flag = true;
        int row = jTable.getSelectedRow();
        String name = model.getValueAt(row,1).toString();
        for(int i=0;i < model.getRowCount();i++){
            if(name.equals(model.getValueAt(i,1).toString())&&i!=row){
                flag = false;
                ErrorPanel.ShowMessage("该类菜品已存在");
                break;
            }

        }
        return flag;
    }

    //保存数据
    public static void saveData(JTable jTable,int rowLength,DefaultTableModel model) {
        if(checkNull(jTable,model)) {
            if(checkConflict(jTable, model)) {
                List<DishCategory> dishCategories = new ArrayList<>();
                for (int i = rowLength; i < model.getRowCount(); i++) {
                    DishCategory dishCategory = new DishCategory();
                    dishCategory.setName(model.getValueAt(i, 1).toString());
                    dishCategory.setDescribe(model.getValueAt(i, 2).toString());
                    dishCategories.add(dishCategory);
                }
                new DishCategoryDAO().saveList(dishCategories);
                List<DishCategory> dishCategories1 = new DishCategoryDAO().getList();
                for (int i = rowLength; i < model.getRowCount(); i++) {
                    model.setValueAt(dishCategories1.get(i).getId(), rowLength, 0);
                }
            }
        }
    }

    //删除数据
    public static void deleteLine(JTable jTable, DefaultTableModel model) {
        if(checkNull(jTable, model)) {
            if(checkConflict(jTable, model)) {
                List<DishCategory> dishCategories = new ArrayList<>();

                int[] rowList = jTable.getSelectedRows();
                for (int j : rowList) {
                    DishCategory dishCategory = new DishCategory();
                    dishCategory.setId(Integer.parseInt(model.getValueAt(j, 0).toString()));
                    dishCategory.setName(model.getValueAt(j, 1).toString());
                    dishCategory.setDescribe(model.getValueAt(j, 2).toString());
                    dishCategories.add(dishCategory);
                }

                new DishCategoryDAO().deleteList(dishCategories);
                model.removeRow(jTable.getSelectedRow());
            }
        }
    }

    //修改数据
    public static void updateData(JTable jTable,DefaultTableModel model) {
        if(checkNull(jTable, model)) {
            if(checkConflict(jTable, model)) {
                List<DishCategory> dishCategories = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                    DishCategory dishCategory = new DishCategory();
                    dishCategory.setId(id);
                    dishCategory.setName((String) model.getValueAt(i, 1));
                    dishCategory.setDescribe((String) model.getValueAt(i, 2));
                    dishCategories.add(dishCategory);
                }
                new DishCategoryDAO().updateList(dishCategories);
            }
        }
    }

    //搜素数据
    public static void searchByRule(String labelContent,DefaultTableModel model,JTable jTable,JPanel searchPanel,JPanel bottomPanel) {
        boolean flag = false;
        for(int i=0;i<model.getRowCount();i++){
            if(model.getValueAt(i,0).toString().equals(labelContent)||model.getValueAt(i,1).equals(labelContent)){
                jTable.setRowSelectionInterval(i,i);
                flag = true;
                searchPanel.setVisible(false);
                bottomPanel.setVisible(true);
                break;
            }
        }
        if(!flag){
            ErrorPanel.ShowMessage("未找到对应信息");
        }
    }
}
