import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by zibu on 2016/12/12.
 */
public class test extends JFrame{
    private JTable table;
    test(){
        setSize(400,300);
        setVisible(true);
        String[] cols = {"Name","Location"};
        String[][] data = new String[][] {{"Name","Location"}};
        JTable table = new JTable(new DefaultTableModel(data, cols));

        /*DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        //tableModel.setRowCount(0);// 清除原有行

        // 填充数据
        String[] arr=new String[3];
        arr[0]="11";
        arr[1]="11";
        arr[2]="11";

        // 添加数据到表格
        tableModel.addRow(arr);*/
        String[] date = new String[] {"Name1","Location1"};
        DefaultTableModel dm = (DefaultTableModel)(table.getModel());
        dm.addRow(date);
        add(table,BorderLayout.CENTER);
    }



    public static void main(String[] args){
        new test();
    }
}
