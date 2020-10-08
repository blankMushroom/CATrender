package ru.lnmo.render;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Main extends JFrame {

    static final int w = 1366;
    static final int h = 768;

    public static void draw(Graphics2D g) {
        //Создаем буффер в который рисуем кадр.
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        //Рисуем кадр.
        //Render.render(img);
        //Render.rendertrialngle(img,Color.BLACK.getRGB(),360,600,350,200,370,220);
        Random rand=new Random();
        double x1=-1;
        double y1=0;
        double y2=0;
        int x0=500;
        int y0=300;
        int r =300;
        /*for (double i = -0.9; i < 1; i+=0.1) {
            Render.rendertrialngle(img,new Color((float)(Math.signum(i) * i),(float)(Math.signum(i) * i),(float)(Math.signum(i) * i) ).getRGB(),x0,y0,(int)(x1*r)+x0,(int)(y1*r)+y0,(int)(i*r)+x0,(int)(Math.sqrt(1-i*i)*r)+y0);
            Render.rendertrialngle(img,new Color((float)(Math.signum(i) * i),(float)(Math.signum(i) * i),(float)(Math.signum(i) * i)).getRGB(),x0,y0,(int)(x1*r)+x0,(int)(y2*r)+y0,(int)(i*r)+x0,(int)((-Math.sqrt(1-i*i))*r)+y0);
            x1=i;
            y1=Math.sqrt(1-i*i);
            y2=-Math.sqrt(1-i*i);
        }*/
        Render.renderobj(img,Color.BLACK.getRGB());
        g.drawImage(img, 0, 0, null);
    }



    //магический код позволяющий всему работать, лучше не трогать
    public static void main(String[] args) throws InterruptedException {
        Main jf = new Main();
        jf.setSize(w, h);//размер экрана
        jf.setUndecorated(false);//показать заголовок окна
        jf.setTitle("TES IV");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.createBufferStrategy(2);
        //в бесконечном цикле рисуем новый кадр
        while (true) {
            long frameLength = 1000 / 60; //пытаемся работать из рассчета  60 кадров в секунду
            long start = System.currentTimeMillis();
            BufferStrategy bs = jf.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
            draw(g);

            bs.show();
            g.dispose();

            long end = System.currentTimeMillis();
            long len = end - start;
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    //Вызывается когда клавиша отпущена пользователем, обработка события аналогична keyPressed
    public void keyReleased(KeyEvent e) {

    }
}
