package src.com.arena.Interfaces;

import src.com.arena.Model.Combatant;

public interface StatusEffect {
    void apply(Combatant c);
    void tick(Combatant c);
    boolean isExpired();
    String getName();
}
