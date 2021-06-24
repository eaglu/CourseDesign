package entitydatabase;

import entity.Dish;
import entity.DishCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDAO implements BaseDAO<Dish>{

    public Dish getById(int id){
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dish where id = ?";
        return getDish(id, conn, ps, rs, sql);
    }

    private Dish getDish(int id, Connection conn, PreparedStatement ps, ResultSet rs, String sql) {
        Dish dish = new Dish();
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            rs.next();
            dish.setId(rs.getInt("id"));
            dish.setName(rs.getString("name"));
            dish.setDishCategory(new DishCategoryDAO().getCategoryById(rs.getInt("categoryid")));
            dish.setPic(rs.getString("pic"));
            dish.setCode(rs.getString("code"));
            dish.setUnit(rs.getString("unit"));
            dish.setPrice(rs.getDouble("price"));
            dish.setStatus(rs.getString("status"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn,ps,rs);
        }
        return dish;
    }

    public Dish getByCode(int code){
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dish where code = ?";
        return getDish(code, conn, ps, rs, sql);
    }

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

    @Override
    public void save(Dish object) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "insert into dish(name,categoryid,pic,code,unit,price,status) values(?,?,?,?,?,?,?)";

        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
                ps.setString(1, object.getName());
                ps.setInt(2, object.getDishCategory().getId());
                ps.setString(3,object.getPic());
                ps.setString(4,object.getCode());
                ps.setString(5,object.getUnit());
                ps.setDouble(6,object.getPrice());
                ps.setString(7,object.getStatus());
                ps.executeUpdate();
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
    public void delete(Dish object) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "delete from dish where id = ?";

        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try{
                ps.setInt(1,object.getId());
                ps.executeUpdate();
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
    public void update(Dish object) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "update dish set name = ?,categoryid = ?,pic = ?,code = ?,unit = ?,price = ?,status = ? where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
                ps.setString(1, object.getName());
                ps.setInt(2, object.getDishCategory().getId());
                ps.setString(3, object.getPic());
                ps.setString(4, object.getCode());
                ps.setString(5, object.getUnit());
                ps.setDouble(6, object.getPrice());
                ps.setString(7, object.getStatus());
                ps.setInt(8, object.getId());
                ps.executeUpdate();
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
