package de.core.level;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Utility class to create and collect Level objects from terminal input.
 * Levels are stored in a LevelObj instance, which can then save them.
 */
public class LevelCreate {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Level> levels = new ArrayList<>();
    
    /**
     * Guides the user through the terminal to input level paths and
     * optionally set maxSteps and maxTime for each level.
     * Stops reading when the user enters "-1".
     * @param levelObject The LevelObj instance to store the created levels.
     */
    public void createLevels(LevelObj levelObject) {
        String input;
        int levelCount = 0;

        System.out.println("--- Level Creation Mode ---");
        System.out.println("Enter level file path (or enter -1 to finish):");

        // Start the infinite input loop
        while (true) {
            System.out.print("Level " + (levelCount + 1) + " path: ");
            
            if (scanner.hasNextLine()) {
                input = scanner.nextLine().trim();

                // Check for exit condition
                if (input.equals("-1")) {
                    System.out.println("\n--- Creation Finished " + levelCount + " levels created ---");
                    break;
                }
                
                // Check for empty input (do not process empty line)
                if (input.isEmpty()) {
                    continue; // Skip the rest of the loop and prompt again
                }

                // --- TRY-CATCH BLOCK STARTET HIER ---
                try {
                    // Attempt to create the Level object                    
                    Level level = new Level(input);
                    
                    // --- Optional Metadata Input ---
                    // 1. Max Steps
                    System.out.print("Max Steps (int, leave blank for -1): ");
                    readAndSetIntAttribute(scanner, value -> level.setMaxSteps(value));
                    
                    // 2. Max Time
                    System.out.print("Max Time in seconds (long, leave blank for -1): ");
                    readAndSetLongAttribute(scanner, value -> level.setMaxTime(value));

                    // Add the successfully created and configured level
                    levelObject.addLevel(level);
                    levelCount++;
                    System.out.println("Level created from '" + input + "'");

                } catch (Exception e) {
                    // Fängt alle anderen Fehler während der Level-Erstellung ab
                    System.out.println("Failed to process level: " + e.getMessage());
                } finally {
                    scanner.close();
                }
            } else {
                // Diese Logik muss innerhalb der while(true) Schleife sein
                System.out.println("\nInput stream closed unexpectedly.");
                break;
            }
        }
    }
    
    /**
     * Reads a line and tries to parse it as an integer, setting the level attribute.
     * Skips setting the attribute if the input is blank.
     * @param scanner The Scanner object.
     * @param setter A functional interface to set the attribute (e.g., currentLevel::setMaxSteps).
     */
    private void readAndSetIntAttribute(Scanner scanner, IntConsumer setter) {
        // Schutz: prüfen, ob noch eine Zeile existiert (vermeidet NoSuchElementException)
        if (!scanner.hasNextLine()) {
            return;
        }
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return; // Attribute remains at default (-1)
        }
        try {
            int value = Integer.parseInt(line);
            setter.accept(value);
        } catch (NumberFormatException e) {
            System.err.println("  [WARNING] Invalid number format. Attribute not set (default -1 used).");
        }
    }

    /**
     * Reads a line and tries to parse it as a long, setting the level attribute.
     * Skips setting the attribute if the input is blank.
     * @param scanner The Scanner object.
     * @param setter A functional interface to set the attribute (e.g., currentLevel::setMaxTime).
     */
    private void readAndSetLongAttribute(Scanner scanner, LongConsumer setter) {
        // Schutz: prüfen, ob noch eine Zeile existiert (vermeidet NoSuchElementException)
        if (!scanner.hasNextLine()) {
            return;
        }
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return; // Attribute remains at default (-1)
        }
        try {
            long value = Long.parseLong(line);
            setter.accept(value);
        } catch (NumberFormatException e) {
            System.err.println("  [WARNING] Invalid number format. Attribute not set (default -1 used).");
        }
    }

    // Functional interfaces to simplify attribute setting (similar to Consumer but for primitive types)
    @FunctionalInterface
    private interface IntConsumer {
        void accept(int value);
    }

    @FunctionalInterface
    private interface LongConsumer {
        void accept(long value);
    }
}