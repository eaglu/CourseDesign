package userinterface.dishmanage;

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
    public DishManagePanel(){
    }
    @Override
    public void getTable() {
        DefaultTableModel model = new DefaultTableModel();
        Vector columnData = new Vector();
        columnData.add("序号");
        columnData.add("名称");
        columnData.add("类别名");
        columnData.add("图片");
        columnData.add("代码");
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

        rowLength = model.getRowCount();
        model.setDataVector(rowData,columnData);
        jTable = new JTable(model);
    }

    @Override
    public void deleteLine() {
        List<DishCategory> dishCategories = new ArrayList<>();

        int[] rowList = jTable.getSelectedRows();
        for (int i : rowList) {
            int id = Integer.parseInt(model.getValueAt(i,0).toString());
            Dish dish = new Dish();
            dish.setId(id);
            dish.setName(model.getValueAt(i,1).toString());
            dish.setDishCategory(new DishCategoryDAO().getCategoryById(Integer.parseInt(model.getValueAt(i,2).toString())));
            dish.setPic(model.getValueAt(i,3).toString());
            dish.setCode(model.getValueAt(i,4).toString());
            dish.setUnit(model.getValueAt(i,5).toString());
            dish.setPrice(Double.parseDouble(model.getValueAt(i,6).toString()));
            dish.setStatus(model.getValueAt(i,7).toString());
        }

        new DishCategoryDAO().deleteList(dishCategories);
        model.removeRow(jTable.getSelectedRow());
    }

    @Override
    public void saveData() {
        List<Dish> dishes = new ArrayList<>();
        for(int i=rowLength;i<model.getRowCount();i++){
            int id = Integer.parseInt(model.getValueAt(i,0).toString());
            Dish dish = new Dish();
            dish.setId(id);
            dish.setName(model.getValueAt(i,1).toString());
            dish.setDishCategory(new DishCategoryDAO().getCategoryById(Integer.parseInt(model.getValueAt(i,2).toString())));
            dish.setPic(model.getValueAt(i,3).toString());
            dish.setCode(model.getValueAt(i,4).toString());
            dish.setUnit(model.getValueAt(i,5).toString());
            dish.setPrice(Double.parseDouble(model.getValueAt(i,6).toString()));
            dish.setStatus(model.getValueAt(i,7).toString());
        }
        new DishDAO().saveList(dishes);
    }

    @Override
    public void updateData() {
        List<Dish> dishes = new ArrayList<>();
        for(int i=0;i<model.getRowCount();i++){
            int id = Integer.parseInt(model.getValueAt(i,0).toString());
            Dish dish = new Dish();
            dish.setId(id);
            dish.setName(model.getValueAt(i,1).toString());
            dish.setDishCategory(new DishCategoryDAO().getCategoryById(Integer.parseInt(model.getValueAt(i,2).toString())));
            dish.setPic(model.getValueAt(i,3).toString());
            dish.setCode(model.getValueAt(i,4).toString());
            dish.setUnit(model.getValueAt(i,5).toString());
            dish.setPrice(Double.parseDouble(model.getValueAt(i,6).toString()));
            dish.setStatus(model.getValueAt(i,7).toString());
        }
        new DishDAO().updateList(dishes);
    }
}
