package de.hsh.ProgPr.Gruppe8.Gamon.core.control;

/**
 * class for controlling exting or reentering in level menu
 * will be changed and controlled by the gameControll class mainly by passing instance through GameController
 */
public class GameStatus {
    private boolean gameStatus; // for exiting game and returning to games selection menu
    private boolean backToMenu; // for get back to level selection menu 

    public GameStatus() {
        gameStatus = true;
        backToMenu = false;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }
    public boolean getGameStatus() {
        return gameStatus;
    }

    public void setBackToMenu(boolean backToMenu) {
        this.backToMenu = backToMenu;
    }
    public boolean getBackToMenu() {
        return backToMenu;
    }
}
