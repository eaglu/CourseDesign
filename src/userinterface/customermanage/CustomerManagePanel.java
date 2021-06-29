package userinterface.customermanage;

import entity.Customer;
import entity.Desk;
import entity.DishCategory;
import entitydatabase.CustomerDAO;
import entitydatabase.DeskDAO;
import entitydatabase.DishCategoryDAO;
import userinterface.ManagePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CustomerManagePanel extends ManagePanel {
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
        List<Customer> customers = new ArrayList<>();

        int[] rowList = jTable.getSelectedRows();
        for (int j : rowList) {
            Customer customer = new Customer();
            customer.setId(Integer.parseInt(model.getValueAt(j, 0).toString()));
            customer.setName(model.getValueAt(j, 1).toString());
            try{
                customer.setSex(model.getValueAt(j,2).toString());
                customer.setCompany(model.getValueAt(j,3).toString());
                customer.setTel(model.getValueAt(j,4).toString());
            } catch (Exception e) {
            }
            customer.setCardID(model.getValueAt(j,5).toString());
            customers.add(customer);
        }

        new CustomerDAO().deleteList(customers);
        model.removeRow(jTable.getSelectedRow());
    }

    @Override
    public void updateData() {
        List<Customer> customers = new ArrayList<>();
        for(int i=0;i<model.getRowCount();i++){
            Customer customer = new Customer();
            customer.setId(Integer.parseInt(model.getValueAt(i, 0).toString()));
            customer.setName(model.getValueAt(i, 1).toString());
            try {
                customer.setSex(model.getValueAt(i, 2).toString());
                customer.setCompany(model.getValueAt(i, 3).toString());
                customer.setTel(model.getValueAt(i, 4).toString());
            }catch (Exception e){}
            customer.setCardID(model.getValueAt(i,5).toString());
            customers.add(customer);
        }
        new CustomerDAO().updateList(customers);
    }

    @Override
    public void saveData() {
        List<Customer> customers = new ArrayList<>();
        for(int i=rowLength;i<model.getRowCount();i++){
            Customer customer = new Customer();
            customer.setName(model.getValueAt(i, 1).toString());;
            try{
                customer.setSex(model.getValueAt(i,2).toString());
                customer.setCompany(model.getValueAt(i,3).toString());
                customer.setTel(model.getValueAt(i,4).toString());
            } catch (Exception e) {
            }
            customer.setCardID(model.getValueAt(i,5).toString());
            customers.add(customer);
        }
        new CustomerDAO().saveList(customers);
        List<Customer> customers1 = new CustomerDAO().getList();
        for(int i=rowLength;i<model.getRowCount();i++){
            model.setValueAt(customers1.get(i).getId(),rowLength,0);
        }
    }

    @Override
    public void searchByRule() {
        boolean flag = false;
        for(int i=0;i<model.getRowCount();i++){
            if(model.getValueAt(i,0).toString().equals(labelContent)||model.getValueAt(i,5).equals(labelContent)){
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
    protected boolean checkNull() {
        boolean flag = true;
        try{
            int row = jTable.getSelectedRow();
            String value = model.getValueAt(row,1).toString();
            if(value.equals("")){
                flag = false;
            }
            value = model.getValueAt(row,5).toString();
            if(value.equals("")){
                flag = false;
            }
        } catch (Exception e) {
            System.out.println("Check Failed");
        }
        if(!flag){
            JOptionPane.showMessageDialog(this,"信息不全","错误", JOptionPane.ERROR_MESSAGE);
        }
        return flag;
    }

    @Override
    protected boolean checkConflict() {
        boolean flag = true;
        int row = jTable.getSelectedRow();
        String name = model.getValueAt(row,1).toString();
        String code = model.getValueAt(row,5).toString();
        for(int i=0;i < (model.getRowCount()-1);i++){
            if((name .equals(model.getValueAt(i,1).toString())||code.equals(model.getValueAt(i,5).toString()))&&i!=row){
                flag = false;
                JOptionPane.showMessageDialog(this,"客户已存在","错误", JOptionPane.ERROR_MESSAGE);
                break;
            }

        }
        return flag;
    }
}
