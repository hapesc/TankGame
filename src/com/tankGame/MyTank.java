package com.tankGame;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyTank extends Tank{

    private CopyOnWriteArrayList<Bullet> myBullets;
    private Bullet myBullet;
    private final int LIFE=30;//默认生命值为3
    private int currentLife=LIFE;
    public MyTank(int x, int y) {
        super(x, y,0);
        myBullets=new CopyOnWriteArrayList<>();
    }

    public void fire(){
        myBullet=new Bullet(getX(),getY(),getDirect());
        myBullets.add(myBullet);
        super.fire(myBullet);
    }

    public CopyOnWriteArrayList<Bullet> getMyBullets() {
        return myBullets;
    }
    public void getHit(){
        if(currentLife>1)
            currentLife--;
        else {
            die();
            try{
                exp=new Explosion(getX(),getY());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
