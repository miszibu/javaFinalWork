import javax.swing.*;
import java.awt.*;

/**
 * Created by zibu on 2016/12/11.
 */
public class ProgressBar extends JFrame {
    private JProgressBar progressBar;
    private JPanel centerPanel;
    public ProgressBar(){
        //布局设置
        this.setTitle("导入文件");
        this.setSize(300,200);
        this.setLayout(new BorderLayout(10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        centerPanel =  new JPanel();
        //进度条设置
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        progressBar.setPreferredSize(new Dimension(200, 30));
        progressBar.setStringPainted(true);
        progressBar.setValue(100);

        //加入布局
        centerPanel.add(progressBar);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
        add(centerPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    public void changeValue(int currentValue){
        progressBar.setValue(++currentValue);
    }
    public void destory(){
        dispose();
    }
    //测试入口
    public static void main(String[] args){
        new ProgressBar();
    }

}
