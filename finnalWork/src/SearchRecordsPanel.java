import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class SearchRecordsPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel,searchTextPanel,resultPanel;
    private JTextArea searchArea;
    private JButton sure;
    private JTable resultTable;
    private String[][] searchResult ;
    private DefaultTableModel tableModel;

    public SearchRecordsPanel(){
        setLayout(new BorderLayout(10,10));
        buildTitle();
        buildSearchArea();
        buildResultTable();
        setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
        tableModel = (DefaultTableModel) resultTable.getModel();
        this.add(titlePanel,BorderLayout.NORTH);
        this.add(searchTextPanel,BorderLayout.CENTER);
        this.add(resultPanel,BorderLayout.SOUTH);
    }

    //标题panel 创建
    private void buildTitle(){
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(500, 80));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        title = new JLabel("输入查询条件", SwingConstants.CENTER);
        title.setFont(new Font("Font.DIALOG", Font.BOLD, 28));
        titlePanel.add(title);
    }

    //搜索框区域创建
    private void buildSearchArea(){
        searchTextPanel =  new JPanel();
        searchTextPanel.setLayout(new BorderLayout(10,10));
        //按钮 创建
        JPanel surePanel =  new JPanel();
        sure = new JButton("确定");
        sure.addActionListener(new SureClickListener());
        surePanel.add(sure);
        //搜索框 创建
        searchArea = new JTextArea();
        //searchArea.setPreferredSize(new Dimension(400,300));
        //searchAreaPanel.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
        searchTextPanel.add(searchArea,BorderLayout.CENTER);
        searchTextPanel.add(surePanel,BorderLayout.SOUTH);

    }

    //搜索结果框创建
    private void buildResultTable(){
        resultPanel = new JPanel();
        String[][] data = {};
        String[] columnNames = {"学号", "姓名", "性别", "语文","数学","英语"};
        //table 初始化
        resultTable = new JTable(new DefaultTableModel(data, columnNames));
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        //每行宽度 设置
       /* for(int i=0;i<columnNames.length;i++){
            columnList[i] = table.getColumnModel().getColumn(i);
            columnList[i].setPreferredWidth(100);
            columnList[i].setMaxWidth(100);
            columnList[i].setMinWidth(100);
        }*/
        //table.setFillsViewportHeight(true);
        resultPanel.add(scrollPane);
        //得设置 这个scrollpane的长度才可以
        scrollPane.setPreferredSize(new Dimension(600,300));
    }

    //确认点击函数
    private class SureClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(searchArea.getText().equals("")){
                JOptionPane.showMessageDialog(null,"查询语句不可为空","",2);
            }else{
                //清空原有数据
                for(int i = 0 ;i<resultTable.getRowCount();i++){
                    tableModel.removeRow(i);
                }
                //TODO:判断是不是select 语句 防止sql注入
                DBopreation dbopreation = new DBopreation();
                searchResult = dbopreation.searchStudent(searchArea.getText());
                String arr[] = new String[6];
                for(int i = 0 ;i<searchResult.length;i++){
                    arr[0] = searchResult[i][0];
                    arr[1] = searchResult[i][1];
                    arr[2] = searchResult[i][2];
                    arr[3] = searchResult[i][3];
                    arr[4] = searchResult[i][4];
                    arr[5] = searchResult[i][5];
                    tableModel.addRow(arr);
                    resultTable.invalidate();
                }
            }
        }
    }
}
