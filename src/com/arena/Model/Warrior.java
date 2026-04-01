// Warrior.java
package src.com.arena.Model;

import src.com.arena.Effects.StunEffect;
import src.com.arena.Interfaces.Action;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public class Warrior extends Player {
    private static final int BASE_HP  = 260;
    private static final int BASE_ATK = 40;
    private static final int BASE_DEF = 20;
    private static final int BASE_SPD = 30;
    private static final int SKILL_COOLDOWN = 3;

    public Warrior() {
        super("Warrior", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD);
    }

    @Override
    public void executeSpecialSkill(List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) return;
        Combatant target = targets.get(0);
        // Deal BasicAttack damage
        int damage = Math.max(0, this.atk - target.getDef());
        target.takeDamage(damage);
        // Apply stun
        target.applyEffect(new StunEffect());
        // Start cooldown
        this.startCooldown(SKILL_COOLDOWN);
    }

    public void shieldBash(Combatant target) {
        int damage = Math.max(0, this.atk - target.getDef());
        target.takeDamage(damage);
        target.applyEffect(new StunEffect());
        this.startCooldown(SKILL_COOLDOWN);
    }
}