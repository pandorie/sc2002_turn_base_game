package src.com.arena.Engine;

import src.com.arena.Interfaces.TurnOrderStrategy;
import src.com.arena.Model.Combatant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SpeedBasedTurnOrder sorts all live combatants by speed descending.
 * Ties are broken deterministically: players before enemies, then by name.
 */
public class SpeedBasedTurnOrder implements TurnOrderStrategy {

    @Override
    public List<Combatant> getOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);

        ordered.sort(Comparator
                .comparing(Combatant::getSpd).reversed()
                .thenComparing(c -> c.getClass().getSimpleName())
                .thenComparing(Combatant::getName));

        return ordered;
    }

    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        return getOrder(combatants);
    }
}

