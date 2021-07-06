package userinterface.customermanage;

import edgeclass.CustomerEdge;
import entity.Customer;
import entitydatabase.CustomerDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

//客户管理界面
public class  CustomerManagePanel extends ManagePanel {
    public CustomerManagePanel(){
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
        columnData.add("姓名");
        columnData.add("性别");
        columnData.add("公司");
        columnData.add("电话");
        columnData.add("卡号");

        Vector rowData = new Vector<>();

        for(Customer customer: new CustomerDAO().getList()){
            Vector single = new Vector();
            single.add(customer.getId());
            single.add(customer.getName());
            single.add(customer.getSex());
            single.add(customer.getCompany());
            single.add(customer.getTel());
            single.add(customer.getCardID());
            rowData.add(single);
        }

        model.setDataVector(rowData,columnData);
        rowLength = model.getRowCount();
        jTable = new JTable(model);
    }

    @Override
    public void deleteLine() {
        CustomerEdge.deleteLine(jTable,model);
    }

    @Override
    public void updateData() {

        CustomerEdge.updateData(jTable,model);
    }

    @Override
    public void saveData() {
        CustomerEdge.saveData(jTable,rowLength,model);
    }

    @Override
    public void searchByRule() {
        CustomerEdge.searchByRule(labelContent,model,jTable,searchPanel,bottomPanel);
    }
}
