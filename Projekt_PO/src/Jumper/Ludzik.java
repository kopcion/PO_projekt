package Jumper;

import java.awt.image.BufferedImage;
public class Ludzik extends Obiekt
{
    static BufferedImage bitmapa=Util.wczytaj("ludzik");
    float grawitacja=0.08f;
    int roof=100;
    boolean skok=false, ziemia=true;
    Ludzik(int r, float g)
    {
        super(55, 40, 300f, 250f);
        roof=r;
        grawitacja=g;
    }
    void live()
    {
        if(skok)
            if(y>roof)y-=(y-roof+1)*grawitacja;
            else skok=false;
        if(y<250 && !skok)y+=(y-roof+1)*grawitacja;
        if(y>250)y=250;
        if(y==250)ziemia=true;
        else ziemia=false;
    }
}