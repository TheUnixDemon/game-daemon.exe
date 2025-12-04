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
    public Map(String filename) throws Exception {
        this.filename = filename;
        read(); // sets map through read map data
    }
    /**
     * 2d array representing the map using scanner to read the file
     * @return mapdata
     * @throws IOException read map file
     */
    public void read() throws IOException {
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