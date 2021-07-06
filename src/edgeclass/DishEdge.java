package edgeclass;

import entity.Dish;
import entitydatabase.DishCategoryDAO;
import entitydatabase.DishDAO;
import userinterface.ErrorPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class DishEdge {
        private static boolean checkNull(JTable jTable, DefaultTableModel model){
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
                ErrorPanel.ShowMessage("信息不完整");
            }
            return flag;
        }

        private static boolean checkConflict(JTable jTable, DefaultTableModel model) {
            boolean flag = true;
            int row = jTable.getSelectedRow();
            String name = model.getValueAt(row,1).toString();
            System.out.println("name = " + name);
            String code = model.getValueAt(row,4).toString();
            System.out.println("code = " + code);
            for(int i=0;i < model.getRowCount();i++){
                if((name .equals(model.getValueAt(i,1).toString())||code.equals(model.getValueAt(i,4).toString()))&&i!=row){
                    flag = false;
                    ErrorPanel.ShowMessage("菜品已存在");
                    break;
                }

            }
            return flag;
        }

        public static void saveData(JTable jTable,int rowLength,DefaultTableModel model) {
            if(checkNull(jTable,model)) {
                if(checkConflict(jTable, model)) {
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
            }
        }

        public static void deleteLine(JTable jTable, DefaultTableModel model) {
            if(checkNull(jTable, model)) {
                if(checkConflict(jTable, model)) {

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
            }
        }


        public static void updateData(JTable jTable,DefaultTableModel model) {
            if(checkNull(jTable, model)) {
                if(checkConflict(jTable, model)) {
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
            }
        }

        public static void searchByRule(String labelContent,DefaultTableModel model,JTable jTable,JPanel searchPanel,JPanel bottomPanel) {
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
                ErrorPanel.ShowMessage("未找到对应信息");
            }
        }

}
