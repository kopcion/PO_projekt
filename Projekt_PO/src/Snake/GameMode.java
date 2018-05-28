package Snake;

import javax.swing.*;
import java.awt.*;

public class GameMode extends JFrame {

    public GameMode(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(4,3));

        JButton Button4 = new JButton("Easy");
        Button4.setBounds(100, 100, 80, 30);
        Button4.addActionListener(event -> {
            setVisible(false);
            EventQueue.invokeLater(()->{
                JFrame ex = new Snake(160, false);
                ex.setVisible(true);
            });
        });
        panel.add(Button4);

        JButton Button6 = new JButton("Hard");
        Button6.setBounds(10, 100, 80, 30);
        Button6.addActionListener(event -> {
            setVisible(false);
            EventQueue.invokeLater(()->{
                JFrame ex = new Snake(80, false);
                ex.setVisible(true);
            });
        });
        panel.add(Button6);

        JButton Button8 = new JButton("Easy with enemy");
        Button8.setBounds(100, 100, 80, 30);
        Button8.addActionListener(event -> {
            setVisible(false);
            EventQueue.invokeLater(()->{
                JFrame ex = new Snake(160, true);
                ex.setVisible(true);
            });
        });
        panel.add(Button8);

        JButton Button10 = new JButton("Easy with enemy");
        Button10.setBounds(100, 100, 80, 30);
        Button10.addActionListener(event -> {
            setVisible(false);
            EventQueue.invokeLater(()->{
                JFrame ex = new Snake(80, true);
                ex.setVisible(true);
            });
        });
        panel.add(Button10);


        setTitle("Chose a game");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        GameMode mode = new GameMode();
//        EventQueue.invokeLater(() -> {
//            JFrame ex = new Snake();
//            ex.setVisible(true);
//        });
    }
}