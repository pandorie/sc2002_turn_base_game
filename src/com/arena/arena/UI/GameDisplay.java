package src.com.arena.UI;

import src.com.arena.Engine.BattleContext;
import src.com.arena.Engine.Level;
import src.com.arena.Interfaces.Action;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Player;

import java.util.List;

/**
 * GameDisplay is the sole class responsible for printing to the console.
 */
public class GameDisplay {

    private static final String DIVIDER =
            "═══════════════════════════════════════════════════════════";
    private static final String THIN =
            "───────────────────────────────────────────────────────────";

    // ─────────────────────────────────────────────────────────────────────────
    //  Loading / setup screens
    // ─────────────────────────────────────────────────────────────────────────

    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println("        ⚔  TURN-BASED COMBAT ARENA  ⚔");
        System.out.println(DIVIDER);
    }

    public void showPlayerClasses() {
        System.out.println("\n── Available Classes ──────────────────────────────────");
        System.out.println(" [1] Warrior");
        System.out.println("     HP: 260  |  ATK: 40  |  DEF: 20  |  SPD: 30");
        System.out.println("     Special : Shield Bash");
        System.out.println("               Deal BasicAttack damage + Stun target (2 turns)");
        System.out.println();
        System.out.println(" [2] Wizard");
        System.out.println("     HP: 200  |  ATK: 50  |  DEF: 10  |  SPD: 20");
        System.out.println("     Special : Arcane Blast");
        System.out.println("               Deal BasicAttack damage to ALL enemies;");
        System.out.println("               +10 ATK per kill (lasts until end of level)");
    }

    public void showEnemyInfo() {
        System.out.println("\n── Enemy Roster ───────────────────────────────────────");
        System.out.println(" Goblin  |  HP: 55  |  ATK: 35  |  DEF: 15  |  SPD: 25");
        System.out.println(" Wolf    |  HP: 40  |  ATK: 45  |  DEF:  5  |  SPD: 35");
    }

    public void showDifficultyOptions() {
        System.out.println("\n── Difficulty ─────────────────────────────────────────");
        System.out.println(" [1] Easy   — 3 Goblins");
        System.out.println(" [2] Medium — 1 Goblin + 1 Wolf  │ Backup: 2 Wolves");
        System.out.println(" [3] Hard   — 2 Goblins          │ Backup: 1 Goblin + 2 Wolves");
    }

    public void showItemOptions() {
        System.out.println("\n── Items (choose 2; duplicates allowed) ───────────────");
        System.out.println(" [1] Potion     — Heal 100 HP (capped at max HP)");
        System.out.println(" [2] Power Stone — Trigger your special skill for free");
        System.out.println(" [3] Smoke Bomb  — Enemy attacks deal 0 damage this turn + next");
    }

    public void showSelectedSetup(Player player, Level level) {
        System.out.println();
        System.out.println(THIN);
        System.out.printf("  Class      : %s%n", player.getClass().getSimpleName());
        System.out.printf("  Difficulty : %s%n", level);
        System.out.printf("  Items      : %s%n", player.getInventoryDescription());
        System.out.println(THIN);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Battle screens
    // ─────────────────────────────────────────────────────────────────────────

    public void showBattleStart(BattleContext ctx) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  ⚔  BATTLE START!");
        System.out.println(DIVIDER);
        showCombatantStatus(ctx);
    }

    public void showRoundHeader(int round) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.printf("  ROUND %d%n", round);
        System.out.println(DIVIDER);
    }

    public void showTurnHeader(Combatant combatant) {
        System.out.printf("%n  ── %s's turn ──%n", combatant.getName());
    }

    public void showStunSkip(Combatant combatant) {
        System.out.printf("  %s is STUNNED — turn skipped!%n", combatant.getName());
    }

    public void showActionResult(Combatant actor, Action action) {
        // Individual actions are responsible for printing their own narrative
        // via the execute() call; this hook lets us add a blank line after.
        System.out.println();
    }

    public void showBackupSpawn(List<Combatant> backups) {
        System.out.println();
        System.out.println("  !! BACKUP SPAWN !!");
        for (Combatant c : backups) {
            System.out.printf("     %s enters the arena! (HP: %d)%n",
                    c.getName(), c.getHp());
        }
    }

    public void showEndOfRound(BattleContext ctx) {
        System.out.println();
        System.out.println(THIN);
        System.out.println("  End-of-round status:");
        showCombatantStatus(ctx);
        System.out.println(THIN);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Action menu (called by InputHandler)
    // ─────────────────────────────────────────────────────────────────────────

    public void showActionMenu(Player player) {
        System.out.println("\n  Your actions:");
        System.out.println("   [1] Basic Attack");
        System.out.println("   [2] Defend");
        if (player.hasItemsLeft()) {
            System.out.println("   [3] Use Item");
        }
        int specialOpt = player.hasItemsLeft() ? 4 : 3;
        int cooldown = player.getSpecialCooldown();
        if (cooldown > 0) {
            System.out.printf("   [%d] Special Skill  (cooldown: %d round(s))%n",
                    specialOpt, cooldown);
        } else {
            System.out.printf("   [%d] Special Skill%n", specialOpt);
        }
    }

    public void showTargetMenu(List<Combatant> targets) {
        System.out.println("  Select target:");
        for (int i = 0; i < targets.size(); i++) {
            Combatant t = targets.get(i);
            System.out.printf("   [%d] %s  (HP: %d)%n", i + 1, t.getName(), t.getHp());
        }
    }

    public void showItemMenu(Player player) {
        System.out.println("  Select item to use:");
        player.printInventory();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  End screens
    // ─────────────────────────────────────────────────────────────────────────

    public void showVictory(BattleContext ctx) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  🏆  VICTORY!  Congratulations, you have defeated all your enemies!");
        System.out.println(DIVIDER);
        System.out.printf("  Remaining HP : %d / %d%n",
                ctx.getPlayer().getHp(), ctx.getPlayer().getMaxHp());
        System.out.printf("  Total Rounds : %d%n", ctx.getRoundNumber());
        System.out.println(THIN);
    }

    public void showDefeat(BattleContext ctx) {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  💀  DEFEATED.  Don't give up, try again!");
        System.out.println(DIVIDER);
        System.out.printf("  Enemies remaining   : %d%n", ctx.getLiveEnemies().size());
        System.out.printf("  Total Rounds Survived: %d%n", ctx.getRoundNumber());
        System.out.println(THIN);
    }

    public void showReplayMenu() {
        System.out.println("\n  What would you like to do?");
        System.out.println("   [1] Replay with same settings");
        System.out.println("   [2] New Game (return to main menu)");
        System.out.println("   [3] Exit");
    }

    public void showInvalidInput() {
        System.out.println("  ⚠  Invalid input. Please try again.");
    }

    public void showGoodbye() {
        System.out.println("\n  Thanks for playing! Goodbye. ⚔");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Helpers
    // ─────────────────────────────────────────────────────────────────────────

    private void showCombatantStatus(BattleContext ctx) {
        Player p = ctx.getPlayer();
        System.out.printf("  %-12s HP: %3d / %3d", p.getName(), p.getHp(), p.getMaxHp());
        if (p.getSpecialCooldown() > 0) {
            System.out.printf("  [Cooldown: %d]", p.getSpecialCooldown());
        }
        System.out.println();

        for (Combatant e : ctx.getAllEnemies()) {
            if (e.isDefeated()) {
                System.out.printf("  %-12s ✗ ELIMINATED%n", e.getName());
            } else {
                System.out.printf("  %-12s HP: %3d", e.getName(), e.getHp());
                if (e.isStunned()) System.out.print("  [STUNNED]");
                System.out.println();
            }
        }
    }
}