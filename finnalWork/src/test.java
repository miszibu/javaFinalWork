import java.sql.*;

/**
 * Created by sakura on 16/6/21.
 */
public class test {
    public static void main(String[] args){
        Connection con = null;
        Statement sta = null;
        ResultSet res = null;
        String sql ;
        String url = new String("jdbc:mysql://localhost:3306/student?characterEncoding=utf8&useSSL=false");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,"root","123456");
            sta = con.createStatement();
            sql = new String("select * from security");
            res = sta.executeQuery(sql);
            //System.out.println("name     sex     age");
            while(res.next()){
                System.out.print(res.getString("userName"));
                System.out.print(res.getString("password"));
            }
        }catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException");

        }catch (SQLException a){
            System.out.println("SQLException");
        }
        catch (Exception b){

        }
        finally {
            try{
                if(con != null)
                    con.close();
                if (sta != null)
                    sta.close();
                if(res != null)
                    res.close();
            }catch (Exception e){

            }
        }
    }
}