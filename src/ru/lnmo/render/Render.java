package ru.lnmo.render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Render {
    static double[][] Zbuffer=new double[Main.w][Main.h];
    static {
        for (int i = 0; i < Zbuffer.length; i++) {
            for (int j = 0; j < Zbuffer[0].length; j++) {
                Zbuffer[i][j]=100_000;
            }
        }
    }
    static double alphax=0;
    static double alphay=0;
    static double alphaz=0;
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
    public static double getZ(double x1,double x2,double x3,double x4,double y1,double y2,double y3,double y4,double z1,double z2,double z3){
        double u=-(x1*y3-x3*y1+x4*y1-x1*y4+x3*y4-x4*y3)/(x2*y3-x3*y2+x3*y1-x1*y3+x1*y2-x2*y1);
        double v=-(x2*y1-x1*y2+x1*y4-x4*y1+x4*y2-x2*y4)/(x2*y3-x3*y2+x3*y1-x1*y3+x1*y2-x2*y1);
        return u*z2+v*z3+(1-u-v)*z1;
    }
    public static void rendertrialngle(BufferedImage img, int rgb,int x1, int y1, int x2, int y2, int x3, int y3,int z1,int z2,int z3){
        Random r=new Random();
        int minx=Math.max(Math.min(x1,Math.min(x2,x3)),0);
        int maxx=Math.min(Math.max(x1,Math.max(x2,x3)),Main.w);
        int miny=Math.max(Math.min(y1,Math.min(y2,y3)),0);
        int maxy=Math.min(Math.max(y1,Math.max(y2,y3)),Main.h);
        for (int i = minx; i <= maxx; i++) {
            for (int j = miny; j <= maxy; j++) {
                if(isintriangle(x1,x2,x3,i,y1,y2,y3,j)){

                    if(getZ(x1,x2,x3,i,y1,y2,y3,j,z1,z2,z3)<=Zbuffer[i][j]) {
                        Zbuffer[i][j] = getZ(x1, x2, x3, i, y1, y2, y3, j, z1, z2, z3);
                        img.setRGB(i, j,/*new Color(r.nextFloat(),r.nextFloat(),r.nextFloat()).getRGB()-new Color(i * j % 256, (i + j) % 256, (i * i + j * j) % 256).getRGB()*/rgb);
                    //} else {
//                        System.out.println(getZ(x1,x2,x3,i,y1,y2,y3,j,z1,z2,z3)   +" " + Zbuffer[i][j] + " " + (-getZ(x1,x2,x3,i,y1,y2,y3,j,z1,z2,z3)  + Zbuffer[i][j]));
//                        img.setRGB(i, j,Color.BLACK.getRGB());
                    }
                }
            }
        }
    }
    public static void renderobj(BufferedImage img,int rgb){
        int cx=400;
        int cy=150;
        objReader r=new objReader();
        HashMap<Integer,double[]>vert=r.readv();
        HashMap<Integer,int[]>tri=r.readt();
        Random rand=new Random();
        for (int i = 1; i <= tri.size(); i++) {
            int x1=(int)vert.get(tri.get(i)[0])[0];
            int x2=(int)vert.get(tri.get(i)[1])[0];
            int x3=(int)vert.get(tri.get(i)[2])[0];
            int y1=(int)vert.get(tri.get(i)[0])[1];
            int y2=(int)vert.get(tri.get(i)[1])[1];
            int y3=(int)vert.get(tri.get(i)[2])[1];
            int z1=(int)vert.get(tri.get(i)[0])[2];
            int z2=(int)vert.get(tri.get(i)[1])[2];
            int z3=(int)vert.get(tri.get(i)[2])[2];

            if (alphax!=0){
                double []v1={x1,y1,z1};
                double []v2={x2,y2,z2};
                double []v3={x3,y3,z3};
                v1=Matrixop.Rotx(v1,alphax);
                v2=Matrixop.Rotx(v2,alphax);
                v3=Matrixop.Rotx(v3,alphax);
                x1=(int)v1[0];
                x2=(int)v2[0];
                x3=(int)v3[0];
                y1=(int)v1[1];
                y2=(int)v2[1];
                y3=(int)v3[1];
                z1=(int)v1[2];
                z2=(int)v2[2];
                z3=(int)v3[2];
            }
            /*if (alphay!=0){
                int []v1={x1,y1,z1};
                int []v2={x2,y2,z2};
                int []v3={x3,y3,z3};
                v1=Matrixop.Roty(v1,alphay);
                v2=Matrixop.Roty(v2,alphay);
                v3=Matrixop.Roty(v3,alphay);
                x1=v1[0];
                x2=v2[0];
                x3=v3[0];
                y1=v1[1];
                y2=v2[1];
                y3=v3[1];
                z1=v1[2];
                z2=v2[2];
                z3=v3[2];
            }
            if (alphaz!=0){
                int []v1={x1,y1,z1};
                int []v2={x2,y2,z2};
                int []v3={x3,y3,z3};
                v1=Matrixop.Rotz(v1,alphaz);
                v2=Matrixop.Rotz(v2,alphaz);
                v3=Matrixop.Rotz(v3,alphaz);
                x1=v1[0];
                x2=v2[0];
                x3=v3[0];
                y1=v1[1];
                y2=v2[1];
                y3=v3[1];
                z1=v1[2];
                z2=v2[2];
                z3=v3[2];
            }*/
            System.out.println(x1+"  "+x2+"  "+x3+"  "+y1+"  "+y2+"  "+y3+"  "+z1+"  "+z2+"  "+z3);
            /*System.out.println(Arrays.toString(tri.get(i)));
            System.out.println(vert.get(tri.get(i)[0])[0]+
                    " "+vert.get(tri.get(i)[0])[1]+
                    " "+vert.get(tri.get(i)[1])[0]+
                    " "+vert.get(tri.get(i)[1])[1]+" "+vert.get(tri.get(i)[2])[0]+" "+vert.get(tri.get(i)[2])[1]);*/
            Render.rendertrialngle(img,new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat()).getRGB(),x1+cx,y1+cy,x2+cx,y2+cy,x3+cx,y3+cy,z1,z2,z3);
        }
        alphax-=0.05;
        alphay-=0.05;
        for (int i = 0; i < Zbuffer.length; i++) {
            for (int j = 0; j < Zbuffer[0].length; j++) {
                Zbuffer[i][j]=Double.MAX_VALUE;
            }
        }
    }
}
