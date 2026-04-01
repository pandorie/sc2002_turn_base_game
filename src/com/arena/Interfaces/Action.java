package src.com.arena.Interfaces;

// Action.java
public interface Action {
    void execute(Combatant source, List<Combatant> targets, BattleContext ctx);
    String getName();
}
