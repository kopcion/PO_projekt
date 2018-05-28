import Snake.Snake;
import Snake.GameMode;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(3,3));

        JButton Button4 = new JButton("Sudoku");
        Button4.setBounds(100, 100, 80, 30);
        Button4.addActionListener(event -> {
            setVisible(false);
            new Sudoku.Menu();
        });
        panel.add(Button4);

        JButton Button6 = new JButton("Jumper");
        Button6.setBounds(10, 100, 80, 30);
        Button6.addActionListener(event -> {
            setVisible(false);
            new Jumper.PierwszaKlasa();
        });
        panel.add(Button6);

        JButton Button8 = new JButton("Snake");
        Button8.setBounds(100, 100, 80, 30);
        Button8.addActionListener(event -> {
            setVisible(false);
            new GameMode();
        });
        panel.add(Button8);

        setTitle("Chose a game");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
    }
}