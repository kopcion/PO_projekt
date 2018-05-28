package Jumper;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
public class Wybor extends JPanel
{
    ArrayList<JButton> przyciski = new ArrayList<JButton>();
    Wybor(String[] titles)
    {
        setLayout(new GridLayout(titles.length,1));
        for(String s: titles)
            przyciski.add(new JButton(s));
        for(JButton jb: przyciski)
            add(jb);
    }
}