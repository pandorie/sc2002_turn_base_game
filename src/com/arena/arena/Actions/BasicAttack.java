// BasicAttack.java
package src.com.arena.Actions;

import src.com.arena.Interfaces.Action;
import src.com.arena.Model.Combatant;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public class BasicAttack implements Action {

    // No-arg constructor for use by BasicAttackBehaviour
    public BasicAttack() {}

    // Constructor kept for backward compatibility
    public BasicAttack(Combatant target) {}

    @Override
    public void execute(Combatant source, List<Combatant> targets, BattleContext ctx) {
        if (targets == null || targets.isEmpty()) return;
        Combatant target = targets.get(0);

        // Check SmokeBombEffect on target — if active, damage is 0
        boolean smokeActive = target.hasEffect(
            src.com.arena.Effects.SmokeBombEffect.class);
        int damage = smokeActive ? 0 : Math.max(0, source.getAtk() - target.getDef());

        target.takeDamage(damage);
        System.out.printf("  %s → BasicAttack → %s: %d dmg (HP: %d/%d)%n",
                source.getName(), target.getName(), damage,
                target.getHp(), target.getMaxHp());
    }

    @Override
    public String getName() { return "Basic Attack"; }
}