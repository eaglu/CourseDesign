package entitydatabase;

import entity.Dish;
import entity.DishCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//对Dish实现数据库操作
public class DishDAO implements BaseDAO<Dish>{
    @Override
    public List<Dish> getList() {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dish order by id";
        List<Dish> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt(1));
                dish.setName(rs.getString(2));
                dish.setDishCategory(new DishCategoryDAO().getCategoryById(rs.getInt(3)));
                dish.setPic(rs.getString(4));
                dish.setCode(rs.getString(5));
                dish.setUnit(rs.getString(6));
                dish.setPrice(rs.getDouble(7));
                dish.setStatus(rs.getString(8));
                list.add(dish);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            DBManager.close(conn,ps,rs);
        }
        return list;
    }

    @Override
    public void saveList(List<Dish> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "insert into dish(name,categoryid,pic,code,unit,price,status) values(?,?,?,?,?,?,?)";

        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (Dish dish : list) {
                ps.setString(1, dish.getName());
                ps.setInt(2, dish.getDishCategory().getId());
                ps.setString(3,dish.getPic());
                ps.setString(4,dish.getCode());
                ps.setString(5,dish.getUnit());
                ps.setDouble(6,dish.getPrice());
                ps.setString(7,dish.getStatus());
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
    public void deleteList(List<Dish> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "delete from dish where id = ?";

        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try{
            for(Dish dish:list){
                ps.setInt(1,dish.getId());
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
    public void updateList(List<Dish> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "update dish set name = ?,categoryid = ?,pic = ?,code = ?,unit = ?,price = ?,status = ? where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (Dish dish : list) {
                ps.setString(1, dish.getName());
                ps.setInt(2, dish.getDishCategory().getId());
                ps.setString(3, dish.getPic());
                ps.setString(4, dish.getCode());
                ps.setString(5, dish.getUnit());
                ps.setDouble(6, dish.getPrice());
                ps.setString(7, dish.getStatus());
                ps.setInt(8, dish.getId());
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
}
