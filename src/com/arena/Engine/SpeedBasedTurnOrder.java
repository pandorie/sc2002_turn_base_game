// SpeedBasedTurnOrder.java
package src.com.arena.Engine;
import src.com.arena.Interfaces.TurnOrderStrategy;
import src.com.arena.Model.Combatant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> getOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort(Comparator
                .comparingInt(Combatant::getSpd).reversed()
                .thenComparing(Combatant::getName));
        return ordered;
    }
}