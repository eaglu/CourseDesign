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
    DishCategorySearchFrame categorySearchFrame;

    public DishCategoryManagePanel(){
        search.addActionListener(e->new DishCategorySearchFrame());
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
    }

    @Override
    public void searchByRule() {

    }
}
