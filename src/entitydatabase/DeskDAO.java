package entitydatabase;

import entity.Desk;
import entity.DishCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//对餐桌数据库进行操作
public class DeskDAO implements BaseDAO<Desk>{
    @Override
    public List<Desk> getList() {
        Connection conn = DBManager.getConn();
        String sql = "select * from desk order by id ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Desk> list = new ArrayList<>();
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            while (rs.next()){
                Desk desk = new Desk();
                desk.setId(rs.getInt(1));
                desk.setNo(rs.getString(2));
                desk.setSeating(rs.getInt(3));
                desk.setStatus(rs.getString(4));
                list.add(desk);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBManager.close(conn,ps,rs);
        }
        return list;
    }

    @Override
    public void saveList(List<Desk> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "insert into desk(no,seating,status) values(?,?,?)";
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (Desk desk : list) {
                ps.setString(1, desk.getNo());
                ps.setInt(2, desk.getSeating());
                ps.setString(3,desk.getStatus());
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
    public void deleteList(List<Desk> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "delete from desk where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Desk desk : list){
                ps.setInt(1,desk.getId());
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
    public void updateList(List<Desk> list) {
        Connection conn = DBManager.getConn();
        PreparedStatement ps = null;
        String sql = "update desk set no = ?,seating = ?,status = ? where id = ?";
        try{
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for(Desk desk : list){
                ps.setString(1,desk.getNo());
                ps.setInt(2, desk.getSeating());
                ps.setString(3,desk.getStatus());
                ps.setInt(4,desk.getId());
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
