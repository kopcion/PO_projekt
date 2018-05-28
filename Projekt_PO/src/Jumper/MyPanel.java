package Jumper;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
public class MyPanel extends JPanel
{
    int time=0, zaliczone=0, dead=1000000000;
    boolean przegrana=false, wygrana=false;
    static Konfiguracja k = new Konfiguracja(10, 150, 1.7f, 0.08f);
    BufferedImage background;
    Ludzik ludzik;
    ArrayList<Przeszkoda> trunks = new ArrayList <Przeszkoda>();
    MyPanel(Konfiguracja kk)
    {
        k=kk;
        background=Util.wczytaj("tlo");
        ludzik = new Ludzik(k.roof, k.grawitacja);
        for(int i=1000; i<500*k.amount+1000; i+=500)
            trunks.add(new Przeszkoda(i));
        setPreferredSize(new Dimension(800, 600));
    }
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, -(int)(time*k.tempo)%800, 0, null);
        g2.drawImage(background, 800-(int)(time*k.tempo)%800, 0, null);
        ludzik.live();
        g2.drawImage(ludzik.bitmapa, 300, (int)ludzik.y, null);
        for(Przeszkoda p : trunks)
        {
            p.x=p.miejsce-(int)(time*k.tempo);
            if(p.x<100)zaliczone++;
            g2.drawImage(p.bitmapa, (int)p.x, 280, null);
            if(Util.kolizja(ludzik, p))dead=time;
            if(time-dead>50)przegrana=true;
        }
        g2.drawString("Trunks "+zaliczone+"/"+k.amount, 20, 30);
        if(zaliczone==k.amount)wygrana=true;
        zaliczone=0;
    }
    void klawisz(KeyEvent e)
    {
        if(e.getKeyChar()==' ' && ludzik.ziemia)ludzik.skok=true;
    }
}