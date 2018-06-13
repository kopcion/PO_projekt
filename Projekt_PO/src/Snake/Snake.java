package Snake;

import javax.swing.*;

public class Snake extends JFrame {
    private Control control;
    int delay;
    boolean withEnemy;

    public Snake(int Delay, boolean withEnemy, int starvation, Control control, boolean bonusApple){
        this.control = control;
        control.setSnake(this);
        this.delay = Delay;
        this.withEnemy = withEnemy;
        initUI(Delay, withEnemy, starvation, bonusApple);
    }

    private void initUI(int Delay, boolean withEnemy, int starvation, boolean bonusApple) {
        Board board = new Board(Delay, withEnemy, starvation, control, bonusApple);

        add(board);

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}