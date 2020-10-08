package ru.lnmo.render;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
public class objReader {
    static HashMap<Integer, int[]> vert=new HashMap<>();
    static HashMap<Integer, int[]> tri=new HashMap<>();
    private File uaz;
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
        for (String j = s.nextLine(); j !=null ;j=s.nextLine()) {
            if(j.charAt(0)=='v'){
                j=j.substring(2);
                int[]m={Integer.parseInt(j.split(" ")[0]),Integer.parseInt(j.split(" ")[1]),Integer.parseInt(j.split(" ")[2])};
                vert.put(iv,m);
                iv++;
            }else if(j.charAt(0)=='f'){
                j=j.substring(2);
                int[]m={Integer.parseInt(j.split(" ")[0].split("/")[0]),Integer.parseInt(j.split(" ")[1].split("/")[0]),Integer.parseInt(j.split(" ")[2].split("/")[0])};
                tri.put(it,m);
                it++;
            }

        }
    }
    public static HashMap<Integer,int[]> readv(){
        return vert;
    }
    public static HashMap<Integer,int[]> readt(){
        return tri;
    }
}
