package src.com.arena.Model;

import java.util.List;

import src.com.arena.Interfaces.Item;
import src.com.arena.Interfaces.StatusEffect;

public class Wizard extends Player {
    private static final int BASE_HP  = 200;
    private static final int BASE_ATK = 50;
    private static final int BASE_DEF = 10;
    private static final int BASE_SPD = 20;
    private static final int SKILL_COOLDOWN = 3;
    private static final int ATK_BONUS_PER_KILL = 10;
    private int stunDuration;

    public Wizard() {
        super("Wizard", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD);
    }

    @Override
    public void executeSpecialSkill(List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) return;
        // Process each target sequentially — ATK updates after each kill
        for (Combatant target : targets) {
            if (!target.isAlive()) continue;
            int damage = Math.max(0, this.atk - target.getDef());
            target.takeDamage(damage);
            // If this hit eliminated the target, gain ATK bonus immediately
            if (!target.isAlive()) {
                this.atk += ATK_BONUS_PER_KILL;
            }
        }
        this.startCooldown(SKILL_COOLDOWN);
    }

    public void arcaneBlast(List<Combatant> targets) {
        executeSpecialSkill(targets);
    }

    @Override
    public int getSpeed() {
        return this.spd;
    }

    @Override
    public void decrementStun() {
        if (this.stunDuration > 0) {
            this.stunDuration--;
        }
    }

    @Override
    public void cleanExpiredEffects() {
        // Remove expired status effects
    }

    @Override
    public List<StatusEffect> getStatusEffects() {
        return this.getStatusEffects();
    }

    @Override
    public boolean isStunned() {
        return this.stunDuration > 0;
    }

    @Override
    public void setInventory(List<Item> asList) {
    }
}
