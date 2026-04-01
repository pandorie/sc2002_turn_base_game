// UseItem.java
package src.com.arena.Actions;

import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.Item;
import src.com.arena.Model.Combatant;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public class UseItem implements Action {
    private Item item;
    private Combatant itemTarget;

    public UseItem(Item item, Combatant itemTarget) {
        this.item = item;
        this.itemTarget = itemTarget;
    }

    @Override
    public void execute(Combatant source, List<Combatant> targets, BattleContext ctx) {
        if (item == null || item.isUsed()) return;
        item.use(itemTarget);
    }

    @Override
    public String getName() {
        return "Use Item: " + (item != null ? item.getName() : "none");
    }

    public Item getItem() {
        return item;
    }
}