package src.com.arena.Engine;

import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.EnemyBehaviourStrategy;
import src.com.arena.Interfaces.StatusEffect;
import src.com.arena.Interfaces.TurnOrderStrategy;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Player;
import src.com.arena.UI.GameDisplay;
import src.com.arena.UI.InputHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * BattleEngine orchestrates the turn-based battle loop.
 *
 * SRP  : manages battle flow only; display delegated to GameDisplay,
 *         input to InputHandler, state to BattleContext.
 * DIP  : depends on TurnOrderStrategy and EnemyBehaviourStrategy
 *         interfaces, not concrete implementations.
 * OCP  : adding new turn-order or AI strategies requires no change here.
 */
public class BattleEngine {

    private final BattleContext context;
    private final Level level;
    private final TurnOrderStrategy turnOrderStrategy;
    private final EnemyBehaviourStrategy enemyBehaviour;
    private final GameDisplay display;
    private final InputHandler input;

    public BattleEngine(BattleContext context,
                        Level level,
                        TurnOrderStrategy turnOrderStrategy,
                        EnemyBehaviourStrategy enemyBehaviour,
                        GameDisplay display,
                        InputHandler input) {
        this.context = context;
        this.level = level;
        this.turnOrderStrategy = turnOrderStrategy;
        this.enemyBehaviour = enemyBehaviour;
        this.display = display;
        this.input = input;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Public API
    // ─────────────────────────────────────────────────────────────────────────

    /** Runs the complete battle until the game ends. */
    public void run() {
        // Seed the initial enemy wave
        context.addEnemies(level.createInitialEnemies());
        display.showBattleStart(context);

        while (!context.isGameOver()) {
            context.incrementRound();
            display.showRoundHeader(context.getRoundNumber());

            // Build the ordered turn list for this round
            List<Combatant> turnOrder = buildTurnOrder();

            // Each combatant takes its turn
            for (Combatant current : turnOrder) {
                if (context.isGameOver()) break;
                if (current.isDefeated()) continue;

                processTurn(current);

                // Check game-end after every individual action
                checkGameEnd();
            }

            if (!context.isGameOver()) {
                // Try to trigger backup spawn between rounds
                tryBackupSpawn();
                display.showEndOfRound(context);
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Private helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** Builds an ordered list of all live combatants for this round. */
    private List<Combatant> buildTurnOrder() {
        List<Combatant> all = new ArrayList<>();
        all.add(context.getPlayer());
        all.addAll(context.getLiveEnemies());
        return turnOrderStrategy.determineTurnOrder(all);
    }

    /** Processes a single combatant's turn. */
    private void processTurn(Combatant current) {
        display.showTurnHeader(current);

        // 1. Apply existing status effects first
        applyStatusEffects(current);
        if (current.isDefeated()) return;

        // 2. Check if stunned (skip action)
        if (current.isStunned()) {
            display.showStunSkip(current);
            current.decrementStun();
            return;
        }

        // 3. Decrement action-blocking effects (e.g. defend, smoke bomb) that
        //    should tick even when the combatant acts
        current.tickEffects();

        // 4. Choose and execute action
        Action action = chooseAction(current);
        if (action != null) {
            List<Combatant> targets = context.getTargetsFor(current);
            action.execute(current, targets, context);
            display.showActionResult(current, action);
        }
    }

    /** Applies persistent status effects (e.g. DoT) at the start of a turn. */
    private void applyStatusEffects(Combatant combatant) {
        List<StatusEffect> effects = combatant.getStatusEffects();
        for (StatusEffect effect : new ArrayList<>(effects)) {
            effect.apply(combatant);
        }
        // Remove expired effects
        combatant.cleanExpiredEffects();
    }

    /**
     * Chooses an action for the combatant:
     * - Players: prompt via InputHandler
     * - Enemies: delegate to EnemyBehaviourStrategy
     */
    private Action chooseAction(Combatant current) {
        if (current instanceof Player) {
            Player player = (Player) current;
            return input.promptPlayerAction(player, context);
        } else {
            List<Combatant> opponents = new ArrayList<>();
            if (!context.getPlayer().isDefeated()) {
                opponents.add(context.getPlayer());
            }
            return enemyBehaviour.chooseAction(current, opponents);
        }
    }

    /** Checks if the battle has ended and updates context accordingly. */
    private void checkGameEnd() {
        if (context.getPlayer().isDefeated()) {
            context.setPlayerWon(false);
            display.showDefeat(context);
        } else if (context.allEnemiesDefeated() && !canSpawnMore()) {
            context.setPlayerWon(true);
            display.showVictory(context);
        }
    }

    /** Triggers backup spawn if conditions are met. */
    private void tryBackupSpawn() {
        if (context.allEnemiesDefeated() && level.hasBackup() && !level.isBackupSpawned()) {
            List<Combatant> backup = level.createBackupEnemies();
            if (backup != null && !backup.isEmpty()) {
                context.addEnemies(backup);
                display.showBackupSpawn(backup);
            }
        }
    }

    /** Returns true if there is still a backup wave that can be spawned. */
    private boolean canSpawnMore() {
        return level.hasBackup() && !level.isBackupSpawned();
    }
}