package src.com.arena.Interfaces;

public interface Item {
    void use(Combatant target);
    String getName();
    boolean isUsed();
}
