import com.sun.xml.internal.fastinfoset.util.CharArray;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * Created by zibu on 2016/12/9.
 */
public class MainLayout extends JFrame{
    //布局 私有变量初始化
    private int WINDOW_WIDTH = 1000;
    private int WINDOW_HEIGHT = 600;
    //主体 控件
    private JPanel navigatorPanel,defaultPanel;
    private JLabel handleRecordsLabel,searchRecordsLabel,resetPasswordLabel;
    private SearchRecordsPanel searchRecordsPanel;
    private HandleRecordsPanel handleRecordsPanel;
    private ResetPasswordPanel resetPasswordPanel;
    //导航栏
    private JMenuBar menuBar;
    private JMenu fileMenu, statisticsMenu ,systemLookMenu;
    private JMenu statisticsGradeMenu ;
    private JMenuItem statisticsTotalStudentMenu;
    private JMenuItem[] statisticsGradeSubMenu =  new JMenuItem[3];
    private JMenuItem[] fileMenuItem = new JMenuItem[3];
    private JSeparator separatorLine =  new JSeparator();
    private JRadioButtonMenuItem[] systemStyleItem = new JRadioButtonMenuItem[3];
    ButtonGroup systemStyleGroup =  new ButtonGroup();
    //多线程 和 进度条控制
    private Thread progressBarControlThread;
    ProgressBar progressBar;
    int progressBarCount = 0;

    public MainLayout(){
        /*界面信息初始化*/
        this.setTitle("班级信息管理");
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setLayout(new BorderLayout(10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //导航栏初始化
        buildMenuBar();
        // 创建panel
        buildNavigatorPanel();
        buildOtherPanels();
        buildDefaultPanel();
        //布局设置
        setJMenuBar(menuBar);
        this.add(navigatorPanel, BorderLayout.WEST);
        //this.add(searchRecordsPanel, BorderLayout.CENTER);
        //this.add(handleRecordsPanel, BorderLayout.CENTER);
        this.add(defaultPanel,BorderLayout.CENTER);
        this.setVisible(true);
    }

    //创建 菜单栏内容
    private void buildMenuBar(){
        UIManager.put("Menu.font", new Font(Font.DIALOG, Font.PLAIN, 12));
        UIManager.put("MenuItem.font", new Font(Font.DIALOG, Font.PLAIN, 12));

        menuBar = new JMenuBar();
        //文件菜单栏
        fileMenu = new JMenu("文件");
        fileMenuItem[0] = new JMenuItem("从文件导入");
        fileMenuItem[0].addActionListener(new FileImport());
        fileMenuItem[1] = new JMenuItem("导出到文件");
        fileMenuItem[1].addActionListener(new FileExport());
        fileMenuItem[2] = new JMenuItem("退出系统");
        fileMenuItem[2].addActionListener(new ExitSystem());
        fileMenu.add(fileMenuItem[0]);
        fileMenu.add(fileMenuItem[1]);
        fileMenu.add(separatorLine);
        fileMenu.add(fileMenuItem[2]);

        //统计菜单栏
        statisticsMenu = new JMenu("统计");
        statisticsGradeMenu =  new JMenu("各科成绩");
        statisticsTotalStudentMenu = new JMenuItem("学生总人数");
        statisticsTotalStudentMenu.addActionListener(new StudentNumCount());
        StatisticsGradeListener statisticsGradeListener = new StatisticsGradeListener();
        statisticsGradeSubMenu[0] = new JMenuItem("各门课程平均分");
        statisticsGradeSubMenu[1] = new JMenuItem("各门课程最高分");
        statisticsGradeSubMenu[2] = new JMenuItem("各门课程最低分");
        for(int i =0;i<statisticsGradeSubMenu.length;i++){
            statisticsGradeSubMenu[i].addActionListener(statisticsGradeListener);
            statisticsGradeMenu.add(statisticsGradeSubMenu[i]);
        }
        statisticsMenu.add(statisticsGradeMenu);
        statisticsMenu.add(statisticsTotalStudentMenu);

        //系统外观菜单栏
        systemLookMenu = new JMenu("系统外观");
        systemStyleItem[0] = new JRadioButtonMenuItem("Metal风格",true);//默认选中
        systemStyleItem[1] = new JRadioButtonMenuItem("Motif风格");
        systemStyleItem[2] = new JRadioButtonMenuItem("Windows风格");
        SystemStyleChangeListener itemListener = new SystemStyleChangeListener();
        for(int i = 0 ;i<systemStyleItem.length;i++){
            //点击事件 加入按钮组 加入menu
            systemStyleItem[i].addActionListener(itemListener);
            systemLookMenu.add(systemStyleItem[i]);
            systemStyleGroup.add(systemStyleItem[i]);
        }

        //menubar添加
        menuBar.add(fileMenu);
        menuBar.add(statisticsMenu);
        menuBar.add(systemLookMenu);
    }

    //创建 布局左侧 导航栏
    private void buildNavigatorPanel() {
        // create a panel for navigator labels
        navigatorPanel = new JPanel();

        //边框设置
        Border insideBorder = BorderFactory.createEmptyBorder(20, 0, 0, 0);
        Border outsideBorder = BorderFactory.createLoweredBevelBorder();
        navigatorPanel.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

        //设置导航栏固定大小
        navigatorPanel.setPreferredSize(new Dimension(100, 500));

        // 给panel设置label
        handleRecordsLabel = new JLabel("处理记录", SwingConstants.CENTER);
        handleRecordsLabel.setPreferredSize(new Dimension(100, 30));
        handleRecordsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // s设置光标手势

        searchRecordsLabel = new JLabel("查询记录", SwingConstants.CENTER);
        searchRecordsLabel.setPreferredSize(new Dimension(100, 30));
        searchRecordsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 设置光标手势

        resetPasswordLabel = new JLabel("重置密码", SwingConstants.CENTER);
        resetPasswordLabel.setPreferredSize(new Dimension(100, 30));
        resetPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 设置光标手势

        // panel添加label控件
        navigatorPanel.add(handleRecordsLabel);
        navigatorPanel.add(searchRecordsLabel);
        navigatorPanel.add(resetPasswordLabel);

        // 导航label 设置鼠标点击事件
        handleRecordsLabel.addMouseListener(new LabelClick());
        searchRecordsLabel.addMouseListener(new LabelClick());
        resetPasswordLabel.addMouseListener(new LabelClick());
    }

    //创建 布局主体内容
    private void buildOtherPanels() {
        // 创建内容panel 默认显示一个
        searchRecordsPanel = new SearchRecordsPanel();
        searchRecordsPanel.setVisible(false);
        //默认显示处理记录panel
        handleRecordsPanel = new HandleRecordsPanel();
        handleRecordsPanel.setVisible(false);
        //重置密码panel 默认false
        resetPasswordPanel = new ResetPasswordPanel();
        resetPasswordPanel.setVisible(false);
    }

    //创建 默认显示panel
    private void buildDefaultPanel(){
        defaultPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("background.jpg");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, defaultPanel.getWidth(),
                        defaultPanel.getHeight(), icon.getImageObserver());
            }
        };
        defaultPanel.setVisible(true);
    }

    //左侧导航栏 点击事件函数
    private class LabelClick extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            defaultPanel.setVisible(false);
            if (e.getSource() == handleRecordsLabel) {
                // 将被点击label设置为红色
                resetPasswordLabel.setForeground(Color.black);
                searchRecordsLabel.setForeground(Color.black);
                handleRecordsLabel.setForeground(Color.red);
                // 显示第一个panel 并居中显示
                if (!handleRecordsPanel.isVisible()) {
                    MainLayout.this.add(handleRecordsPanel, BorderLayout.CENTER);
                    handleRecordsPanel.setVisible(true);
                }
                searchRecordsPanel.setVisible(false);
                resetPasswordPanel.setVisible(false);
            } else if (e.getSource() == searchRecordsLabel) {
                // 将被点击label设置为红色
                handleRecordsLabel.setForeground(Color.black);
                searchRecordsLabel.setForeground(Color.red);
                resetPasswordLabel.setForeground(Color.black);
                // 显示第二个panel 并居中显示
                if (!searchRecordsPanel.isVisible()) {
                    MainLayout.this.add(searchRecordsPanel, BorderLayout.CENTER);
                    searchRecordsPanel.setVisible(true);
                }
                handleRecordsPanel.setVisible(false);
                resetPasswordPanel.setVisible(false);
            }else if(e.getSource() == resetPasswordLabel){
                // 将被点击label设置为红色
                resetPasswordLabel.setForeground(Color.red);
                searchRecordsLabel.setForeground(Color.black);
                handleRecordsLabel.setForeground(Color.black);
                // 显示第三个panel 并居中显示
                if (!resetPasswordPanel.isVisible()) {
                    MainLayout.this.add(resetPasswordPanel, BorderLayout.CENTER);
                    resetPasswordPanel.setVisible(true);
                }
                handleRecordsPanel.setVisible(false);
                searchRecordsPanel.setVisible(false);

            }
        }
    }

    //系统外观 对应事件函数
    private class SystemStyleChangeListener implements ActionListener{
        //系统外观更改
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Metal风格")){
                try{
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(MainLayout.this);
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error setting the look and feel.");
                    System.exit(0);
                }
            }else if(e.getActionCommand().equals("Motif风格")){
                try{
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(MainLayout.this);
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error setting the look and feel.");
                    System.exit(0);
                }
            }else if(e.getActionCommand().equals("Windows风格")){
                try{
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(MainLayout.this);
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error setting the look and feel.");
                    System.exit(0);
                }
            }
        }
    }

    //文件导入 对应事件函数
    private class FileImport implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int status = fileChooser.showOpenDialog(MainLayout.this);
            //用户点击了 OK按钮
            if (status == JFileChooser.APPROVE_OPTION)
            {
                //开启进度条线程
                progressBar = new ProgressBar();
                progressBarControlThread = new Thread(new ProgressBarControlThread());
                progressBarControlThread.start();
                //文件参数
                String[][] fileDataArray = new String[100][6];
                int i=0 , j = 0;
                File selectedFile = fileChooser.getSelectedFile();
                //文件长度 以字节为单位 获取文件长度 和 已读文件长度
                long fileSize = selectedFile.length();
                long readSize = 0;
                String filename = selectedFile.getPath();
                //System.out.println(filename);
                /*```````````````````````````文件读取`````````````````````````````````*/
                try {
                    FileInputStream inputFile=new FileInputStream(selectedFile);
                    int ch = inputFile.read();

                    // 当该字节的内容不是-1，表示没有到达文件末尾
                    String temp = "";
                    while(ch!=-1){
                        //该字节不为空格
                        if(ch == 13 || ch==10){
                            //对回车 换行不理会
                        }else if(ch!=32){
                            temp+=(char)ch;
                        }else{
                            fileDataArray[i][j++] = temp;
                            if(j>=6){
                                i++; j=0;
                            }
                            temp="";
                        }
                        //一个ch两个字节
                        ch = inputFile.read();
                        readSize+=1*100;//让结果*100
                        progressBarCount = Integer.parseInt(String.valueOf(readSize / fileSize))-1;
                        System.out.println();
                    }
                    if(inputFile!=null){
                           inputFile.close();
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //数据库操作
                DBopreation dbopreation = new DBopreation();
                dbopreation.deleteAndUpdatteStudentInfo(fileDataArray,i);
                //数据库设置完才设置100
                progressBarCount=100;
                //JOptionPane.showMessageDialog(null, "You selected " + filename);
            }
        }
    }

    //文件导出 对应事件函数
    private class FileExport implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // 创建文件选择器，初始化路径
            JFileChooser fileChooser = new JFileChooser();
            // 显示打开文件对话框
            int	status = fileChooser.showSaveDialog(MainLayout.this);
            // 如果用户点击"打开"按钮
            if (status == JFileChooser.APPROVE_OPTION) {
                String[][] studentInfoData;
                // 获取用户所选择的文件对象
                File selectedFile = fileChooser.getSelectedFile();
                //获取数据
                DBopreation dbopreation = new DBopreation();
                studentInfoData=dbopreation.searchAllStudentInfo();
                //输出到文件
                try {
                    FileOutputStream inputFile=new FileOutputStream(selectedFile);
                    for(int i =0;i<studentInfoData.length;i++){
                        for(int j =0;j<6;j++){
                            inputFile.write(studentInfoData[i][j].getBytes());
                            inputFile.write(" ".getBytes());
                        }
                        inputFile.write("\r\n".getBytes());
                    }
                    inputFile.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(MainLayout.this, "文件已成功写至"+selectedFile);

            }
        }
    }

    //退出系统 对应事件函数
    private class ExitSystem implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    //学生人数统计 对应事件函数
    private class StudentNumCount implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            DBopreation dbopreation = new DBopreation();
            int studentNum = dbopreation.countStudentNum();
            String totalStudentString = "学生人数: "+studentNum+"人";
            JOptionPane.showMessageDialog(null,totalStudentString,"学生人数",1);
        }
    }

    //成绩统计 对应事件函数
    private class StatisticsGradeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String  printString ;
            DBopreation dbopreation = new DBopreation();
            int[] grade;
            if(e.getActionCommand().equals("各门课程平均分")){
                grade = dbopreation.statisticsGrade(0);
                printString="语文："+grade[0]+"\n"+"英语："+grade[1]+"\n"+"数学："+grade[2]+"\n";
                JOptionPane.showMessageDialog(null,printString,"各科平均分",1);
            }else if(e.getActionCommand().equals("各门课程最高分")){
                grade = dbopreation.statisticsGrade(1);
                printString="语文："+grade[0]+"\n"+"英语："+grade[1]+"\n"+"数学："+grade[2]+"\n";
                JOptionPane.showMessageDialog(null,printString,"各科最高分",1);
            }else if(e.getActionCommand().equals("各门课程最低分")){
                grade = dbopreation.statisticsGrade(2);
                printString="语文："+grade[0]+"\n"+"英语："+grade[1]+"\n"+"数学："+grade[2]+"\n";
                JOptionPane.showMessageDialog(null,printString,"各科最低分",1);
            }
        }
    }

    private void buildProgressBar(){
       /* progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 200);
        progressBar.setPreferredSize(new Dimension(200, 30));
        progressBar.setStringPainted(true);*/
    }

    //滑动条控制线程
    private class ProgressBarControlThread implements Runnable {
        @Override
        public void run() {
            while (progressBarCount < 100) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.changeValue(progressBarCount);
                //导入完成关闭 滑动条窗口
                if(progressBarCount>=100){
                    JOptionPane.showMessageDialog(null, "导入成功");
                    progressBar.destory();
                }
            }
        }
    }

    //主函数  测试用
    public static void main(String[] args) {
        new MainLayout();
    }

    /*public  void createLayout(){
        new MainLayout();
    }*/
}
