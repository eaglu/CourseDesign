package userinterface.deskmanage;

import edgeclass.DeskEdge;
import entity.Desk;
import entitydatabase.DeskDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"可用","不可用"});
        jTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));
    }

    @Override
    public void deleteLine() {
        DeskEdge.deleteLine(jTable,model);
    }

    @Override
    public void updateData() {
        DeskEdge.updateData(jTable,model);
    }

    @Override
    public void saveData() {
        DeskEdge.saveData(jTable,rowLength,model);
    }

    @Override
    public void searchByRule() {
        DeskEdge.searchByRule(labelContent,model,jTable,searchPanel,bottomPanel);
    }

}
