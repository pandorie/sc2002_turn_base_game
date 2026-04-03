// BasicAttackBehaviour.java
package src.com.arena.Engine;

import src.com.arena.Actions.BasicAttack;
import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.EnemyBehaviourStrategy;
import src.com.arena.Model.Enemy;

public class BasicAttackBehaviour implements EnemyBehaviourStrategy {

    @Override
    public Action decideAction(Enemy e, BattleContext ctx) {
        return new BasicAttack();
    }
}