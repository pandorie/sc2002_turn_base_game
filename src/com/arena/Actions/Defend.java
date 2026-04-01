// Defend.java
package src.com.arena.Actions;

import src.com.arena.Effects.DefendEffect;
import src.com.arena.Interfaces.Action;
import src.com.arena.Model.Combatant;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public class Defend implements Action {

    @Override
    public void execute(Combatant source, List<Combatant> targets, BattleContext ctx) {
        source.applyEffect(new DefendEffect());
    }

    @Override
    public String getName() {
        return "Defend";
    }
}