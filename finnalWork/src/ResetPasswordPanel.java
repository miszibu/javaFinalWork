import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Created by zibu on 2016/12/9.
 */
public class ResetPasswordPanel extends JPanel {
    private JLabel title;
    private JPanel titlePanel;

    public ResetPasswordPanel(){
        // 第一个主内容布局
        titlePanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(600, 140));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        title = new JLabel("Third Panel", SwingConstants.CENTER);
        title.setFont(new Font("Font.DIALOG", Font.BOLD, 28));

        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);
    }
}
