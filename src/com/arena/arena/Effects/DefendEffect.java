// DefendEffect.java
package src.com.arena.Effects;

import src.com.arena.Interfaces.StatusEffect;
import src.com.arena.Model.Combatant;

public class DefendEffect implements StatusEffect {
    private static final int DEFENSE_BONUS = 10;
    private static final int DEFEND_DURATION = 2;
    private int turnsRemaining;
    private boolean bonusApplied;

    public DefendEffect() {
        this.turnsRemaining = DEFEND_DURATION;
        this.bonusApplied = false;
    }

    @Override
    public void apply(Combatant c) {
        c.setDef(c.getDef() + DEFENSE_BONUS);
        bonusApplied = true;
    }

    @Override
    public void tick(Combatant c) {
        turnsRemaining--;
        // Remove defense bonus when effect expires
        if (isExpired() && bonusApplied) {
            c.setDef(c.getDef() - DEFENSE_BONUS);
            bonusApplied = false;
        }
    }

    @Override
    public boolean isExpired() {
        return turnsRemaining <= 0;
    }

    @Override
    public String getName() {
        return "Defend";
    }

    public int getTurnsRemaining() {
        return turnsRemaining;
    }
}