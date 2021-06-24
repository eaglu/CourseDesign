package entitydatabase;

import entity.Admin;
import entity.Customer;
import entity.Desk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements BaseDAO<Customer> {
    @Override
    public List<Customer> getList() {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from customer order by id = ?";
        List<Customer> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while(rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setSex(rs.getString("sex"));
                customer.setCompany(rs.getString("company"));
                customer.setTel(rs.getString("tel"));
                customer.setCardID(rs.getString("cardID"));
                list.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn,ps,rs);
        }
        return list;
    }

    @Override
    public void saveList(List<Customer> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "insert into customer(name,sex,company,tel,cardID) values(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (Customer customer : list) {
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSex());
                ps.setString(3,customer.getCompany());
                ps.setString(4,customer.getTel());
                ps.setString(5,customer.getCardID());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void deleteList(List<Customer>list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "delete from customer where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Customer customer : list){
                ps.setInt(1,customer.getId());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void updateList(List<Customer> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "update customer set name = ?,sex = ?,company = ?,tel = ?,cardID = ? where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Customer customer : list){
                ps.setString(1,customer.getName());
                ps.setString(2, customer.getSex());
                ps.setString(3,customer.getCompany());
                ps.setString(4,customer.getTel());
                ps.setString(5,customer.getCardID());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                ps.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    @Override
    public void save(Customer object) {

    }

    @Override
    public void delete(Customer object) {

    }

    @Override
    public void update(Customer object) {

    }
}
