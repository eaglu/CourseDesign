package edgeclass;

import entity.Customer;
import entity.Desk;
import entitydatabase.CustomerDAO;
import entitydatabase.DeskDAO;
import userinterface.ErrorPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomerEdge {
    private static boolean checkNull(JTable jTable, DefaultTableModel model){
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
            ErrorPanel.ShowMessage("信息不完整");
        }
        return flag;
    }

    private static boolean checkConflict(JTable jTable, DefaultTableModel model) {
        boolean flag = true;
        int row = jTable.getSelectedRow();
        String name = model.getValueAt(row,1).toString();
        String code = model.getValueAt(row,5).toString();
        for(int i=0;i < (model.getRowCount()-1);i++){
            if((name .equals(model.getValueAt(i,1).toString())||code.equals(model.getValueAt(i,5).toString()))&&i!=row){
                flag = false;
                ErrorPanel.ShowMessage("客户已存在");
                break;
            }

        }
        return flag;
    }

    public static void saveData(JTable jTable,int rowLength,DefaultTableModel model) {
        if(checkNull(jTable,model)) {
            if(checkConflict(jTable, model)) {
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
        }
    }

    public static void deleteLine(JTable jTable,DefaultTableModel model) {
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


    public static void updateData(JTable jTable,DefaultTableModel model) {
        if(checkNull(jTable, model)) {
            if(checkConflict(jTable, model)) {
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
        }
    }

    public static void searchByRule(String labelContent,DefaultTableModel model,JTable jTable,JPanel searchPanel,JPanel bottomPanel) {
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
            ErrorPanel.ShowMessage("未找到对应信息");
        }
    }
}
