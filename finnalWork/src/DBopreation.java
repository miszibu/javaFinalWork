import java.sql.*;

/**
 * Created by zibu on 2016/12/13.
 */
public class DBopreation {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet res = null;
    private static String url = new String("jdbc:mysql://localhost:3306/student?characterEncoding=utf8&useSSL=false");
    private static String sqlSearhAccount = new String("select * from security");

    protected  DBopreation(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"root","123456");
            statement = connection.createStatement();
            System.out.println("aaaa");
            //login();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected static Boolean login(String userName ,String password){
        try {
            String newSearchSql = sqlSearhAccount;
            newSearchSql+=" where userName='"+userName+"'and password='"+password+"'";
            res = statement.executeQuery(newSearchSql);
            while(res.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  false;
    }

    //TODO:销毁创建的连接 destory
}
