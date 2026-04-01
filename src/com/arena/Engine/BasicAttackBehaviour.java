package src.com.arena.Engine;

import src.com.arena.Actions.BasicAttack;
import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.EnemyBehaviourStrategy;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Enemy;

import java.util.List;
import java.util.Random;

/**
 * BasicAttackBehaviour always returns a BasicAttack targeting a random
 * live opponent.
 */
public class BasicAttackBehaviour implements EnemyBehaviourStrategy {

    private final Random random = new Random();

    @Override
    public Action chooseAction(Combatant enemy, List<Combatant> opponents) {
        if (opponents.isEmpty()) return null;

        // Pick a random live target from the opponent list
        Combatant target = opponents.get(random.nextInt(opponents.size()));
        return new BasicAttack(target);
    }

    @Override
    public Action decideAction(Enemy e, BattleContext ctx) {
        List<Combatant> opponents = ctx.getLiveOpponents();
        if (opponents.isEmpty()) return null;

        // Pick a random live target from the opponent list
        Combatant target = opponents.get(random.nextInt(opponents.size()));
        return new BasicAttack(target);
    }

}