package Snake;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake(int Delay, boolean withEnemy) {

        initUI(Delay, withEnemy);
    }

    private void initUI(int Delay, boolean withEnemy) {
        Board board = new Board(Delay, withEnemy);
//        board.withEnemy = withEnemy;
//        board.DELAY = Delay;
        add(board);

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            JFrame ex = new Snake();
//            ex.setVisible(true);
//        });
    }
}