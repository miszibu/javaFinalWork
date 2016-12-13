import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;


@SuppressWarnings("serial")
public class SearchRecordsPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel;
    private JTextArea searchArea;
    private JButton sure;
    private JTable resultTable;

    public SearchRecordsPanel(){
        setLayout(new BorderLayout(10,10));
        buildTitle();
        buildSearchArea();
        this.add(titlePanel,BorderLayout.NORTH);
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

    private void buildSearchArea(){

    }
}
