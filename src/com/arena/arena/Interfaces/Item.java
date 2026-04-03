package src.com.arena.Interfaces;

import src.com.arena.Model.Combatant;

public interface Item {
    void use(Combatant target);
    String getName();
    boolean isUsed();
}
