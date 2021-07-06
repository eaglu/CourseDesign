package entitydatabase;

import entity.Admin;
import entity.DishCategory;

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

//对Admin表进行操作
public class AdminDAO implements BaseDAO<Admin>{

    public Admin getByUsername(String username){
        Connection conn = DBManager.getConn();
        PreparedStatement ps;
        ResultSet rs = null;
        String sql = "select * from admin where username = ?";
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            return null;
        }
        Admin admin = new Admin();
        try{
            rs.next();
            admin.setUsername(rs.getString("username"));
            admin.setPassword(rs.getString("password"));
        } catch (SQLException throwables) {
            return null;
        }
        return  admin;
    }

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

    //保存单个
    public void save(Admin object) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "insert into admin(username,password) values (?,?)";
        try{
            ps = conn.prepareStatement(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps.setString(1,hashCode(object.getUsername()));
            ps.setString(2,hashCode(object.getPassword()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //对数据进行加密
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
