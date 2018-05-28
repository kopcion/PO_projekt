package Sudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    public Menu(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(3,3));

        JButton Button4 = new JButton("4x4");
        Button4.setBounds(100, 100, 80, 30);
        Button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                new Board(4);
            }
        });
        panel.add(Button4);

        JButton Button6 = new JButton("6x6");
        Button6.setBounds(10, 100, 80, 30);
        Button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                new Board(6);
            }
        });
        panel.add(Button6);

        JButton Button8 = new JButton("8x8");
        Button8.setBounds(100, 100, 80, 30);
        Button8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                new Board(8);
            }
        });
        panel.add(Button8);

        setTitle("Sudoku");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
    }
}