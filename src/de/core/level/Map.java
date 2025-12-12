package de.core.level;

import java.io.*;
import java.util.*;

/**
 * loads map ascii file
 * generate 2d arraylist out of map file
 */
public class Map implements Serializable {
    private String filename;
    private ArrayList<char[]> map;

    /**
     * @param filename + path for reference
     */
    public Map(String filename) {
        this.filename = filename;
        read(); // sets map through read map data
    }

    public Map(ArrayList<char[]> map) {
        this.map = map;
    }

    /**
     * sets 2d array representing the map using scanner to read ascii map file
     */
    public void read() {
        ArrayList<char[]> map = new ArrayList<>();
        Scanner scan = null;
        try {
            scan = new Scanner(new File(filename));
            while(scan.hasNextLine()) {
                map.add(scan.nextLine().toCharArray());
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            scan.close();
        }
        this.map = map;
    }

    /**
     * @return full map as char 2d array
     */
    public ArrayList<char[]> getMap() {
        return map;
    }
}