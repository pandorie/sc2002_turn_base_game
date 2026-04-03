// SmokeBomb.java
package src.com.arena.Items;

import src.com.arena.Effects.SmokeBombEffect;
import src.com.arena.Interfaces.Item;
import src.com.arena.Model.Combatant;

public class SmokeBomb implements Item {
    private boolean used;

    public SmokeBomb() {
        this.used = false;
    }

    @Override
    public void use(Combatant target) {
        if (!used) {
            target.applyEffect(new SmokeBombEffect());
            used = true;
        }
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}