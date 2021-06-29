package entitydatabase;

import entity.Admin;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements BaseDAO<Admin>{
    @Override
    public List<Admin> getList() {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from admin order by id";
        List<Admin> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while(rs.next()){
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                list.add(admin);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn,ps,rs);
        }
        return list;
    }

    @Override
    public void saveList(List<Admin> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "insert into admin(username,password) values (?,?)";
        try{
            ps = conn.prepareStatement(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Admin admin:list){
                ps.setString(1,hashCode(admin.getUsername()));
                ps.setString(2,hashCode(admin.getPassword()));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteList(List<Admin> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "delete from admin where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Admin admin : list){
                ps.setInt(1,admin.getId());
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
    public void updateList(List<Admin> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "update admin set username = ?,password = ? where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Admin admin : list){
                ps.setString(1,admin.getUsername());
                ps.setString(2, admin.getPassword());
                ps.setInt(3,admin.getId());
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
    public void save(Admin object) {

    }

    @Override
    public void delete(Admin object) {

    }

    @Override
    public void update(Admin object) {

    }

    public static String hashCode(String text){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] result = md.digest();
            return new BigInteger(1,result).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
