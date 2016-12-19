
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.*;


public class LoginLayout extends JFrame{
    //私有控件
    private static final long serialVersionUID = 2236421765412062610L;
    private JButton sure,close;
    private JTextField userName;
    private JPasswordField password;
    private JLabel userNameLabel,passwordLabel;
    private JPanel southPanel,centerPanel,northPanel;
    private DBopreation dbopreation;

    LoginLayout(){
        setTitle("系统登陆");
        setSize(300,200);
        // 布局设置
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        southPanel = new JPanel();
        centerPanel = new JPanel();
        northPanel = new JPanel();
        add(northPanel,BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(southPanel,BorderLayout.SOUTH);

        createLayout();
        setVisible(true);
        dbopreation = new DBopreation();
    }

    private void createLayout(){
        //button设置
        sure = new JButton("sure");
        close = new JButton("close");
        sure.addActionListener(new sureClick());
        close.addActionListener(new closeClick());
        //同户名 密码设置
        userName = new JTextField("",6);
        password = new JPasswordField("",6);
        userNameLabel = new JLabel("用户名",JLabel.CENTER);
        passwordLabel = new JLabel("密码",JLabel.CENTER);
        passwordLabel.setPreferredSize(new Dimension(40,40));
        userNameLabel.setPreferredSize(new Dimension(40,40));
        //控件加入panel
        northPanel.add(userNameLabel);
        northPanel.add(userName);
        centerPanel.add(passwordLabel);
        centerPanel.add(password);
        southPanel.add(sure);
        southPanel.add(close);
    }

    public class sureClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String userNameValue = String.valueOf(userName.getText());
            String passwordValue = String.valueOf(password.getText());
            if(userNameValue.length()<1){
                JOptionPane.showMessageDialog(null, "用户名不可为空");
            }else if(passwordValue.length()<1){
                JOptionPane.showMessageDialog(null, "密码不可为空");
            }else{
                if(dbopreation.login(userName.getText(),password.getText())){
                    //用户登录成功
                    main.username = userName.getText();
                    main.password = password.getText();
                    System.out.println("login Success");
                    //关闭当前登陆窗口 开启下一个窗口
                    dispose();
                    new MainLayout();
                }else{
                    //用户登录失败
                    JOptionPane.showMessageDialog(null, "用户名密码错误，请输入正确的账号密码");
                    System.out.println("login Failed");
                }
            }
        }
    }

    public class closeClick implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
           //关闭当前窗口
            dispose();
        }
    }
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginLayout();
	}*/

}
