// BattleEngine.java
package src.com.arena.Engine;

import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.TurnOrderStrategy;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Enemy;
import src.com.arena.Model.Player;
import src.com.arena.UI.GameDisplay;
import src.com.arena.UI.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {

    private final BattleContext context;
    private final Level level;
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameDisplay display;
    private final InputHandler input;

    public BattleEngine(BattleContext context,
                        Level level,
                        TurnOrderStrategy turnOrderStrategy,
                        GameDisplay display,
                        InputHandler input) {
        this.context = context;
        this.level = level;
        this.turnOrderStrategy = turnOrderStrategy;
        this.display = display;
        this.input = input;
    }

    public void run() {
        context.addEnemies(level.createInitialEnemies());
        display.showBattleStart(context);

        while (!context.isGameOver()) {
            context.incrementRound();
            display.showRoundHeader(context.getRoundNumber());

            List<Combatant> turnOrder = buildTurnOrder();

            for (Combatant current : turnOrder) {
                if (context.isGameOver()) break;
                if (current.isDefeated()) continue;
                processTurn(current);
                checkGameEnd();
            }

            if (!context.isGameOver()) {
                tryBackupSpawn();
                display.showEndOfRound(context);
            }
        }
    }

    private List<Combatant> buildTurnOrder() {
        List<Combatant> all = new ArrayList<>();
        all.add(context.getPlayer());
        all.addAll(context.getLiveEnemies());
        return turnOrderStrategy.getOrder(all);
    }

    private void processTurn(Combatant current) {
        display.showTurnHeader(current);

        // 1. Tick status effects at start of turn
        current.tickEffects();

        // 2. Skip turn if stunned
        if (current.isStunned()) {
            display.showStunSkip(current);
            return;
        }

        // 3. Choose and execute action
        Action action = chooseAction(current);
        if (action != null) {
            List<Combatant> targets = context.getTargetsFor(current);
            action.execute(current, targets, context);
            display.showActionResult(current, action);
        }

        // 4. Decrement skill cooldown after player acts
        if (current instanceof Player) {
            ((Player) current).decrementCooldown();
        }
    }

    private Action chooseAction(Combatant current) {
        if (current instanceof Player) {
            return input.promptPlayerAction((Player) current, context);
        } else if (current instanceof Enemy) {
            return ((Enemy) current).getBehaviour().decideAction((Enemy) current, context);
        }
        return null;
    }

    private void checkGameEnd() {
        if (context.getPlayer().isDefeated()) {
            context.setPlayerWon(false);
            display.showDefeat(context);
        } else if (context.allEnemiesDefeated() && !canSpawnMore()) {
            context.setPlayerWon(true);
            display.showVictory(context);
        }
    }

    private void tryBackupSpawn() {
        if (context.allEnemiesDefeated() && level.hasBackup() && !level.isBackupSpawned()) {
            List<Combatant> backup = level.createBackupEnemies();
            if (backup != null && !backup.isEmpty()) {
                context.addEnemies(backup);
                display.showBackupSpawn(backup);
            }
        }
    }

    private boolean canSpawnMore() {
        return level.hasBackup() && !level.isBackupSpawned();
    }
}