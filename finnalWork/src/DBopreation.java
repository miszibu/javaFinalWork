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
        int lineNum = 0;
        String[][] data = new String[100][6];
        String[][] returnData;
        try {
            String newSearchSql = sqlSelectAllStudent;
            res = statement.executeQuery(newSearchSql);
            while(res.next()){
                data[lineNum][0]=res.getString("stuId");
                data[lineNum][1]=res.getString("name");
                data[lineNum][2]=res.getString("gender");
                data[lineNum][3]=String.valueOf(res.getInt("chinese"));
                data[lineNum][4]=String.valueOf(res.getInt("english"));
                data[lineNum][5]=String.valueOf(res.getInt("math"));
                lineNum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        returnData = new String[lineNum][6];
        for (int i=0;i<lineNum;i++){
            returnData[i][0] = data[i][0];
            returnData[i][1] = data[i][1];
            returnData[i][2] = data[i][2];
            returnData[i][3] = data[i][3];
            returnData[i][4] = data[i][4];
            returnData[i][5] = data[i][5];
        }
        return  returnData;
    }

    //学生表 插入一个学生信息
    protected static Boolean addStudentInfo(String[] studentInfoArr){
        int state = 0;
        String newSql = sqlAddStudent;
        newSql +=" ('"+studentInfoArr[0]+"', '"+studentInfoArr[1]+"', '"+studentInfoArr[2]+"', '"+studentInfoArr[3]+"', '"+studentInfoArr[4]+"', '"+studentInfoArr[5]+"')";
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

    //导入studentinfo表  删除studentinfo表 再导入新数据
    protected static  Boolean deleteAndUpdatteStudentInfo(String[][] studentInfoArr,int length){
        int state1 = 0;
        int i = 0;
        String deleteSql = "Truncate table studentinfo";
        //删除studentinfo 表
        try {
            state1 = statement.executeUpdate(deleteSql);
            for (int j =0;j<length;j++){
                String updateSql=sqlAddStudent;
                updateSql +=" ('"+studentInfoArr[i][0]+"', '"+studentInfoArr[i][1]+"', '"+studentInfoArr[i][2]+"', '"+studentInfoArr[i][3]+"', '"+studentInfoArr[i][4]+"', '"+studentInfoArr[i][5]+"')";
                i++;
                statement.executeUpdate(updateSql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(state1==1){
            return true;
        }else{
            return false;
        }
    }

    //统计学生 人数
    protected  static int countStudentNum(){
        int studentNum = 0;
        String sqlCountStundentNum = "select count(*) as studentNum from studentinfo";
        try {
            res = statement.executeQuery(sqlCountStundentNum);
            res.next();
            studentNum = res.getInt("studentNum");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNum;
    }

    //统计学生的最高分1  最低分2 平均分0
    protected  static int[] statisticsGrade(int state){
        int[] highestGrade = new int[3];
        String sqlSearchHighestGrade = "select MAX(chinese) as chinese,MAX(english) as english,MAX(math) as math from studentinfo";
        String sqlSearchLowestGrade = "select MIN(chinese) as chinese,MIN(english) as english,AVG(math) as math from studentinfo";
        String sqlSearchAvgGrade = "select AVG(chinese) as chinese,AVG(english) as english,MIN(math) as math from studentinfo";
        try {
            if(state == 0){
                res = statement.executeQuery(sqlSearchAvgGrade);
            }else if(state == 1){
                res = statement.executeQuery(sqlSearchHighestGrade);
            }else if(state == 2){
                res = statement.executeQuery(sqlSearchLowestGrade);
            }
            res.next();
            highestGrade[0] = res.getInt("chinese");
            highestGrade[1] = res.getInt("english");
            highestGrade[2] = res.getInt("math");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highestGrade;
    }

    //根据 账号名字 查询密码
    protected static  String getPassword(String username){
        String password = "";
        String sqlSearchPassword = "select password from security where userName = '"+username+"'";
        try {
            res = statement.executeQuery(sqlSearchPassword);
            res.next();
            password = res.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    //更新密码
    protected static Boolean updatePassword(String username,String password){
        int state = 0;
        String sqlUpdatePassword = "update security set password = '"+password+"' where userName ='"+username+"'";
        try {
            state =  statement.executeUpdate(sqlUpdatePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(state==1){
            return true;
        }else{
            return false;
        }
    }

    //对指定sql语句进行查询
    protected static String[][] searchStudent(String sql){
        String[][] searchResult = new String[100][6];
        String[][] returnData ;
        int lineNum = 0;
        try {
            res = statement.executeQuery(sql);
            while(res.next()){
                searchResult[lineNum][0]=res.getString("stuId");
                searchResult[lineNum][1]=res.getString("name");
                searchResult[lineNum][2]=res.getString("gender");
                searchResult[lineNum][3]=String.valueOf(res.getInt("chinese"));
                searchResult[lineNum][4]=String.valueOf(res.getInt("english"));
                searchResult[lineNum][5]=String.valueOf(res.getInt("math"));
                lineNum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        returnData = new String[lineNum][6];
        for (int i = 0;i<lineNum;i++){
            returnData[i][0] = searchResult[i][0];
            returnData[i][1] = searchResult[i][1];
            returnData[i][2] = searchResult[i][2];
            returnData[i][3] = searchResult[i][3];
            returnData[i][4] = searchResult[i][4];
            returnData[i][5] = searchResult[i][5];
        }
        return returnData;
    }
    //TODO:销毁创建的连接 destory
}
