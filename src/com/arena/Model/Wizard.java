// Wizard.java
package src.com.arena.Model;
import src.com.arena.Interfaces.Item;
import java.util.ArrayList;
import java.util.List;

public class Wizard extends Player {
    private static final int BASE_HP=200, BASE_ATK=50, BASE_DEF=10,
                             BASE_SPD=20, SKILL_COOLDOWN=3, ATK_BONUS=10;

    public Wizard() { super("Wizard", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD); }

    @Override
    public void executeSpecialSkill(List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) return;
        System.out.printf("  %s → ArcaneBlast → All enemies!%n", getName());
        for (Combatant target : targets) {
            if (!target.isAlive()) continue;
            int damage = Math.max(0, this.atk - target.getDef());
            target.takeDamage(damage);
            System.out.printf("    → %s: %d dmg (HP: %d/%d)%n",
                    target.getName(), damage, target.getHp(), target.getMaxHp());
            if (!target.isAlive()) {
                this.atk += ATK_BONUS;
                System.out.printf("    %s eliminated! Wizard ATK → %d%n",
                        target.getName(), this.atk);
            }
        }
        this.startCooldown(SKILL_COOLDOWN);
    }

    public void arcaneBlast(List<Combatant> targets) {
        executeSpecialSkill(targets);
    }

    @Override
    public void setInventory(List<Item> items) {
        this.inventory = new ArrayList<>(items);
    }
}