// EnemyBehaviourStrategy.java
package src.com.arena.Interfaces;
import src.com.arena.Model.Enemy;
import src.com.arena.Engine.BattleContext;

public interface EnemyBehaviourStrategy {
    Action decideAction(Enemy e, BattleContext ctx);
}