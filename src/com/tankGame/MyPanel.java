package com.tankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

//游戏的绘图区
//KeyListener监听键盘行为
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义坦克
    MyTank hero=null;
    CopyOnWriteArrayList<EnemyTank> et=new CopyOnWriteArrayList<>();//考虑多线程问题

    static CopyOnWriteArrayList<Explosion> exp = new CopyOnWriteArrayList<>();

    static final int FRONT = 0;
    static final int BACK = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;

    static final int PLAYER = 0;
    static final int ENEMY = 1;
    static final int ENEMY_NUMBER = 5;

    public MyPanel(){
        //初始化玩家的坦克
        hero=new MyTank(900,600);

        for(int i=0;i<ENEMY_NUMBER;i++){
            EnemyTank enemyTank = new EnemyTank((int) (900 * Math.random()), (int) (700 * Math.random()));
            enemyTank.setDirect(BACK);
            et.add(enemyTank);
        }

    }

    public void paint(Graphics g){
        super.paintComponent(g);
        //画出背景
        g.fillRect(0,0,1000,750);//矩形，默认黑色

        //画出敌人坦克
        Iterator<EnemyTank> it=et.iterator();
        if(!et.isEmpty()){
            while(it.hasNext()) {
                EnemyTank etX=it.next();
                if (etX.isLiving()) {
                    drawTank(g, etX);
                    new Thread(etX).start();
                    etX.fire();
                    drawEnemyBullet(g, etX);//如果放在if内，坦克die后子弹立刻消失
                } else {
                    et.remove(etX);
                }

        }
        }
        //画出玩家的子弹
        //画出玩家坦克

        if(hero.isLiving()) {
            drawTank(g, hero);
            drawPlayerBullet(g, hero);
        }else
            hero=null;
        //画出爆炸效果
        for(int j=0;j<exp.size();j++){
            Explosion e=exp.get(j);
            if(e.isLiving()){
                e.explode(g,this);
            }else
                exp.remove(e);

        }
    }

    /**
     *
     * @param tank
     * @param g Graphics画笔
     */
    public void drawTank(Graphics g,Tank tank){
        int x=tank.getX();
        int y=tank.getY();
        int type=tank.getTYPE();
        int direct=tank.getDirect();
        switch (type){
            case 0://玩家
                g.setColor(Color.pink);
                break;
            case 1:
                g.setColor(Color.CYAN);
                break;

        }
        //根据方向绘制坦克
        switch (direct) {
            case 0://前方
                g.fill3DRect(x, y, 10, 60, false);//坦克的左履带，其坐标定义为坦克的坐标(x,y)尺寸10*60
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中部，坐标(x+10,y+10)尺寸20*40
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克的右履带，坐标为(x+30,y)
                g.fillOval(x + 10, y + 20, 20, 20);//坦克炮台，坐标为(x+10,y+20),尺寸为20*20
                g.drawLine(x + 20, y, x + 20, y + 30);//炮管，坐标参数为(x+20,y,x+20,y+30)
                break;
            case 1://后方
                g.fill3DRect(x, y, 10, 60, false);//坦克的左履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//坦克中部
                g.fill3DRect(x + 30, y, 10, 60, false);//坦克的右履带
                g.fillOval(x + 10, y + 20, 20, 20);//坦克炮台
                g.drawLine(x + 20, y+60, x + 20, y + 30);//炮管，坐标参数变为(x+20,y+60,x+20,y+30)
                break;
            case 2://左方
                g.fill3DRect(x, y, 60, 10, false);//坦克的左履带，其坐标定义为坦克的坐标(x,y)
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克中部
                g.fill3DRect(x , y+30, 60, 10, false);//坦克的右履带
                g.fillOval(x + 20, y + 10, 20, 20);//坦克炮台
                g.drawLine(x , y+20, x + 30, y + 20);//炮管
                break;
            case 3://右方
                g.fill3DRect(x, y, 60, 10, false);//坦克的左履带，其坐标定义为坦克的坐标(x,y)
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//坦克中部
                g.fill3DRect(x , y+30, 60, 10, false);//坦克的右履带
                g.fillOval(x + 20, y + 10, 20, 20);//坦克炮台
                g.drawLine(x+60 , y+20, x + 30, y + 20);//炮管
                break;
        }
    }
    public void drawPlayerBullet(Graphics g,MyTank t){
        CopyOnWriteArrayList<Bullet> bullets=hero.getMyBullets();
        for(int i=0;i<bullets.size();i++) {
            Bullet bullet=bullets.get(i);
            if (bullet != null && bullet.isLiving()) {
                int mx = bullet.getX();
                int my = bullet.getY();
                int size = bullet.size;
                g.setColor(Color.pink);
                g.fillOval(mx, my, size, size);
            }else
                bullets.remove(bullet);
        }
    }

    public void drawEnemyBullet(Graphics g,EnemyTank t) {
        CopyOnWriteArrayList<Bullet> bullets = t.getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            if ( bullets.get(i).isLiving()) {
                int bx = bullets.get(i).getX();
                int by = bullets.get(i).getY();
                int size = bullets.get(i).size;
                g.setColor(Color.CYAN);
                g.fillOval(bx, by, size, size);
            }else
                bullets.remove(i);
        }
    }
    //判断子弹有没有击中坦克，放在drawPlayerBullet方法里循环调用
    public static void hitTank(Bullet bullet,Tank tank){
        int bx = bullet.getX();
        int by = bullet.getY();
        int tx = tank.getX();
        int ty = tank.getY();
        int direct = tank.getDirect();

        if(direct==0||direct==1){
            if(bx>=tx&&bx<=tx+40&&by>=ty&&by<=ty+60) {

                tank.getHit();
                bullet.die();
            }
        }else if(direct==2||direct==3){
            if(bx>=tx&&bx<=tx+60&&by>=ty&&by<=ty+40) {
                tank.getHit();
                bullet.die();
            }
        }
        if(!tank.isLiving())
            exp.add(tank.getExp());


    }
//有字符输出时，触发该方法
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W) {
            hero.setDirect(FRONT);
            hero.moveFront();
        }
        if(e.getKeyCode()==KeyEvent.VK_S) {
            hero.setDirect(BACK);
            hero.moveBack();
        }
        if(e.getKeyCode()==KeyEvent.VK_A) {
            hero.setDirect(LEFT);
            hero.moveLeft();
        }
        if(e.getKeyCode()==KeyEvent.VK_D) {
            hero.setDirect(RIGHT);
            hero.moveRight();
        }
        if(e.getKeyCode()==KeyEvent.VK_J){

                hero.fire();


            }
        this.repaint();
        }





    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        //不断刷新游戏画面
        try{
            while(true){
                Thread.sleep(33);
                for(Bullet b:hero.getMyBullets()){
                    for(EnemyTank etX:et){
                        hitTank(b,etX);

                    }

                }
                for(EnemyTank etX:et){
                    for(Bullet eb:etX.getBullets()){
                        hitTank(eb,hero);
                    }
                }

                this.repaint();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
