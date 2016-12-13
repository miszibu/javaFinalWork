import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.*;

/**
 * Created by zibu on 2016/12/13.
 */
public class DBopreation {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet res = null;
    private static String url = new String("jdbc:mysql://localhost:3306/student?characterEncoding=utf8&useSSL=false");
   /*原sql数据*/
    private static String sqlSearhAccount = new String("select * from security");
    private static String sqlSelectAllStudent = new String ("select * from studentinfo");
    private static String sqlAddStudent = new String("insert into studentinfo values");
    private static String sqlUpdateStudent = new String("update studentinfo set ");
    private static String sqlDeleteStudent = new String("delete from studentinfo where ");
    protected  DBopreation(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"root","123456");
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //登录 数据查询 返回true or false
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

    //查询所有学生信息
    protected static String[][] searchAllStudentInfo(){
        int i = 0;
        //TODO:数据长度 没有动态
        String[][] data = new String[4][7];
        try {
            String newSearchSql = sqlSelectAllStudent;
            res = statement.executeQuery(newSearchSql);
            while(res.next()){
                data[i][0]=res.getString("stuId");
                data[i][1]=res.getString("name");
                data[i][2]=res.getString("gender");
                data[i][3]=String.valueOf(res.getInt("chinese"));
                data[i][4]=String.valueOf(res.getInt("english"));
                data[i][5]=String.valueOf(res.getInt("math"));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  data;
    }

    //学生表 插入一个学生信息
    protected static Boolean addStudentInfo(String[] studentInfoArr){
        int state = 0;
        String newSql = sqlAddStudent;
        newSql +=" ('"+studentInfoArr[0]+"', '"+studentInfoArr[1]+"', '"+studentInfoArr[2]+"', '"+studentInfoArr[3]+"', '"+studentInfoArr[4]+"', '"+studentInfoArr[5]+"')";
        System.out.println(newSql);
        try {
            state = statement.executeUpdate(newSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(state==1){
            return true;
        }else{
            return false;
        }
    }

    //学生表 修改一个学生信息
    protected static  Boolean updateStudentInfo(String[] studentInfoArr){
        int state = 0;
        String newSql = sqlUpdateStudent;
        //根据stdID 去查找
        newSql +="stuId = "+"'"+studentInfoArr[0]+"',"+"name = "+"'"+studentInfoArr[1]+"',"+"gender = "+"'"+studentInfoArr[2]+"',"+"chinese = "+"'"+studentInfoArr[3]+"',"+"english = "+"'"+studentInfoArr[4]+"',"+"math = "+"'"+studentInfoArr[5]+"'";
        newSql += "where stuId = '"+studentInfoArr[0]+"'";
        try {
            state = statement.executeUpdate(newSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(state==1){
            return true;
        }else{
            return false;
        }
    }

    //学生表 删除一个学生信息
    protected static  Boolean deleteStudentInfo(String studentId){
        int state = 0;
        String newSql = sqlDeleteStudent;
        newSql +="stuId = '"+studentId+"'";
        //根据stdID 去查找 并删除
        try {
            state = statement.executeUpdate(newSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(state==1){
            return true;
        }else{
            return false;
        }
    }
    //TODO:销毁创建的连接 destory
}
