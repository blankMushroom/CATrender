package ru.lnmo.render;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class objReader {
    static HashMap<Integer, double[]> vert=new HashMap<>();
    static HashMap<Integer, int[]> tri=new HashMap<>();
    private File uaz=new File("uaz.obj");
    Scanner s;

    {
        try {
            s = new Scanner(uaz);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    {
        int iv=1;
        int it=1;
        List<String> lines = new ArrayList<>();
        try {
             lines = Files.readAllLines(uaz.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String j : lines) {
            if(j.length() == 0) continue;
            if (j.charAt(0) == 'v'&&j.charAt(1)==' ') {
                j = j.substring(2);
                double[] m = {Double.parseDouble(j.split(" ")[0]), Double.parseDouble(j.split(" ")[1]), Double.parseDouble(j.split(" ")[2])};
                vert.put(iv, m);
                iv++;
            } else if (j.charAt(0) == 'f') {
                j = j.substring(2);
                int[] m = {Integer.parseInt(j.split(" ")[0].split("/")[0]), Integer.parseInt(j.split(" ")[1].split("/")[0]), Integer.parseInt(j.split(" ")[2].split("/")[0])};
                tri.put(it, m);
                it++;
            }
        }
    }
    public static HashMap<Integer,double[]> readv(){
        return vert;
    }
    public static HashMap<Integer,int[]> readt(){
        return tri;
    }
}
