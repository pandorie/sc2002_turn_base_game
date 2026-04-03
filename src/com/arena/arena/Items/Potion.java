package src.com.arena.Items;

import src.com.arena.Interfaces.Item;
import src.com.arena.Model.Combatant;

public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;
    private boolean used;

    public Potion() {
        this.used = false;
    }

    @Override
    public void use(Combatant target) {
        if (!used) {
            target.heal(HEAL_AMOUNT);
            used = true;
        }
    }

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}