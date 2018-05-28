package Jumper;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
public class PierwszaKlasa extends JFrame
{
    static MyPanel panel;
    static Twarz twarz;
    java.util.Timer timer;
    KeyListener kl = new KeyListener()
    {

        public void keyPressed(KeyEvent e)
        {
            panel.klawisz(e);
        }
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e){}
    };
    void koniec(String info1, String info2)
    {
        panel.przegrana=false;
        panel.wygrana=false;
        panel.dead=1000000000;
        int opcja=JOptionPane.showConfirmDialog(null, info1+", do you want to play again?", info2, JOptionPane.YES_NO_OPTION);
        if(opcja==JOptionPane.YES_OPTION)
        {
            timer.cancel();
            graj(panel.k);
        }
        if(opcja==JOptionPane.NO_OPTION)
        {
            twarz.gra=false;
            panel.setVisible(false);
            twarz.setVisible(true);
            timer.cancel();
        }
    }
    public PierwszaKlasa()
    {
        super("Rysowanie");
        twarz = new Twarz(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2-400, screenSize.height/2-300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        twarz.setBounds(0,0,800,600);
        add(twarz);
        setSize(800, 600);
        addKeyListener(kl);
    }
    void graj(Konfiguracja k)
    {
        panel = new MyPanel(k);
        panel.setBounds(0,0,800,600);
        requestFocus();
        add(panel);
        twarz.setVisible(false);
        panel.setVisible(true);
        timer = new java.util.Timer();
        TimerTask tt = new TimerTask()
        {
            public void run()
            {
                if(!panel.wygrana && !panel.przegrana)panel.time++;
                if(panel.przegrana)koniec("You lose","Defeat");
                if(panel.wygrana)koniec("You win","Victory");
                panel.repaint();
            }
        };
        timer.schedule(tt, 0, 5);
        //experimantal
        /*Thread runnable = new Thread() {
            @Override
            public void run()
            {
                while(true){
                    if(!panel.wygrana && !panel.przegrana)panel.time++;
                    if(panel.przegrana)koniec("You lose","Defeat");
                    if(panel.wygrana)koniec("You win","Victory");
                    panel.repaint();
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        runnable.start();*/
    }
    public static void main(String[] args)
    {
        new PierwszaKlasa();
    }
}