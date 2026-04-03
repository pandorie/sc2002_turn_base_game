// GameController.java
package src.com.arena.UI;

import src.com.arena.Engine.BattleContext;
import src.com.arena.Engine.BattleEngine;
import src.com.arena.Engine.Level;
import src.com.arena.Engine.SpeedBasedTurnOrder;
import src.com.arena.Interfaces.Item;
import src.com.arena.Model.Player;
import src.com.arena.Model.Warrior;
import src.com.arena.Model.Wizard;
import java.util.Arrays;

public class GameController {

    private final GameDisplay display;
    private final InputHandler input;

    public GameController() {
        this.display = new GameDisplay();
        this.input   = new InputHandler(display);
    }

    public void start() {
        display.showWelcome();

        boolean running = true;
        while (running) {
            Player player = setupPlayer();
            Level  level  = setupLevel();
            display.showSelectedSetup(player, level);

            runBattle(player, level);

            int choice = input.promptReplay();
            if (choice == 1) {
                player = createPlayer(classIndexOf(player), setupItems());
                level  = new Level(level.getDifficulty());
                runBattle(player, level);
            } else if (choice == 2) {
                // New game — loop back to top
            } else {
                running = false;
            }
        }

        display.showGoodbye();
    }

    private Player setupPlayer() {
        display.showPlayerClasses();
        int    classChoice = input.promptClassChoice();
        Item[] items       = setupItems();
        return createPlayer(classChoice, items);
    }

    private Item[] setupItems() {
        display.showItemOptions();
        return input.promptItemSelection();
    }

    private Level setupLevel() {
        display.showEnemyInfo();
        display.showDifficultyOptions();
        Level.Difficulty diff = input.promptDifficulty();
        return new Level(diff);
    }

    private Player createPlayer(int classChoice, Item[] items) {
        Player player = (classChoice == 1) ? new Warrior() : new Wizard();
        player.setInventory(Arrays.asList(items));
        return player;
    }

    private int classIndexOf(Player player) {
        return (player instanceof Warrior) ? 1 : 2;
    }

    private void runBattle(Player player, Level level) {
        BattleContext context = new BattleContext(player);

        BattleEngine engine = new BattleEngine(
                context,
                level,
                new SpeedBasedTurnOrder(),
                display,
                input
        );

        engine.run();
    }
}