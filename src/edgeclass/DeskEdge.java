package edgeclass;

import entity.Desk;
import entitydatabase.DeskDAO;
import userinterface.ErrorPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

//中间层，用于实现界面层与服务层之间的操作
public class DeskEdge {
    //对界面层数据进行查
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
        for(int i=0;i < (model.getRowCount());i++){
            if(name .equals(model.getValueAt(i,1).toString())&&i!=row){
                flag = false;
                ErrorPanel.ShowMessage("餐台已存在");
                break;
            }

        }
        return flag;
    }
    //保存数据
    public static void saveData(JTable jTable,int rowLength,DefaultTableModel model) {
        if(checkNull(jTable,model)) {
            if(checkConflict(jTable, model)) {
                List<Desk> desks = new ArrayList<>();
                for(int i=rowLength;i<model.getRowCount();i++){
                    Desk desk = new Desk();
                    desk.setNo(model.getValueAt(i,1).toString());
                    desk.setSeating(Integer.parseInt(model.getValueAt(i,2).toString()));
                    desk.setStatus(model.getValueAt(i,3).toString());
                    desks.add(desk);
                }
                new DeskDAO().saveList(desks);
                List<Desk> desks1 = new DeskDAO().getList();
                for(int i=rowLength;i<model.getRowCount();i++){
                    model.setValueAt(desks1.get(i).getId(),rowLength,0);
                }
            }
        }
    }
    //删除数据
    public static void deleteLine(JTable jTable,DefaultTableModel model) {
                List<Desk> desks = new ArrayList<>();

                int[] rowList = jTable.getSelectedRows();
                for (int j : rowList) {
                    Desk desk = new Desk();
                    desk.setId(Integer.parseInt(model.getValueAt(j, 0).toString()));
                    desk.setNo(model.getValueAt(j, 1).toString());
                    desk.setSeating(Integer.parseInt(model.getValueAt(j, 2).toString()));
                    desk.setStatus(model.getValueAt(j,3).toString());
                    desks.add(desk);
                }

                new DeskDAO().deleteList(desks);
                model.removeRow(jTable.getSelectedRow());
    }

    //修改数据
    public static void updateData(JTable jTable,DefaultTableModel model) {
        if(checkNull(jTable, model)) {
            if(checkConflict(jTable, model)) {
                List<Desk> desks = new ArrayList<>();
                for(int i=0;i<model.getRowCount();i++){
                    int id = Integer.parseInt(model.getValueAt(i,0).toString());
                    Desk desk = new Desk();
                    desk.setId(id);
                    desk.setNo((String) model.getValueAt(i,1));
                    desk.setSeating(Integer.parseInt(model.getValueAt(i,2).toString()));
                    desk.setStatus(model.getValueAt(i,3).toString());
                    desks.add(desk);
                }
                new DeskDAO().updateList(desks);
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
