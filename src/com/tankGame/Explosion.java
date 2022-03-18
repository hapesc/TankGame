package com.tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Explosion {
    private Image explosion;
    private final int x;
    private final int y;
    private boolean living=true;
    public Explosion(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        explosion=ImageIO.read(new File("src/photo/explosion.jpeg"));
    }
    public void disappear(){living=false;}
    public boolean isLiving(){return living;}

    public void explode(Graphics g, ImageObserver ob)  {

            g.drawImage(explosion,x,y,60,60,ob);
            disappear();



    }

}
