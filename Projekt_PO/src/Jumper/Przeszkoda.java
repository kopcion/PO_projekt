package Jumper;

import java.awt.image.BufferedImage;
public class Przeszkoda extends Obiekt
{
    final int miejsce;
    static BufferedImage bitmapa=Util.wczytaj("pien");
    Przeszkoda(int m)
    {
        super(30, 30, 0, 250f);
        miejsce=m;
    }
}