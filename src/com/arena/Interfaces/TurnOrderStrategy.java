package src.com.arena.Interfaces;
import src.com.arena.Model.Combatant;
import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> getOrder(List<Combatant> combatants);

    List<Combatant> determineTurnOrder(List<Combatant> all);
}

