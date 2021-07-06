package userinterface.dishcategorymanage;

import edgeclass.DishCategoryEdge;
import entity.DishCategory;
import entitydatabase.DishCategoryDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        DishCategoryEdge.deleteLine(jTable,model);
    }

    @Override
    public void updateData() {
        DishCategoryEdge.updateData(jTable,model);
    }

    @Override
    public void saveData() {
        DishCategoryEdge.saveData(jTable,rowLength,model);
    }

    @Override
    public void searchByRule() {
        DishCategoryEdge.searchByRule(labelContent,model,jTable,searchPanel,bottomPanel);
    }

}
