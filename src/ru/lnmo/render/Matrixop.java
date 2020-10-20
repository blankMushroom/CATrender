package ru.lnmo.render;

public class Matrixop {
    public static int[]Mult(int[][]m,int[]v){
        int[]va=new int[v.length];
        for (int i = 0; i < va.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                va[i]+=m[i][j]*v[j];
            }
        }
        return va;
    }
    public static int[]Rotx(int[]v,double alpha){
        int[][] m={{1,0,0},{0, (int) Math.cos(alpha), (int) -Math.sin(alpha)},{0,(int)Math.sin(alpha),(int)Math.cos(alpha)}};
        v=Mult(m,v);
        return v;
    }
    public static int[]Roty(int[]v,double alpha){
        int[][] m={{(int) Math.cos(alpha),0,(int) Math.sin(alpha)},{0, 1, 0},{(int) -Math.sin(alpha),0,(int)Math.cos(alpha)}};
        v=Mult(m,v);
        return v;
    }
    public static int[]Rotz(int[]v,double alpha){
        int[][] m={{(int) Math.cos(alpha), (int) -Math.sin(alpha),0},{(int)Math.sin(alpha),(int)Math.cos(alpha),0},{0,0,1}};
        v=Mult(m,v);
        return v;
    }
}
