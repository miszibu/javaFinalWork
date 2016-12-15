package lab11;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StudentManager extends JFrame{
	//数据库操作
	private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet res = null;
    private static String url = new String("jdbc:mysql://localhost:3306/student?characterEncoding=utf8&useSSL=false");
    
	private JLabel[] projectName = new JLabel[6];
	private JTextField[] projectValue = new JTextField[6];
	private JPanel northPanel,southPanel;
	private JPanel insertPanel,searchPanel,buttonPanle;
	private JButton insertButton , searchButton , exit;
	private JTextArea searchArea ; 
	private static final long serialVersionUID = -6631222277893857896L;
	public StudentManager(){
		setTitle("学生信息管理");
		//setSize(490,300);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10,10));
		createNorthLayout();
		createSouthLayout();
		add(northPanel,BorderLayout.NORTH);
		add(southPanel,BorderLayout.SOUTH);
		pack();
		setVisible(true);
		//数据库初始化
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
	
	public void createNorthLayout(){
		northPanel = new JPanel();
		northPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		northPanel.setLayout(new GridLayout(2,3));
		projectName[0] = new JLabel("学号");
		projectName[1] = new JLabel("姓名");
		projectName[2] = new JLabel("性别");
		projectName[3] = new JLabel("语文");
		projectName[4] = new JLabel("数学");
		projectName[5] = new JLabel("英语");
		for(int i =0;i<projectName.length;i++){
			projectValue[i] = new JTextField(10);
			//网格布局 把两个放在一个panel中加入
			JPanel temp = new JPanel();
			temp.add(projectName[i]);
			temp.add(projectValue[i]);
			northPanel.add(temp);
		}
	}
	
	public void createSouthLayout(){
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout(10,10));
		buildInsertPanel();
		buildSearchPanel();
		buildButtonPanel();
		southPanel.add(insertPanel,BorderLayout.NORTH);
		southPanel.add(searchPanel,BorderLayout.CENTER);
		southPanel.add(buttonPanle,BorderLayout.SOUTH);
	}
	
	public void buildInsertPanel(){
		insertPanel = new JPanel();
		insertPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		insertButton = new JButton("插入新记录");
		insertButton.addActionListener(new InsertClickListener());
		insertPanel.add(insertButton);
	}
	
	public void buildSearchPanel(){
		searchPanel = new JPanel();
		searchArea = new JTextArea(10,40);
		searchPanel.add(searchArea);
	}
	
	public void buildButtonPanel(){
		buttonPanle = new JPanel();
		searchButton = new JButton("查询");
		searchButton.addActionListener(new SearchCLickListener());
		exit = new JButton("退出");
		exit.addActionListener(new ExitClickListener());
		buttonPanle.add(searchButton);
		buttonPanle.add(exit);
	}
	
	public class InsertClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String sql = "insert into studentinfo values (";
			for(int i =0;i<projectValue.length;i++){
				if(projectValue[i].getText() == null){
					JOptionPane.showMessageDialog(null,"输入数据不合法","",2);
					return;
				}
				if(i==projectValue.length-1){
					sql+="'"+projectValue[i].getText()+"'";
				}else{
					sql+="'"+projectValue[i].getText()+"',";
				}
				
			}
			sql+=")";
			//插入数据
			System.out.println(sql);
		}
		
	}
	
	public class SearchCLickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(searchArea.getText() == null){
				JOptionPane.showMessageDialog(null,"输入语句不合法","",2);
				return ;
			}else{
				String[][] searchResult = new String[100][6];
		        String[][] returnData ;
		        int lineNum = 0;
		        try {
		            res = statement.executeQuery(searchArea.getText());
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
			}
		}
		
	}
	
	public class ExitClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		
	}
	
	public static void main(String[] args){
		new StudentManager();
	}
}
