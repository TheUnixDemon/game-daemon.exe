import java.io.*;
import java.util.*;
import de.core.level.Level;

/** testing */
public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<char[]> map = readMap("./resources/level/maps/map-1.txt");
        Level l = new Level(map);
        System.out.println(l.getObstacles());
        System.out.println(l.getPlayer());
        System.out.println(l.getWalls());
    }
    public static ArrayList<char[]> readMap(String filePath) throws IOException {
        ArrayList<char[]> map = new ArrayList<>(); 
        Scanner scan = null;
        try {
            scan = new Scanner(new File(filePath));
            while(scan.hasNextLine()) {
                map.add(scan.nextLine().toCharArray());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return map;
    }
}
