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

        rowLength = model.getRowCount();
        model.setDataVector(rowData,columnData);
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
    }
}
