package com.tankGame;

import javax.swing.*;

public class TankGame extends JFrame {

    public static void main(String[] args){
        TankGame tankGame = new TankGame();
        new Thread(tankGame.mp).start();
    }

    private MyPanel mp=null;
    public TankGame(){
        mp=new MyPanel();
        this.add(mp);//把绘图区域（游戏绘图区域）加入窗口中
        this.setSize(1000,750);//设置窗口大小，与画图区大小保持一致 1000*750
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
