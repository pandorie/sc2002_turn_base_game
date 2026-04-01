package src.com.arena.Engine;

import src.com.arena.Model.Combatant;
import src.com.arena.Model.Goblin;
import src.com.arena.Model.Wolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Level encapsulates difficulty configuration: which enemies spawn initially
 * and which enemies form the backup wave.
 */
public class Level {

    public enum Difficulty {
        EASY(1, "Easy"),
        MEDIUM(2, "Medium"),
        HARD(3, "Hard");

        private final int number;
        private final String label;

        Difficulty(int number, String label) {
            this.number = number;
            this.label = label;
        }

        public int getNumber() { return number; }
        public String getLabel() { return label; }
    }

    private final Difficulty difficulty;
    private boolean backupSpawned;

    public Level(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.backupSpawned = false;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    /** Returns the initial wave of enemies for this level. */
    public List<Combatant> createInitialEnemies() {
        List<Combatant> enemies = new ArrayList<>();
        switch (difficulty) {
            case EASY:
                // 3 Goblins: A, B, C
                enemies.add(new Goblin(new BasicAttackBehaviour()));
                enemies.add(new Goblin(new BasicAttackBehaviour()));
                enemies.add(new Goblin(new BasicAttackBehaviour()));
                break;
            case MEDIUM:
                // 1 Goblin + 1 Wolf
                enemies.add(new Goblin(new BasicAttackBehaviour()));
                enemies.add(new Wolf(new BasicAttackBehaviour()));
                break;
            case HARD:
                // 2 Goblins
                enemies.add(new Goblin(new BasicAttackBehaviour()));
                enemies.add(new Goblin(new BasicAttackBehaviour()));
                break;
        }
        return enemies;
    }

    /**
     * Returns the backup wave if it hasn't been spawned yet, otherwise null.
     * Call this only when the current active wave is fully defeated.
     */
    public List<Combatant> createBackupEnemies() {
        if (backupSpawned || !hasBackup()) return null;
        backupSpawned = true;

        List<Combatant> backup = new ArrayList<>();
        switch (difficulty) {
            case MEDIUM:
                // 2 Wolves
                backup.add(new Wolf(new BasicAttackBehaviour()));
                backup.add(new Wolf(new BasicAttackBehaviour()));
                break;
            case HARD:
                // 1 Goblin + 2 Wolves
                backup.add(new Goblin(new BasicAttackBehaviour()));
                backup.add(new Wolf(new BasicAttackBehaviour()));
                backup.add(new Wolf(new BasicAttackBehaviour()));
                break;
            default:
                break;
        }
        return backup;
    }

    public boolean hasBackup() {
        return difficulty == Difficulty.MEDIUM || difficulty == Difficulty.HARD;
    }

    public boolean isBackupSpawned() {
        return backupSpawned;
    }

    @Override
    public String toString() {
        return difficulty.getLabel();
    }
}
