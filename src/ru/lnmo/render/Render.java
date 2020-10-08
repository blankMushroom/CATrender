package ru.lnmo.render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Render {

    public static void render(BufferedImage img){
//        img.setRGB(500, 300, new Color(255, 0, 200).getRGB());
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB(i, j, new Color(i * j % 256, (i + j) % 256, (i * i + j * j) % 256).getRGB() );
            }
        }
    }

    //Стоит начать с этого
    public static void renderLine(BufferedImage img, int x1, int y1, int x2, int y2, int rgb){
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        if (dx >= dy) {
            if (x2 < x1) {
                int tx = x1;
                x1 = x2;
                x2 = tx;
                int ty = y1;
                y1 = y2;
                y2 = ty;
            }
            int dirY = (int) Math.signum(y2 - y1);
            int error = 0;
            int dError = dy;
            int y = y1;
            for (int x = x1; x <= x2; x++) {
                img.setRGB(x, y, rgb);
                error += dError;
                if ((error << 1) >= dx) {
                    y += dirY;
                    error -= dx;
                }
            }
        } else {
            if (y2 < y1) {
                int tx = x1;
                x1 = x2;
                x2 = tx;
                int ty = y1;
                y1 = y2;
                y2 = ty;
            }
            int dirX = (int) Math.signum(x2 - x1);
            int error = 0;
            int dError = dx;
            int x = x1;
            for (int y = y1; y <= y2; y++) {
                img.setRGB(x, y, rgb);
                error += dError;
                if (error << 1 >= dy) {
                    x += dirX;
                    error -= dy;
                }
            }
        }
    }
    public static boolean isintriangle(double x1,double x2,double x3,double x4,double y1,double y2,double y3,double y4){
        double u=-(x1*y3-x3*y1+x4*y1-x1*y4+x3*y4-x4*y3)/(x2*y3-x3*y2+x3*y1-x1*y3+x1*y2-x2*y1);
        double v=-(x2*y1-x1*y2+x1*y4-x4*y1+x4*y2-x2*y4)/(x2*y3-x3*y2+x3*y1-x1*y3+x1*y2-x2*y1);
        return v >= 0 & u >= 0 & v + u <= 1;
    }
    public static void rendertrialngle(BufferedImage img, int rgb,int x1, int y1, int x2, int y2, int x3, int y3){
        Random r=new Random();
        int minx=Math.max(Math.min(x1,Math.min(x2,x3)),0);
        int maxx=Math.min(Math.max(x1,Math.max(x2,x3)),Main.w);
        int miny=Math.max(Math.min(y1,Math.min(y2,y3)),0);
        int maxy=Math.min(Math.max(y1,Math.max(y2,y3)),Main.h);
        for (int i = minx; i <= maxx; i++) {
            for (int j = miny; j <= maxy; j++) {
                if(isintriangle(x1,x2,x3,i,y1,y2,y3,j)){

                    img.setRGB(i, j,/*new Color(r.nextFloat(),r.nextFloat(),r.nextFloat()).getRGB()-new Color(i * j % 256, (i + j) % 256, (i * i + j * j) % 256).getRGB()*/rgb);
                }
            }
        }
    }
    public static void renderobj(BufferedImage img,int rgb){
        int cx=400;
        int cy=200;
        objReader r=new objReader();
        HashMap<Integer,double[]>vert=r.readv();
        HashMap<Integer,int[]>tri=r.readt();
        Random rand=new Random();
        for (int i = 1; i <= tri.size(); i++) {
            System.out.println(Arrays.toString(tri.get(i)));
            System.out.println(vert.get(tri.get(i)[0])[0]+
                    " "+vert.get(tri.get(i)[0])[1]+
                    " "+vert.get(tri.get(i)[1])[0]+
                    " "+vert.get(tri.get(i)[1])[1]+" "+vert.get(tri.get(i)[2])[0]+" "+vert.get(tri.get(i)[2])[1]);
            Render.rendertrialngle(img,new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat()).getRGB(),(int)vert.get(tri.get(i)[0])[0]+cx,(int)vert.get(tri.get(i)[0])[1]+cy,(int)vert.get(tri.get(i)[1])[0]+cx,(int)vert.get(tri.get(i)[1])[1]+cy,(int)vert.get(tri.get(i)[2])[0]+cx,(int)vert.get(tri.get(i)[2])[1]+cy);
        }
    }
}
