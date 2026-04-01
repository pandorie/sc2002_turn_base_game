package src.com.arena.Interfaces;


public interface EnemyBehaviourStrategy {
    Action decideAction(Enemy e, BattleContext ctx);
}
