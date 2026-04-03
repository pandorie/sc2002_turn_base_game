package src.com.arena.UI;

import src.com.arena.Actions.BasicAttack;
import src.com.arena.Actions.Defend;
import src.com.arena.Actions.SpecialSkill;
import src.com.arena.Actions.UseItem;
import src.com.arena.Engine.BattleContext;
import src.com.arena.Engine.Level;
import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.Item;
import src.com.arena.Items.Potion;
import src.com.arena.Items.PowerStone;
import src.com.arena.Items.SmokeBomb;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Player;

import java.util.List;
import java.util.Scanner;

/**
 * InputHandler reads and validates all player input from the console.
 *
 * SRP  : solely responsible for input; display is delegated to GameDisplay.
 * ISP  : only exposes input-related methods.
 * DIP  : depends on the Action interface, not concrete action classes.
 */
public class InputHandler {

    private final Scanner scanner;
    private final GameDisplay display;

    public InputHandler(GameDisplay display) {
        this.scanner = new Scanner(System.in);
        this.display = display;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Setup prompts
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Prompts the user to choose a player class (1 = Warrior, 2 = Wizard).
     * Returns the integer choice.
     */
    public int promptClassChoice() {
        return readInt("  Choose your class [1-2]: ", 1, 2);
    }

    /**
     * Prompts the user to choose a difficulty level.
     * Returns the Level.Difficulty enum value.
     */
    public Level.Difficulty promptDifficulty() {
        int choice = readInt("  Choose difficulty [1-3]: ", 1, 3);
        switch (choice) {
            case 1: return Level.Difficulty.EASY;
            case 2: return Level.Difficulty.MEDIUM;
            default: return Level.Difficulty.HARD;
        }
    }

    /**
     * Prompts the user to pick two items (duplicates allowed).
     * Returns an array of two Item instances.
     */
    public Item[] promptItemSelection() {
        Item[] items = new Item[2];
        System.out.println("  Pick item 1:");
        items[0] = pickItem();
        System.out.println("  Pick item 2:");
        items[1] = pickItem();
        return items;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Battle prompts
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Prompts the player to choose an action this turn.
     * Enforces cooldown rules and item availability.
     */
    public Action promptPlayerAction(Player player, BattleContext context) {
        display.showActionMenu(player);

        boolean hasItems = player.hasItemsLeft();
        int specialOpt = hasItems ? 4 : 3;

        while (true) {
            System.out.print("  Your choice: ");
            String raw = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                display.showInvalidInput();
                continue;
            }

            if (choice == 1) {
                // Basic Attack → pick a target
                Combatant target = promptTargetChoice(context.getLiveEnemies());
                if (target != null) return new BasicAttack(target);

            } else if (choice == 2) {
                return new Defend();

            } else if (choice == 3 && hasItems) {
                // Use Item
                Item item = promptItemUse(player);
                if (item != null) return new UseItem(item, player);

            } else if (choice == specialOpt) {
                // Special Skill
                if (player.getSpecialCooldown() > 0) {
                    System.out.printf("  Special skill is on cooldown (%d round(s) left).%n",
                            player.getSpecialCooldown());
                } else {
                    // Warrior: must pick a target; Wizard: targets all
                    if (requiresTarget(player)) {
                        Combatant target = promptTargetChoice(context.getLiveEnemies());
                        if (target != null) return new SpecialSkill();
                    } else {
                        return new SpecialSkill();
                    }
                }

            } else {
                display.showInvalidInput();
            }
        }
    }

    /**
     * Prompts the player to replay, start a new game, or exit.
     * Returns 1 (replay), 2 (new game), or 3 (exit).
     */
    public int promptReplay() {
        display.showReplayMenu();
        return readInt("  Your choice: ", 1, 3);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Private helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** Prompts the user to pick an item from the player's inventory. */
    private Item promptItemUse(Player player) {
        display.showItemMenu(player);
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("  No items left!");
            return null;
        }
        int choice = readInt("  Select item: ", 1, inventory.size());
        return inventory.get(choice - 1);
    }

    /** Prompts the user to pick a target from the list of live enemies. */
    private Combatant promptTargetChoice(List<Combatant> targets) {
        if (targets.isEmpty()) {
            System.out.println("  No valid targets!");
            return null;
        }
        display.showTargetMenu(targets);
        int choice = readInt("  Select target: ", 1, targets.size());
        return targets.get(choice - 1);
    }

    /** Returns the item chosen by the user (1-3 on the item menu). */
    private Item pickItem() {
        display.showItemOptions();
        int choice = readInt("  Your choice [1-3]: ", 1, 3);
        switch (choice) {
            case 1: return new Potion();
            case 2: return new PowerStone(null);
            default: return new SmokeBomb();
        }
    }

    /**
     * Warriors need a target for Shield Bash; Wizards do not (Arcane Blast
     * hits all enemies automatically).
     */
    private boolean requiresTarget(Player player) {
        return player.getClass().getSimpleName().equals("Warrior");
    }

    /**
     * Reads an integer from stdin within [min, max].
     * Re-prompts on invalid input.
     */
    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(line);
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) { }
            display.showInvalidInput();
        }
    }
}