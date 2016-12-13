import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class HandleRecordsPanel extends JPanel{
    //私有控件初始化
    private JLabel title;
    private JPanel titlePanel,buttonPanel,tablePanel,operationPanel;
    private JLabel[] operationLabel = new JLabel[6];
    private JTextField[] operationTextField = new JTextField[6];
    private JButton sureButton,delButton,modifyButton;
    private TableColumn[] columnList = new TableColumn[6];
    private JTable table ;
    private int selectedRowIndex;
    private DefaultTableModel tableModel;

    /*String studentCodePattern = "/^[0-9]{1,6}$/";
    Pattern r = Pattern.compile(studentCodePattern);*/

    public HandleRecordsPanel(){
        // 第一个主内容布局
        //布局控件初始化
        //this.setSize(890,540);
        setLayout(new FlowLayout());
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        //对应panel处理
        buildTitlePanel();
        buildTablePanel();
        buildOperationPanel();
        buildButtonPanel();

        tableModel = (DefaultTableModel) table.getModel();
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
        //数据库获取 数据
        DBopreation dbopreation = new DBopreation();
        String[][] data = dbopreation.searchAllStudentInfo();
        tablePanel =  new JPanel();
        String[] columnNames = {"学号", "姓名", "性别", "语文","数学","英语"};
        //table 初始化 创建对应点击事件
        table = new JTable(new DefaultTableModel(data, columnNames));
        table.addMouseListener(new tableListener());
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

        operationTextField[0] = new JTextField(9);
        operationTextField[1] = new JTextField(9);
        operationTextField[2] = new JTextField(8);
        operationTextField[3] = new JTextField(3);
        operationTextField[4] = new JTextField(3);
        operationTextField[5] = new JTextField(3);


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
            //数据校验
            try{
                if(operationTextField[0].getText().length()>15){
                    //TODO:正则匹配 拿到书再做
                    return;
                }else if(operationTextField[1].getText().length()>10 || operationTextField[1].getText().length()<2){
                    JOptionPane.showMessageDialog(null,"名字长度不合法","",2);
                    return;
                }else if(!(operationTextField[2].getText().equals("male") || operationTextField[2].getText().equals("female"))){
                    JOptionPane.showMessageDialog(null, "性别不存在", "", 2);
                    return;
                }else if(Integer.valueOf(operationTextField[3].getText())>100 || Integer.valueOf(operationTextField[3].getText())<0){
                    JOptionPane.showMessageDialog(null,"语文成绩 超出范围","",2);
                    return;
                }else if(Integer.valueOf(operationTextField[4].getText())>100 || Integer.valueOf(operationTextField[4].getText())<0){
                    JOptionPane.showMessageDialog(null,"数学成绩 超出范围","",2);
                    return;
                }else if(Integer.valueOf(operationTextField[5].getText())>100 || Integer.valueOf(operationTextField[5].getText())<0){
                    JOptionPane.showMessageDialog(null,"英语成绩 超出范围","",2);
                    return;
                }
            }catch (NumberFormatException numEx){
                JOptionPane.showMessageDialog(null, "不得有空数据","",2);
                return;
            }
            if(e.getActionCommand().equals("确认")){
                try{
                    String[] arr=new String[6];
                    for(int i = 0;i<arr.length;i++) {
                        if(operationTextField[i].getText().equals("")){
                            JOptionPane.showMessageDialog(null, "内容不可有空");
                            return;
                        }
                        arr[i] = String.valueOf(operationTextField[i].getText());
                    }
                    DBopreation dbopreation = new DBopreation();
                    if(dbopreation.addStudentInfo(arr)){
                        // 添加数据到表格  更新表格
                        tableModel.addRow(arr);
                        table.invalidate();
                        JOptionPane.showMessageDialog(null, "添加成功");
                    }else{
                        JOptionPane.showMessageDialog(null, "添加失败","请重试",2);
                    }
                }catch (Exception ez){
                    System.out.print(ez.getMessage());
                }

            }else if(e.getActionCommand().equals("修改")){
                String[] modifyData = new String[columnList.length];
                for(int i = 0;i<columnList.length;i++){
                    modifyData[i] = operationTextField[i].getText();
                    if(operationTextField[i].getText().equals("")){
                        JOptionPane.showMessageDialog(null, "内容不可有空","",2);
                        return;
                    }
                }
                DBopreation dbopreation = new DBopreation();
                if(dbopreation.updateStudentInfo(modifyData)){
                    for(int i = 0;i<columnList.length;i++) {
                        table.setValueAt(operationTextField[i].getText(),selectedRowIndex,i);
                    }
                    JOptionPane.showMessageDialog(null, "修改成功");
                }else{
                    JOptionPane.showMessageDialog(null, "修改失败","请重试",2);
                }
            }else if(e.getActionCommand().equals("删除")){
                if(selectedRowIndex+1>=table.getRowCount()){
                    JOptionPane.showMessageDialog(null, "该行不存在","",2);
                    return ;
                }
                DBopreation dbopreation = new DBopreation();
                if(dbopreation.deleteStudentInfo((String)table.getValueAt(selectedRowIndex, 0))){
                    tableModel.removeRow(selectedRowIndex);
                    JOptionPane.showMessageDialog(null, "删除成功");
                }else{
                    JOptionPane.showMessageDialog(null, "删除失败","请重试",2);
                }
            }
        }
    }

    //表格 对应点击事件
    public class tableListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            // 鼠标点击时的处理
            selectedRowIndex = table.getSelectedRow(); // 取得用户所选单行
            for(int i = 0;i<columnList.length;i++){
                operationTextField[i].setText((String)table.getValueAt(selectedRowIndex, i));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // 鼠标按下时的处理
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 鼠标松开时的处理
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // 鼠标进入表格时的处理
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // 鼠标退出表格时的处理
        }
    }
}
