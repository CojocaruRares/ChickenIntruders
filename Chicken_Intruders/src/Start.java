import javax.swing.*;
import java.sql.*;

public class Start extends JFrame {

    public Start() {
        add(new Game());
        setTitle("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new Start();
    }
}