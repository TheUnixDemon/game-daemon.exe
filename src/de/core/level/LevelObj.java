package de.core.level;

import java.io.*;
import java.util.*;

/**
 * saves and loads level object
 */
public class LevelObj {
    private final String filename = "./resources/level/level.obj"; // level
    private ArrayList<Level> levels;
    public LevelObj() {
        levels = new ArrayList<>();
    }
    public void addLevel(Level level) { 
        levels.add(level);
    }
    public void save() throws IOException {
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(filename));
        o.writeObject(levels); // saves levels as file
        o.close();
    }
    public void read() throws IOException, ClassCastException {
        ObjectInputStream i = new ObjectInputStream(new FileInputStream(filename));
        levels = (ArrayList<Level>) i.readObject();
        i.close();
    }
    public ArrayList<Level> get() {
        return levels;
    }