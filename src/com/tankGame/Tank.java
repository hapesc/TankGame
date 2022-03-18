package com.tankGame;

import java.awt.*;
import java.util.Vector;

public class Tank {
    //坦克的横纵坐标
    private int x;
    private int y;
    private int speed=10;
    private int direct=0;
    private final int TYPE;
    private boolean living=true;
    protected Explosion exp;
//    private int cnt=0;
//    private Bullet bullet;
    public Tank(int x, int y,int type) {
        this.x = x;
        this.y = y;
        this.TYPE=type;

    }

    public boolean isLiving(){return living;}
    public void die(){living=false;}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public void moveFront(){
        if(getY()-getSpeed()>0)
        y-=speed;
    }
    public void moveBack(){
        if(getY()+60+getSpeed()<750)
        y+=speed;
    }
    public void moveLeft(){
        if(getX()-getSpeed()>0)
        x-=speed;
    }
    public void moveRight(){
        if(getX()+60+getSpeed()<1000)
        x+=speed;
    }

    public int getTYPE() {
        return TYPE;
    }



    public  void  fire(Bullet bullet){


        Runnable r=new Runnable() {
            @Override
            public  void  run() {
                try {
                    while (true){
                        Thread.sleep(50);
                        bullet.move();
                        int bx=bullet.getX();
                        int by=bullet.getY();
//                        System.out.printf("%d,%d\n",bx,by);

                        if(!(bx>0&&bx<1000&&by>0&&by<750)) {
                            bullet.die();
                            break;
                        }
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(r);
        t1.start();
    }

    public Explosion getExp() {
        return exp;
    }

    public void getHit() {
    }

}
