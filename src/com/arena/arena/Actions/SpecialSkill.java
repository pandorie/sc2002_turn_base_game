
package src.com.arena.Actions;

import src.com.arena.Interfaces.Action;
import src.com.arena.Model.Combatant;
import src.com.arena.Model.Player;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public class SpecialSkill implements Action {

    @Override
    public void execute(Combatant source, List<Combatant> targets, BattleContext ctx) {
        if (!(source instanceof Player)) return;
        Player player = (Player) source;
        if (!player.isSkillReady()) {
            System.out.println(player.getName() + "'s special skill is on cooldown ("
                    + player.getSkillCooldown() + " turns remaining).");
            return;
        }
        player.executeSpecialSkill(targets);
    }

    @Override
    public String getName() {
        return "Special Skill";
    }
}