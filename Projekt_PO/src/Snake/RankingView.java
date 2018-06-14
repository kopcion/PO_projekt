package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class RankingView extends JPanel {

    private Control control;
    public RankingView(Control control){
        this.control = control;
        init();
    }

    private void init() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    control.getRanking().setVisible(false);
                    control.getGameMode().setVisible(true);
                }
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        setBackground(Color.black);
        setFocusable(true);
        setDoubleBuffered(true);

        setPreferredSize(new Dimension(300, 300));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawRanking(g);
    }

    private void drawRanking(Graphics g){
        try {
            int size = control.getDatabase().size();
            ArrayList list = new ArrayList();
            for(int i=0 ; i < size; i++){
                list.add(control.getDatabase().getData(i));
            }
            System.out.println(list);
            list.sort(null);
            Font small = new Font("Helvetica", Font.BOLD, 12);
            FontMetrics metr = getFontMetrics(small);

            if(size > 10) size = 10;
            for(int i=0; i < size; i++){
                g.setColor(Color.white);
                g.setFont(small);
                StringBuilder builder = new StringBuilder((i+1) + ". " + list.get(list.size() - i -1));
                g.drawString(builder.toString(), (300 - metr.stringWidth(builder.toString())) / 2, 17*i + 50);
            }
            g.drawString("press [backspace] to return", (300 - metr.stringWidth("press [backspace] to return"))/2, 17*(size+2) + 50);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
