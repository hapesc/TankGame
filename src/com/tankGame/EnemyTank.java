package com.tankGame;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyTank extends Tank implements Runnable{
    Bullet bullet;
    private CopyOnWriteArrayList<Bullet> bullets= new CopyOnWriteArrayList<>();
    private final int controller=(int)(Math.random()*2);//控制坦克要么上下移动，要么左右移动
    private final int LIFE=2;//默认生命值为2
    private int currentLife=LIFE;



    public EnemyTank(int x, int y) {
        super(x, y,1);
        setSpeed(2);
    }
    public void fire(){
        bullet=new Bullet(getX(),getY(),getDirect());
        bullets.add(bullet);
        super.fire(bullet);

    }
    public CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void getHit()  {
        if(currentLife>1)
            currentLife--;
        else {
            die();
            try {
                exp= new Explosion(getX(), getY());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void run() {
        try{

            while(isLiving()){
                switch (getDirect()){
                    case 0:
                        for(int i=0;i<(int)(Math.random()*30);i++) {

                                moveFront();
                                Thread.sleep(33);

                        }
                        break;
                    case 1:
                        for(int i=0;i<(int)(Math.random()*30);i++) {

                                moveBack();
                                Thread.sleep(33);

                        }
                        break;
                    case 2:
                        for(int i=0;i<(int)(Math.random()*30);i++) {

                                moveLeft();
                                Thread.sleep(33);

                        }
                        break;
                    case 3:
                        for(int i=0;i<(int)(Math.random()*30);i++) {

                                moveRight();
                                Thread.sleep(33);

                        }
                        break;
                }

                if(controller==0)
                    setDirect((int)(Math.random()*2));//上下
                else
                    setDirect((int)(Math.random()*2+2));//左右
                if(getX()<0||getX()>1000||getY()<0||getY()>750)
                    die();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
