import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;


/**
 * Created by zibu on 2016/12/9.
 */
public class ResetPasswordPanel extends JPanel {
    private JLabel title;
    private JPanel titlePanel,buttonPanel,contentPanel;
    private JButton sure;
    private JLabel[] textLabel =  new JLabel[3];
    private JPasswordField[] passwordTextField = new JPasswordField[3];
    private JPanel[] passwordPanel = new JPanel[3];
    public ResetPasswordPanel(){
        // 第一个主内容布局
        setLayout(new BorderLayout(10,10));
        buildTitle();
        buildButtonPanel();
        buildContextPanel();
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(contentPanel,BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    //标题布局 创建
    private void buildTitle(){
        titlePanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(500, 80));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        title = new JLabel("修改密码", SwingConstants.CENTER);
        title.setFont(new Font("Font.DIALOG", Font.BOLD, 28));

        titlePanel.add(title);
    }

    //按钮布局 创建
    private void buildButtonPanel(){
        buttonPanel =  new JPanel();
        sure = new JButton("确认");
        sure.addActionListener(new SureListener());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,100,0));
        buttonPanel.add(sure);
    }

    //内容布局 创建
    private void buildContextPanel(){
        contentPanel = new JPanel();
        //contentPanel.setLayout(new BorderLayout(10,10));

        textLabel[0] = new JLabel("请输入旧密码");
        textLabel[1] = new JLabel("请输入新密码");
        textLabel[2] = new JLabel("再次输入新密码");
        for (int i =0;i<textLabel.length;i++){
            passwordTextField[i] = new JPasswordField(10);
            passwordPanel[i] = new JPanel();
            passwordPanel[i].add(textLabel[i]);
            passwordPanel[i].add(passwordTextField[i]);
            //TODO:label 和 textfield 对齐
            passwordPanel[i].setPreferredSize(new Dimension(800,40));
            contentPanel.add(passwordPanel[i]);
        }
    }

    //按钮点击 事件
    private class SureListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO:获取 全局的账号密码
            DBopreation dbopreation = new DBopreation();
            //旧密码验证通过
            if(passwordTextField[0].getText().equals(dbopreation.getPassword(main.username))){
                //两次新密码验证
                if(passwordTextField[1].getText().equals(passwordTextField[2].getText())){
                    if(dbopreation.updatePassword(main.username,passwordTextField[1].getText())){
                        JOptionPane.showMessageDialog(null, "更新密码成功");
                    }else{
                        JOptionPane.showMessageDialog(null, "更新密码失败","请重试",2);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "新密码不同","请填写相同的新密码",2);
                }
            }else {
                JOptionPane.showMessageDialog(null, "原密码错误","请填写正确的密码",2);
            }
        }
    }
}
