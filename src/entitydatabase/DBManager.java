package entitydatabase;

import java.sql.*;

public class DBManager {
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3307/restmanager?serverTimezone=GMT";
    private static final String user = "root";
    private static final String password = "123456";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn(){
        conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn,Statement st,ResultSet rs){
        try{
            if(rs!=null){
                rs.close();
            }
            if(st!=null){
                st.close();
            }
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}