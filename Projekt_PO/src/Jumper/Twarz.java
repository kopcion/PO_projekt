package Jumper;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
public class Twarz  extends JPanel
{
    int l_misji=10;
    boolean gra=false;
    BufferedImage background;
    PierwszaKlasa baza;
    String[] Smain = {"Custom Game","Campaign","Exit"};
    String[] Skampania=new String[l_misji+1];
    Wybor Wmain = new Wybor(Smain), Wkampania;
    Config Wown = new Config();
    ActionListener BL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            String name=((JButton)e.getSource()).getText();
            if(name.equals("Campaign"))
            {
                Wmain.setVisible(false);
                Wkampania.setVisible(true);
            }
            if(name.equals("Custom Game"))
            {
                Wmain.setVisible(false);
                Wown.setVisible(true);
                repaint();
            }
            if(name.equals("Exit"))System.exit(1);
            if(name.contains("Level"))
            {
                switch(name)
                {
                    case "Level 1":  baza.graj(new Konfiguracja(10, 150, 1.6f, 0.08f)); break;
                    case "Level 2":  baza.graj(new Konfiguracja(15, 150, 2.0f, 0.08f)); break;
                    case "Level 3":  baza.graj(new Konfiguracja(20, 150, 2.5f, 0.08f)); break;
                    case "Level 4":  baza.graj(new Konfiguracja(20, 150, 3.0f, 0.08f)); break;
                    case "Level 5":  baza.graj(new Konfiguracja(20, 150, 3.5f, 0.08f)); break;
                    case "Level 6":  baza.graj(new Konfiguracja(20, 150, 4.0f, 0.08f)); break;
                    case "Level 7":  baza.graj(new Konfiguracja(25, 150, 5.0f, 0.12f)); break;
                    case "Level 8":  baza.graj(new Konfiguracja(30, 150, 6.0f, 0.15f)); break;
                    case "Level 9":  baza.graj(new Konfiguracja(35, 150, 7.0f, 0.18f)); break;
                    case "Level 10": baza.graj(new Konfiguracja(50, 150, 8.0f, 0.2f));  break;
                }
                Wkampania.setVisible(false);
                Wmain.setVisible(true);
                setVisible(false);
            }
            if(name.equals("Return"))
            {
                Wkampania.setVisible(false);
                Wown.setVisible(false);
                Wmain.setVisible(true);
                repaint();
            }
            if(name.equals("Play!"))
            {
                baza.graj(Wown.conf);
            }
        }
    };
    ActionListener TFL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            String name=e.getActionCommand();
            if(name==Wown.jtfText[0])Wown.conf.amount=Integer.valueOf(Wown.jtf[0].getText());
            if(name==Wown.jtfText[1])Wown.conf.roof=Integer.valueOf(Wown.jtf[1].getText());
            if(name==Wown.jtfText[2])Wown.conf.tempo=Float.valueOf(Wown.jtf[2].getText());
            if(name==Wown.jtfText[3])Wown.conf.grawitacja=Float.valueOf(Wown.jtf[3].getText());
        }
    };
    Twarz(PierwszaKlasa pk)
    {
        background=Util.wczytaj("tlo");
        setLayout(null);
        baza=pk;

        Wmain.setBounds(300,200,200,150);
        for(JButton jb: Wmain.przyciski)
            jb.addActionListener(BL);
        add(Wmain);

        for(int i=0; i<l_misji; i++)
            Skampania[i]="Level "+(i+1);
        Skampania[l_misji]="Return";
        Wkampania = new Wybor(Skampania);
        Wkampania.setBounds(300,20,200,500);
        for(JButton jb: Wkampania.przyciski)
            jb.addActionListener(BL);
        add(Wkampania);
        Wkampania.setVisible(false);
        Wown.setBounds(250,100,300,300);
        Wown.aReturn.addActionListener(BL);
        Wown.graj.addActionListener(BL);
        for(int i=0; i<Wown.l_opcji; i++)
            Wown.jtf[i].addActionListener(TFL);
        add(Wown);
        Wown.setVisible(false);

        setPreferredSize(new Dimension(800, 600));
    }
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, null);
        if(Wown.isVisible())
        {
            g.setFont(new Font("Default", Font.BOLD, 15));
            g2.drawString("Press ENTER to confirm change", 250, 80);
        }
    }
}