package src.com.arena.Interfaces;

public interface StatusEffect {
    void apply(Combatant c);
    void tick(Combatant c);
    boolean isExpired();
    String getName();
}
