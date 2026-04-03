// StunEffect.java
package src.com.arena.Effects;

import src.com.arena.Interfaces.StatusEffect;
import src.com.arena.Model.Combatant;

public class StunEffect implements StatusEffect {
    private static final int STUN_DURATION = 2;
    private int turnsRemaining;

    public StunEffect() {
        this.turnsRemaining = STUN_DURATION;
    }

    @Override
    public void apply(Combatant c) {
        // No stat change on apply — stun just blocks action
        // BattleEngine checks hasEffect(StunEffect.class) before processing turn
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
        return "Stun";
    }

    public int getTurnsRemaining() {
        return turnsRemaining;
    }
}