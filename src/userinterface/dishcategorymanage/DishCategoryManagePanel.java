package userinterface.dishcategorymanage;

import entity.DishCategory;
import entitydatabase.DishCategoryDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DishCategoryManagePanel extends ManagePanel {

    public DishCategoryManagePanel(){
    }

    public void getTable(){
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return 0 > row || row >= getRowCount() || column != 0;
            }
        };
        Vector columnData = new Vector();
        columnData.add("序号");
        columnData.add("名称");
        columnData.add("描述");

        Vector rowData = new Vector<>();

        for(DishCategory dishCategory: new DishCategoryDAO().getList()){
            Vector single = new Vector();
            single.add(dishCategory.getId());
            single.add(dishCategory.getName());
            single.add(dishCategory.getDescribe());
            rowData.add(single);
        }

        model.setDataVector(rowData,columnData);
        rowLength = model.getRowCount();
        jTable = new JTable(model);
    }

    @Override
    public void deleteLine() {
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

    @Override
    public void updateData() {
        List<DishCategory> dishCategories = new ArrayList<>();
        for(int i=0;i<model.getRowCount();i++){
                int id = Integer.parseInt(model.getValueAt(i,0).toString());
                DishCategory dishCategory = new DishCategory();
                dishCategory.setId(id);
                dishCategory.setName((String) model.getValueAt(i,1));
                dishCategory.setDescribe((String) model.getValueAt(i,2));
                dishCategories.add(dishCategory);
            }
        new DishCategoryDAO().updateList(dishCategories);
    }

    @Override
    public void saveData() {
        List<DishCategory> dishCategories = new ArrayList<>();
        for(int i=rowLength;i<model.getRowCount();i++){
            DishCategory dishCategory = new DishCategory();
            dishCategory.setName(model.getValueAt(i,1).toString());
            dishCategory.setDescribe(model.getValueAt(i,2).toString());
            dishCategories.add(dishCategory);
        }
        new DishCategoryDAO().saveList(dishCategories);
        List<DishCategory> dishCategories1 = new DishCategoryDAO().getList();
        for(int i=rowLength;i<model.getRowCount();i++){
            model.setValueAt(dishCategories1.get(i).getId(),rowLength,0);
        }
    }

    @Override
    public void searchByRule() {
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
            JOptionPane.showMessageDialog(this,"未找到对应信息","错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected boolean checkConflict() {
        boolean flag = true;
        int row = jTable.getSelectedRow();
        String name = model.getValueAt(row,1).toString();
        for(int i=0;i < model.getRowCount();i++){
            if(name.equals(model.getValueAt(i,1).toString())&&i!=row){
                flag = false;
                JOptionPane.showMessageDialog(this,"该类菜品已存在","错误", JOptionPane.ERROR_MESSAGE);
                break;
            }

        }
        return flag;
    }
}
