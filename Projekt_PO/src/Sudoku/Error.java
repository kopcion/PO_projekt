package Sudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Error extends JFrame {

    public Error(){
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(3,3));

        JButton quitButton = new JButton("OK");
        quitButton.setBounds(100, 100, 80, 30);
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });
        JTextField textField = new JTextField();
        textField.setText("Collision with starting number, try again...");
        textField.setEditable(false);
        panel.add(textField);
        panel.add(quitButton);

        setTitle("OK");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Error e = new Error();
    }
}