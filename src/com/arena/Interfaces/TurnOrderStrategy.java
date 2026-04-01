package src.com.arena.Interfaces;
import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> getOrder(List<Combatant> combatants);
}
