import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Created by sakura on 16/6/21.
 */
public class test extends JFrame{
    test(){
        setSize(400,300);
        add(new JPasswordField(5), BorderLayout.CENTER);
        setVisible(true);
    }
    public static void main(String[] args){
        new test();
    }
}