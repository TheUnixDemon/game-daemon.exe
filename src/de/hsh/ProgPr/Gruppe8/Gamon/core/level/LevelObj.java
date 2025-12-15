package de.hsh.ProgPr.Gruppe8.Gamon.core.level;

import java.io.*;
import java.util.*;

/**
 * creates & loads level object
 * puts level in level arraylist for single object use
 */
public class LevelObj {
    private final String filename = "/resources/level/level.obj"; // level object path
    private ArrayList<Level> levels;

    /**
     * creates level object
     */
    public LevelObj() {
        levels = new ArrayList<>();
    }

    /**
     * adds to current level arraylist one level
     * @param level single one
     */
    public void addLevel(Level level) { 
        levels.add(level);
    }
    
    /**
     * saves levels ArrayList<Level> as object file
     */
    public void save() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(levels);
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * reads levels Arraylist<Level> out of object file
     */
    public void read() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filename));
            @SuppressWarnings("unchecked")
            ArrayList<Level> levels = (ArrayList<Level>) in.readObject();
            this.levels = levels;
            in.close(); // throws ioexception
        // replaces filenotfoundexpection
        } catch (IOException io) {
            System.out.println(io);
        } catch (ClassNotFoundException cl) {
            System.out.println(cl);
        }
    }

    /**
     * @return list of Level objects
     */
    public ArrayList<Level> getLevels() {
        return levels;
    }
}