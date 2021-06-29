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
        search.addActionListener(e->new DishSearchFrame());
    }
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
        List<Dish> dishes = new ArrayList<>();

        int[] rowList = jTable.getSelectedRows();
        for (int i : rowList) {
            Dish dish = new Dish();
            dish.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
            dish.setName(model.getValueAt(i,1).toString());
            dish.setDishCategory(new DishCategoryDAO().getCategoryById(Integer.parseInt(model.getValueAt(i,2).toString())));
            dish.setPic(model.getValueAt(i,3).toString());
            dish.setCode(model.getValueAt(i,4).toString());
            dish.setUnit(model.getValueAt(i,5).toString());
            dish.setPrice(Double.parseDouble(model.getValueAt(i,6).toString()));
            dish.setStatus(model.getValueAt(i,7).toString());
            dishes.add(dish);
        }

        new DishDAO().deleteList(dishes);
        model.removeRow(jTable.getSelectedRow());
    }

    @Override
    public void saveData() {
        List<Dish> dishes = new ArrayList<>();
        for(int i=rowLength;i<(model.getRowCount());i++){
            Dish dish = new Dish();
            dish.setName(model.getValueAt(i,1).toString());
            dish.setDishCategory(new DishCategoryDAO().getCategoryById(Integer.parseInt(model.getValueAt(i,2).toString())));
            dish.setPic(model.getValueAt(i,3).toString());
            dish.setCode(model.getValueAt(i,4).toString());
            dish.setUnit(model.getValueAt(i,5).toString());
            dish.setPrice(Double.parseDouble(model.getValueAt(i,6).toString()));
            dish.setStatus(model.getValueAt(i,7).toString());
            dishes.add(dish);
        }
        new DishDAO().saveList(dishes);
        List<Dish> dishes1 = new DishDAO().getList();
        for(int i=rowLength;i<model.getRowCount();i++){
            model.setValueAt(dishes1.get(i).getId(),rowLength,0);
        }
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
            dishes.add(dish);
        }
        new DishDAO().updateList(dishes);
    }

    @Override
    public void searchByRule() {
        boolean flag = false;
        for(int i=0;i<model.getRowCount();i++){
            if(model.getValueAt(i,0).toString().equals(labelContent)||model.getValueAt(i,1).equals(labelContent)||model.getValueAt(i,4).equals(labelContent)){
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
        System.out.println("name = " + name);
        String code = model.getValueAt(row,4).toString();
        System.out.println("code = " + code);
        for(int i=0;i < model.getRowCount()-1;i++){
            if(name .equals(model.getValueAt(i,1).toString())||code.equals(model.getValueAt(i,4).toString())){
                flag = false;
                JOptionPane.showMessageDialog(this,"菜品已存在","错误", JOptionPane.ERROR_MESSAGE);
            }

        }
        System.out.println(flag);
        return flag;
    }

    @Override
    protected boolean checkNull() {
        boolean flag = true;
        try{
            int row = jTable.getSelectedRow();
            for(int i= 1;i<model.getColumnCount();i++){
                if(i==3){
                    continue;
                }
                String value = model.getValueAt(row,i).toString();
                System.out.println("value = " + value);
                if (value.equals("")){
                    flag = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Check Failed");
            flag = false;
        }
        if(!flag){
            JOptionPane.showMessageDialog(this,"信息不完整","错误", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Check passed");
        return flag;
    }
}
