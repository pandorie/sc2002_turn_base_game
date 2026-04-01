package src.com.arena.Interfaces;
import src.com.arena.Model.Combatant;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public interface Action {
    void execute(Combatant source, List<Combatant> targets, BattleContext ctx);
    String getName();
}
