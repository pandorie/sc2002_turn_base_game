// Warrior.java
package src.com.arena.Model;
import src.com.arena.Effects.StunEffect;
import src.com.arena.Interfaces.Item;
import java.util.ArrayList;
import java.util.List;

public class Warrior extends Player {
    private static final int BASE_HP=260, BASE_ATK=40, BASE_DEF=20,
                             BASE_SPD=30, SKILL_COOLDOWN=3;

    public Warrior() { super("Warrior", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD); }

    @Override
    public void executeSpecialSkill(List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) return;
        Combatant target = targets.get(0);
        int damage = Math.max(0, this.atk - target.getDef());
        target.takeDamage(damage);
        target.applyEffect(new StunEffect());
        this.startCooldown(SKILL_COOLDOWN);
        System.out.printf("  %s → ShieldBash → %s: %d dmg [STUNNED]%n",
                getName(), target.getName(), damage);
    }

    public void shieldBash(Combatant target) {
        executeSpecialSkill(List.of(target));
    }

    @Override
    public void setInventory(List<Item> items) {
        this.inventory = new ArrayList<>(items);
    }
}