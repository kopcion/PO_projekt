package Jumper;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
public class Util
{
    static BufferedImage wczytaj(String name)
    {
        BufferedImage bufor;
        try
        {
            URL link=Util.class.getClassLoader().getResource("img/"+name+".png");
            bufor = ImageIO.read(link);
            return bufor;
        }
        catch (Exception e)
        {
            System.err.println("Image reading error!");
            e.printStackTrace();
        }
        return null;
    }
    static boolean kolizja(Obiekt a, Obiekt b)
    {
        int licznik=0;
        if(a.x<=b.x+b.szer)licznik++;
        if(a.x+a.szer>=b.x)licznik++;
        if(a.y<=b.y+b.wys)licznik++;
        if(a.y+a.wys>=b.y)licznik++;
        if(licznik==4)return true;
        return false;
    }
}