import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class HandleRecordsPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel,contentPanel,buttonPanel;
    private JButton sureButton,delButton,modifyButton;
    private TableColumn[] columnList = new TableColumn[6];
    public HandleRecordsPanel(){
        // 第一个主内容布局
        //布局控件初始化
        //this.setSize(890,540);
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        contentPanel = new JPanel();

        buildTitlePanel();
        buildButtonPanel();
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
        contentPanel.add(scrollPane);
        //宽度设置没有起效果 center 就那么大 要把东西也包括进来 方可
        contentPanel.setPreferredSize(new Dimension(800,300));
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
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
