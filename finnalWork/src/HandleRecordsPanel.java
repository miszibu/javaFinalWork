import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class HandleRecordsPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel,buttonPanel,tablePanel,operationPanel;
    private JLabel[] operationLabel = new JLabel[6];
    private JTextField[] operationTextField = new JTextField[6];
    private JButton sureButton,delButton,modifyButton;
    private TableColumn[] columnList = new TableColumn[6];
    public HandleRecordsPanel(){
        // 第一个主内容布局
        //布局控件初始化
        //this.setSize(890,540);
        setLayout(new FlowLayout());

        titlePanel = new JPanel();
        buttonPanel = new JPanel();

        buildTitlePanel();
        buildTablePanel();
        buildOperationPanel();
        buildButtonPanel();

        //加入主panel 布局
        this.add(titlePanel);
        this.add(tablePanel);
        this.add(operationPanel);
        this.add(buttonPanel);
    }

    //标题panel设置
    private void buildTitlePanel(){
        //titlePanel 初始化
        titlePanel.setLayout(new BorderLayout(10,10));
        titlePanel.setPreferredSize(new Dimension(500, 80));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        title = new JLabel("学生信息", SwingConstants.CENTER);
        title.setFont(new Font("Font.DIALOG", Font.BOLD, 28));
        titlePanel.add(title);
    }

    //表格panel 设置
    private void buildTablePanel(){
        tablePanel =  new JPanel();
        //TODO：真实数据获取
        String[] columnNames = {"学号", "姓名", "性别", "语文","数学","英语"};
        Object[][] data = {
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
                {"10000001", "aaa", "female", "87","70","92"},
        };
        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        //每行宽度 设置
        for(int i=0;i<columnList.length;i++){
            columnList[i] = table.getColumnModel().getColumn(i);
            columnList[i].setPreferredWidth(100);
            columnList[i].setMaxWidth(100);
            columnList[i].setMinWidth(100);
        }
        //table.setFillsViewportHeight(true);
        tablePanel.add(scrollPane);
        //得设置 这个scrollpane的长度才可以
        scrollPane.setPreferredSize(new Dimension(600,300));
    }

    //操作框panel设置
    private void buildOperationPanel(){
        operationPanel =  new JPanel();
        setLayout(new FlowLayout());
        operationLabel[0] = new JLabel("学号:");
        operationLabel[1] = new JLabel("姓名:");
        operationLabel[2] = new JLabel("性别:");
        operationLabel[3] = new JLabel("语文:");
        operationLabel[4] = new JLabel("数学:");
        operationLabel[5] = new JLabel("英语:");

        operationTextField[0] = new JTextField(5);
        operationTextField[1] = new JTextField(5);
        operationTextField[2] = new JTextField(5);
        operationTextField[3] = new JTextField(5);
        operationTextField[4] = new JTextField(5);
        operationTextField[5] = new JTextField(5);


        for (int i = 0;i<operationLabel.length;i++){
            operationPanel.add(operationLabel[i]);
            operationPanel.add(operationTextField[i]);
        }
    }

    //按钮panel设置
    private void buildButtonPanel(){
        buttonPanel.setPreferredSize(new Dimension(600, 140));
        sureButton = new JButton("确认");
        delButton = new JButton("修改");
        modifyButton = new JButton("删除");

        ButtonClick buttonClick = new ButtonClick();
        sureButton.addActionListener(buttonClick);
        delButton.addActionListener(buttonClick);
        modifyButton.addActionListener(buttonClick);

        buttonPanel.add(sureButton);
        buttonPanel.add(delButton);
        buttonPanel.add(modifyButton);
    }

    //底部按钮 点击事件
    private class ButtonClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("确认")){
                System.out.print(1);
                //TODO
            }else if(e.getActionCommand().equals("修改")){
                System.out.print(2);
                //TODO
            }else if(e.getActionCommand().equals("删除")){
                System.out.print(3);
                //TODO
            }
        }
    }
}
