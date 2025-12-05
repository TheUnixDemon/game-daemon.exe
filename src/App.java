import de.core.level.*;
import de.core.*;
import de.core.control.GameController;

public class App {
    public static void main(String[] args) {
        // Der Pfad muss relativ zum Verzeichnis sein, aus dem das Programm ausgeführt wird (meist das Projekt-Stammverzeichnis).
        String levelPath = "./srv/level/map/map-1.txt"; 
        
        Level level = null;
        try {
            level = new Level(levelPath);
        } catch (Exception e) {
            System.out.println(e);
        }
        GameController controller = new GameController(level);

    }
}