// SmokeBombEffect.java
package src.com.arena.Effects;

import src.com.arena.Interfaces.StatusEffect;
import src.com.arena.Model.Combatant;

public class SmokeBombEffect implements StatusEffect {
    private static final int SMOKE_DURATION = 2;
    private int turnsRemaining;

    public SmokeBombEffect() {
        this.turnsRemaining = SMOKE_DURATION;
    }

    @Override
    public void apply(Combatant c) {
        // No stat change on apply — BattleEngine checks for this
        // effect before applying incoming damage
    }

    @Override
    public void tick(Combatant c) {
        turnsRemaining--;
    }

    @Override
    public boolean isExpired() {
        return turnsRemaining <= 0;
    }

    @Override
    public String getName() {
        return "SmokeBomb";
    }

    public int getTurnsRemaining() {
        return turnsRemaining;
    }
}