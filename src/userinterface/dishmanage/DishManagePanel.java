package userinterface.dishmanage;

import edgeclass.DishEdge;
import entity.Dish;
import entity.DishCategory;
import entitydatabase.DishCategoryDAO;
import entitydatabase.DishDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DishManagePanel extends ManagePanel {
    @Override
    public void getTable() {
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return 0 > row || row >= getRowCount() || column != 0;
            }
        };

        Vector columnData = new Vector();
        columnData.add("序号");
        columnData.add("名称");
        columnData.add("类别代码");
        columnData.add("图片");
        columnData.add("菜品代码");
        columnData.add("单位");
        columnData.add("单价");
        columnData.add("状态");

        Vector rowData = new Vector();
        for(Dish dish:new DishDAO().getList()){
            Vector single = new Vector();
            single.add(dish.getId());
            single.add(dish.getName());
            single.add(dish.getDishCategory().getId());
            single.add(dish.getPic());
            single.add(dish.getCode());
            single.add(dish.getUnit());
            single.add(dish.getPrice());
            single.add(dish.getStatus());
            rowData.add(single);
        }

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"在售","未售"});
        model.setDataVector(rowData,columnData);
        rowLength = model.getRowCount();
        jTable = new JTable(model);
        jTable.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(comboBox));
        List<Integer> categoryidList = new ArrayList<>();

        for(DishCategory dishCategory:new DishCategoryDAO().getList()){
            categoryidList.add(dishCategory.getId());
        }
        JComboBox jComboBox = new JComboBox(categoryidList.toArray());
        jTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(jComboBox));

    }

    @Override
    public void deleteLine() {
        DishEdge.deleteLine(jTable,model);
    }

    @Override
    public void saveData() {

        DishEdge.saveData(jTable,rowLength,model);
    }

    @Override
    public void updateData() {
        DishEdge.updateData(jTable,model);
    }

    @Override
    public void searchByRule() {
        DishEdge.searchByRule(labelContent,model,jTable,searchPanel,bottomPanel);
    }

}
