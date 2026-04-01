// BasicAttack.java
package src.com.arena.Actions;

import src.com.arena.Interfaces.Action;
import src.com.arena.Model.Combatant;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public class BasicAttack implements Action {

    @Override
    public void execute(Combatant source, List<Combatant> targets, BattleContext ctx) {
        if (targets == null || targets.isEmpty()) return;
        Combatant target = targets.get(0);
        int damage = Math.max(0, source.getAtk() - target.getDef());
        target.takeDamage(damage);
    }

    @Override
    public String getName() {
        return "Basic Attack";
    }
}