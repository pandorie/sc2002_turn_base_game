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
            // Trigger skill effect only — do NOT call startCooldown()
            // cooldown remains exactly as it was before this item was used
            player.executeSpecialSkill(targets);
            // Undo the cooldown that executeSpecialSkill just set
            // because PowerStone must not start or change the cooldown
            if (!player.isSkillReady()) {
                player.startCooldown(player.getSkillCooldown() - 3);
            }
            used = true;
        }
    }

    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}