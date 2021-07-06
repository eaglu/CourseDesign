package entitydatabase;

import entity.DishCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishCategoryDAO implements BaseDAO<DishCategory> {

    //通过id获取dishcategory实例
    public DishCategory getCategoryById(int id){
        Connection conn = DBManager.getConn();
        PreparedStatement ps;
        ResultSet rs = null;
        String sql = "select * from dishcategory where id = ?";
        DishCategory dishCategory = new DishCategory();
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getDishCategory(rs, dishCategory);
    }

    private DishCategory getDishCategory(ResultSet rs, DishCategory dishCategory) {
        try{
            rs.next();
            dishCategory.setId(rs.getInt("id"));
            dishCategory.setName(rs.getString("name"));
            dishCategory.setDescribe(rs.getString("describ"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  dishCategory;
    }



    private DishCategory getDishCategory(String name, Connection conn, ResultSet rs, String sql) {
        PreparedStatement ps;
        DishCategory dishCategory = new DishCategory();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getDishCategory(rs, dishCategory);
    }

    public DishCategory getCategoryByDescribe(String describe){
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dishcategory where describ = ?";
        return getDishCategory(describe, conn, rs, sql);
    }

    @Override
    public List<DishCategory> getList() {
        Connection conn = DBManager.getConn();
        String sql = "select * from dishcategory order by id ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DishCategory> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            while (rs.next()){
                DishCategory dishCategory = new DishCategory();
                dishCategory.setId(rs.getInt(1));
                dishCategory.setName(rs.getString(2));
                dishCategory.setDescribe(rs.getString(3));
                list.add(dishCategory);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn,ps,rs);
        }
        return list;
    }

    @Override
    public void saveList(List<DishCategory> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "insert into dishcategory(name,describ) values(?,?)";
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (DishCategory dishCategory : list) {
                ps.setString(1, dishCategory.getName());
                ps.setString(2, dishCategory.getDescribe());
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
    public void deleteList(List<DishCategory> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "delete from dishcategory where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(DishCategory dishCategory : list){
                ps.setInt(1,dishCategory.getId());
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
    public void updateList(List<DishCategory> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "update dishcategory set name = ?,describ = ? where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(DishCategory dishCategory : list){
                ps.setString(1,dishCategory.getName());
                ps.setString(2, dishCategory.getDescribe());
                ps.setInt(3,dishCategory.getId());
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


    public static int getRowCount() {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dishcategory";
        int rowCount = 0;
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rowCount = rs.getRow();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn, ps, rs);
        }
        return rowCount;
    }

    public static int getColumnCount() {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dishcategory";
        int columnCount = 0;
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            columnCount = rs.getRow();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn, ps, rs);
        }
        return columnCount;
    }

}
