package com.tankGame;

public class Bullet {
    private int x;
    private int y;
    final int size=5;
    private  int direct;
    private final int speed=20;
    private boolean living=true;
    public Bullet(int x, int y,int direct) {
        this.x = x;
        this.y = y;
        this.direct=direct;
        fix();
    }

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

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public void move(){
        int delta_x=speed;
        int delta_y=speed;
        switch (direct){
            case 0 ://Front
                delta_x=0;
                delta_y=-delta_y;
                break;
            case 1://Back
                delta_x=0;
                break;
            case 2://Left
                delta_x=-delta_x;
                delta_y=0;
                break;
            case 3://Right
                delta_y=0;
                break;
        }
            x+=delta_x;
            y+=delta_y;

    }
    private void fix(){
        switch (direct){
            case 0 ://Front
                //修正子弹位置
                x+=15;
                y-=10;
                break;
            case 1://Back
                x+=15;
                y+=60;
                break;
            case 2://Left
                y+=15;
                x-=10;
                break;
            case 3://Right
                y+=15;
                x+=60;
                break;
        }
    }

    public boolean isLiving() {
        return living;
    }
    public void die(){
        living=false;
    }
}
