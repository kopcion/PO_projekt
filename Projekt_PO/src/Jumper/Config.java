package Jumper;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class Config  extends JPanel
{
    Konfiguracja conf = new Konfiguracja(10, 150, 1.6f, 0.08f);
    String[] jbText = {"The number of obstacles", "The height of jumps", "The speed of running", "Gravity"};
    String[] jtfText = {conf.amount.toString(), conf.roof.toString(), conf.tempo.toString(), conf.grawitacja.toString()};
    int l_opcji=jbText.length;
    JButton graj = new JButton("Play!"), aReturn = new JButton("Return");
    JButton[] jb = new JButton[l_opcji];
    JTextField[] jtf = new JTextField[l_opcji];
    Config()
    {
        setLayout(new GridLayout(l_opcji+1,2));
        add(graj);
        add(aReturn);
        for(int i=0; i<l_opcji; i++)
        {
            jb[i]=new JButton(jbText[i]);
            jtf[i]=new JTextField(jtfText[i]);
            jtf[i].setActionCommand(jtfText[i]);
            add(jb[i]);
            add(jtf[i]);
        }
    }
}