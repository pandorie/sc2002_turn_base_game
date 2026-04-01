package src.com.arena.UI;

import src.com.arena.Engine.BasicAttackBehaviour;
import src.com.arena.Engine.BattleContext;
import src.com.arena.Engine.BattleEngine;
import src.com.arena.Engine.Level;
import src.com.arena.Engine.SpeedBasedTurnOrder;
import src.com.arena.Interfaces.Item;
import src.com.arena.Model.Player;
import src.com.arena.Model.Warrior;
import src.com.arena.Model.Wizard;

import java.util.Arrays;

/**
 * GameController is the top-level application controller.
 * It wires together all subsystems and manages the outer game loop
 * (setup → battle → replay / exit).
 *
 * SRP : orchestrates; does not contain battle logic or display logic.
 * DIP : constructs concrete implementations and injects them as interfaces
 *       into BattleEngine.
 */
public class GameController {

    private final GameDisplay display;
    private final InputHandler input;

    public GameController() {
        this.display = new GameDisplay();
        this.input = new InputHandler(display);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Entry point
    // ─────────────────────────────────────────────────────────────────────────

    public void start() {
        display.showWelcome();

        boolean running = true;
        while (running) {
            // ── Setup ─────────────────────────────────────────────────────────
            Player player = setupPlayer();
            Level level = setupLevel();
            display.showSelectedSetup(player, level);

            // ── Battle ────────────────────────────────────────────────────────
            runBattle(player, level);

            // ── Post-game ─────────────────────────────────────────────────────
            int replayChoice = input.promptReplay();
            if (replayChoice == 1) {
                // Replay same settings: recreate player (resets HP, cooldowns)
                // Level is re-created from the same difficulty
                player = createPlayer(classIndexOf(player), setupItems());
                level = new Level(level.getDifficulty());
                runBattle(player, level);
            } else if (replayChoice == 2) {
                // Fall through to the top of the loop (new game)
            } else {
                running = false;
            }
        }

        display.showGoodbye();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Private setup helpers
    // ─────────────────────────────────────────────────────────────────────────

    private Player setupPlayer() {
        display.showPlayerClasses();
        int classChoice = input.promptClassChoice();
        Item[] items = setupItems();
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

    /** Instantiates the chosen player class and gives it its items. */
    private Player createPlayer(int classChoice, Item[] items) {
        Player player = classChoice == 1 ? new Warrior() : new Wizard();
        player.setInventory(Arrays.asList(items));
        return player;
    }

    /** Returns 1 for Warrior, 2 for Wizard — used for replay. */
    private int classIndexOf(Player player) {
        return player instanceof Warrior ? 1 : 2;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Battle wiring
    // ─────────────────────────────────────────────────────────────────────────

    private void runBattle(Player player, Level level) {
        BattleContext context = new BattleContext(player);

        BattleEngine engine = new BattleEngine(
                context,
                level,
                new SpeedBasedTurnOrder(),
                new BasicAttackBehaviour(),
                display,
                input
        );

        engine.run();
    }
}