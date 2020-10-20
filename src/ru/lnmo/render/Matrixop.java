package ru.lnmo.render;

public class Matrixop {
    public static double[]Mult(double[][]m,double[]v){
        double[]va=new double[v.length];
        for (int i = 0; i < va.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                va[i]+=m[i][j]*v[j];
            }
        }
        return va;
    }
    public static double[]Rotx(double[]v,double alpha){
        double[][] m={{1,0                     ,0                    },
                   {0,Math.cos(alpha),-Math.sin(alpha)},
                   {0,Math.sin(alpha),Math.cos(alpha)   }};
        v=Mult(m,v);
        return v;
    }
    public static double[]Roty(double[]v,double alpha){
        double[][] m={{Math.cos(alpha),0, Math.sin(alpha)},{0, 1, 0},{ -Math.sin(alpha),0,Math.cos(alpha)}};
        v=Mult(m,v);
        return v;
    }
    public static double[]Rotz(double[]v,double alpha){
        double[][] m={{ Math.cos(alpha),  -Math.sin(alpha),0},{Math.sin(alpha),Math.cos(alpha),0},{0,0,1}};
        v=Mult(m,v);
        return v;
    }
}
