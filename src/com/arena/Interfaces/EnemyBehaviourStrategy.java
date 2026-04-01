package src.com.arena.Interfaces;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Enemy;

import java.util.List;

import src.com.arena.Engine.BattleContext;


public interface EnemyBehaviourStrategy {
    Action decideAction(Enemy e, BattleContext ctx);

    Action chooseAction(Combatant enemy, List<Combatant> opponents);
}
