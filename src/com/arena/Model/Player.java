// Player.java
package src.com.arena.Model;

import src.com.arena.Interfaces.Item;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant {
    protected List<Item> inventory;
    protected int skillCooldown;

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
        this.inventory = new ArrayList<>();
        this.skillCooldown = 0;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void useItem(Item item) {
        if (inventory.contains(item) && !item.isUsed()) {
            item.use(this);
        }
    }

    public void decrementCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }

    public boolean isSkillReady() {
        return skillCooldown == 0;
    }

    public void startCooldown(int turns) {
        this.skillCooldown = turns;
    }

    public abstract void executeSpecialSkill(List<Combatant> targets);

    // Getters
    public List<Item> getInventory()  { return inventory; }
    public int getSkillCooldown()     { return skillCooldown; }
}