package userinterface.deskmanage;

import entity.Desk;
import entity.DishCategory;
import entitydatabase.DeskDAO;
import entitydatabase.DishCategoryDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DeskManagePanel extends ManagePanel {
    public DeskManagePanel(){
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
        columnData.add("餐台编号");
        columnData.add("座位数");
        columnData.add("状态");

        Vector rowData = new Vector<>();

        for(Desk desk: new DeskDAO().getList()){
            Vector single = new Vector();
            single.add(desk.getId());
            single.add(desk.getNo());
            single.add(desk.getSeating());
            single.add(desk.getStatus());
            rowData.add(single);
        }

        model.setDataVector(rowData,columnData);
        rowLength = model.getRowCount();
        jTable = new JTable(model);
    }

    @Override
    public void deleteLine() {
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

    @Override
    public void updateData() {
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

    @Override
    public void saveData() {
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
        for(int i=0;i < (model.getRowCount()-1);i++){
            if(name .equals(model.getValueAt(i,1).toString())){
                flag = false;
                JOptionPane.showMessageDialog(this,"餐台已存在","错误", JOptionPane.ERROR_MESSAGE);
            }

        }
        return flag;
    }
}
