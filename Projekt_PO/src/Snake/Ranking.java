package Snake;

import javax.swing.*;

/**
 * Created by kopcion on 13.06.18.
 */
public class Ranking extends JFrame {
    private Control control;
    private RankingView view = null;
    public Ranking(Control control) {
        this.control = control;
        this.control.setRanking(this);
        initUI();
    }
    public void refresh(){
        view.repaint();
    }
    private void initUI() {

        view = new RankingView(control);
        add(view);

        setResizable(false);
        pack();

        setTitle("Ranking");
        setLocationRelativeTo(null);
        setFocusable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

