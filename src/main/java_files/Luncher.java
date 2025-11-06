package main.java_files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;

public class Luncher extends JFrame {
    private static final String URL = "jdbc:sqlite:pacman.db";
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePanel panel = new GamePanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
