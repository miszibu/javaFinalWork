import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class SearchRecordsPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel;

    public SearchRecordsPanel(){
        titlePanel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(600, 140));

        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        title = new JLabel("Second Panel", SwingConstants.CENTER);
        title.setFont(new Font("Font.DIALOG", Font.BOLD, 28));

        titlePanel.add(title);

        this.add(titlePanel, BorderLayout.NORTH);
    }
}
