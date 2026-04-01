package src.com.arena;

import src.com.arena.UI.GameController;

/**
 * Main — application entry point.
 * Delegates immediately to GameController to keep this class trivially simple.
 */
public class Main {
    public static void main(String[] args) {
        new GameController().start();
    }
}