// PowerStone.java
package src.com.arena.Items;
import src.com.arena.Interfaces.Item;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Player;
import java.util.List;

public class PowerStone implements Item {
    private boolean used;
    private List<Combatant> targets;

    public PowerStone(List<Combatant> targets) {
        this.used = false;
        this.targets = targets;
    }

    @Override
    public void use(Combatant target) {
        if (!used && target instanceof Player) {
            Player player = (Player) target;
            // Save cooldown before — PowerStone must not change it
            int cooldownBefore = player.getSkillCooldown();
            player.executeSpecialSkill(targets);
            // Restore exactly — undo whatever startCooldown() did inside executeSpecialSkill
            player.startCooldown(cooldownBefore);
            used = true;
        }
    }

    @Override public String getName() { return "Power Stone"; }
    @Override public boolean isUsed() { return used; }
}