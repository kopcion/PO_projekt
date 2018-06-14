package Snake;

import javax.swing.*;
import java.awt.*;

public class GameMode extends JFrame {
    private Control control;

    Ranking rank = null;
    JPanel panel = null;

    Checkbox gameLevel = null;
    Checkbox bonusApple = null;
    Checkbox withEnemy = null;
    Checkbox starvation = null;
    JButton ranking = null;

    public GameMode(Control control){
        this.control = control;
        this.control.setGameMode(this);


        rank = new Ranking(control);
        panel = new JPanel();


        getContentPane().add(panel);

        panel.setLayout(new GridLayout(6,1));

        gameLevel = new Checkbox();
        gameLevel.setLabel("hard");
        panel.add(gameLevel);

        withEnemy = new Checkbox();
        withEnemy.setLabel("enemy");
        panel.add(withEnemy);

        bonusApple = new Checkbox();
        bonusApple.setLabel("bonus apple");
        panel.add(bonusApple);

        starvation = new Checkbox();
        starvation.setLabel("starvation");
        panel.add(starvation);

        JButton start = new JButton("Go!");
//        start.setBounds();
        start.addActionListener(event -> launchGame());
        panel.add(start);

        ranking = new JButton("ranking");
        ranking.addActionListener(event -> {
            setVisible(false);
            rank.refresh();
            rank.setVisible(true);
        });
        panel.add(ranking);

        setTitle("Chose a game");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void launchGame(){
        setVisible(false);
        EventQueue.invokeLater(()->{
            int rate = 0;
            int delay = 80;//160
            if(starvation.getState()) rate = 160;
            if(gameLevel.getState()) delay = 40;//100
            JFrame ex = new Snake(delay, withEnemy.getState(), rate, control, bonusApple.getState());
            ex.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Control control1 = new Control();
        control1.setDatabase(new Database());
        GameMode mode = new GameMode(control1);
    }
}