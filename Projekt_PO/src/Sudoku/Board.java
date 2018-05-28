package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel{
    int dimension;
    JFrame frame;
    private JTextField[][] tfCells;
    private Cell cell;
    public Board( int dimension ){
        this.dimension = dimension;
        tfCells = new JTextField[dimension][dimension];
        cell = new Cell(dimension);
        frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.setLayout(new GridLayout(dimension+1,dimension+1));
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                tfCells[row][col] = new JTextField();
                cp.add(tfCells[row][col]);

                tfCells[row][col].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int val = 0;
                        JTextField source = (JTextField) e.getSource();
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if( source.getText().length() == 0 ){
                                source.setText("");
                                return;
                            }
                            if (!(KeyEvent.VK_0 <= source.getText().charAt(0) && source.getText().charAt(0) <= dimension + KeyEvent.VK_0) || source.getText().length() > 1 ) {
                                source.setText("");
                                return;
                            }
                            int row=0 , col=0;
                            boolean found = false;
                            for (int i = 0; i < dimension && !found; ++i) {
                                for (int j = 0; j < dimension && !found; ++j) {
                                    if (tfCells[i][j] == source) {
                                        row = i;
                                        col = j;
                                        found = true;
                                    }
                                }
                            }
                            try {
                                val = cell.getValue(row,col);

                                if(val == source.getText().charAt(0)-'0') return;

                                System.out.println("The old value was " + val);

                                if(val == 0) {
                                    cell.setValue(row, col, source.getText().charAt(0) - '0');
                                }

                                if(val != 0){
                                    try {
                                        //check if there is something to be reversed in coloring
                                        cell.setValue(row,col,val);
                                    } catch (RuntimeException er ){
                                        String message = er.getMessage();
                                        System.out.println("1st Caught error : " + message);
                                        //if(message.charAt(0)!='e') {
                                        reverseColoring(message);
                                        //}
                                    }
                                    cell.setValue(row,col, source.getText().charAt(0)-'0');
                                }
                            } catch (RuntimeException error) {
                                String message = error.getMessage();
                                System.out.println("2nd Caught error : " + message);
                                if(message.charAt(0)=='e'){
                                    if(val != 0){
                                        try{
                                            System.out.println("Resetting old value...");
                                            cell.setValue(row,col,-1);
                                            tfCells[row][col].setText("" + val);
                                            cell.setValue(row,col,val);
                                        } catch (RuntimeException rev) {
                                            String data = rev.getMessage();
                                            source.setBackground(Color.red);
                                            colorRed(data);
                                        }
                                    } else
                                        source.setText("");
                                    return;
                                }
                                source.setBackground(Color.red);
                                colorRed(message);
                            }
                        }
                    }
                });
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(new Font("Monospaced", Font.BOLD, 20));
            }
        }
        frame.setSize(50*dimension+16,50*dimension+39);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void colorRed(String data){
        for(int i=0; i < data.length(); ){
            int Erow = data.charAt(i++) - '0';
            int Ecol = data.charAt(i++) - '0';
            tfCells[Erow][Ecol].setBackground(Color.red);
        }
    }
    public void reverseColoring(String data){
        for (int i = 0; i < data.length(); ) {
            int Erow = data.charAt(i++) - '0';
            int Ecol = data.charAt(i++) - '0';
            tfCells[Erow][Ecol].setBackground(new Color(
                    cell.getColour(cell.getSet(Erow, Ecol))[0],
                    cell.getColour(cell.getSet(Erow, Ecol))[1],
                    cell.getColour(cell.getSet(Erow, Ecol))[2]
            ));
        }
    }
    @Override
    public void paint(Graphics g){
        for( int row=0; row < dimension; ++row){
            for( int col=0; col < dimension; ++col){
                if( cell.getValue(row, col) > 0 ){
                    tfCells[row][col].setText("" + cell.getValue(row, col));
                    tfCells[row][col].setEditable(false);
                } else {
                    tfCells[row][col].setText("");
                    tfCells[row][col].setEditable(true);
                }
                tfCells[row][col].setOpaque(true);
                tfCells[row][col].setBackground(new Color(
                        cell.getColour( cell.getSet(row,col) )[0],
                        cell.getColour( cell.getSet(row,col) )[1],
                        cell.getColour( cell.getSet(row,col) )[2]
                ));
            }
        }
    }

    public static void main(String[] args) {

//        Board b = new Board(4);
    }
}