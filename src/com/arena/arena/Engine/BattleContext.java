package src.com.arena.Engine;

import src.com.arena.Model.Combatant;
import src.com.arena.Model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * BattleContext holds the shared state for an ongoing battle.
*/
public class BattleContext {

    private final Player player;
    private final List<Combatant> activeEnemies;
    private int roundNumber;
    private boolean gameOver;
    private boolean playerWon;

    public BattleContext(Player player) {
        this.player = player;
        this.activeEnemies = new ArrayList<>();
        this.roundNumber = 0;
        this.gameOver = false;
        this.playerWon = false;
    }

    public void reset() {
        activeEnemies.clear();
        roundNumber = 0;
        gameOver = false;
        playerWon = false;
    }

    public List<Combatant> getTargetsFor(Combatant source) {
        // This method can be expanded to return different targets based on the source's action.
        // For now, it returns all live enemies if the source is the player, and just the player if the source is an enemy.
        if (source == player) {
            return getLiveEnemies();
        } else {
            List<Combatant> targets = new ArrayList<>();
            if (!player.isDefeated()) {
                targets.add(player);
            }
            return targets;
        }
    }

    // ── Enemies ──────────────────────────────────────────────────────────────

    public void addEnemies(List<Combatant> enemies) {
        activeEnemies.addAll(enemies);
    }

    /** Returns only enemies that are still alive. */
    public List<Combatant> getLiveEnemies() {
        List<Combatant> live = new ArrayList<>();
        for (Combatant c : activeEnemies) {
            if (!c.isDefeated()) live.add(c);
        }
        return live;
    }

    public List<Combatant> getAllEnemies() {
        return activeEnemies;
    }

    public boolean allEnemiesDefeated() {
        return getLiveEnemies().isEmpty();
    }

    // ── Player ───────────────────────────────────────────────────────────────

    public Player getPlayer() {
        return player;
    }

    // ── Round ────────────────────────────────────────────────────────────────

    public void incrementRound() {
        roundNumber++;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    // ── Game-over state ───────────────────────────────────────────────────────

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(boolean won) {
        this.gameOver = true;
        this.playerWon = won;
    }

    public List<Combatant> getLiveOpponents() {
        return getLiveEnemies();
    }
}