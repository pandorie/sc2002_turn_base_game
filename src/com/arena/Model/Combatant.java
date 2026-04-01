// Combatant.java
package src.com.arena.Model;

import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.StatusEffect;
import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int atk;
    protected int def;
    protected int spd;
    protected List<StatusEffect> activeEffects;

    public Combatant(String name, int hp, int atk, int def, int spd) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
        this.activeEffects = new ArrayList<>();
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage);
        this.hp = Math.max(0, this.hp - actualDamage);
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public void applyEffect(StatusEffect effect) {
        activeEffects.add(effect);
        effect.apply(this);
    }

    public void tickEffects() {
        for (StatusEffect effect : new ArrayList<>(activeEffects)) {
            effect.tick(this);
        }
        activeEffects.removeIf(StatusEffect::isExpired);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public boolean hasEffect(Class<? extends StatusEffect> effectClass) {
        for (StatusEffect effect : activeEffects) {
            if (effectClass.isInstance(effect)) {
                return true;
            }
        }
        return false;
    }

    // Getters
    public String getName()  { return name; }
    public int getHp()       { return hp; }
    public int getMaxHp()    { return maxHp; }
    public int getAtk()      { return atk; }
    public int getDef()      { return def; }
    public int getSpd()      { return spd; }
    public List<StatusEffect> getActiveEffects() { return activeEffects; }

    // Setters
    public void setAtk(int atk) { this.atk = atk; }
    public void setDef(int def) { this.def = def; }
    public void setHp(int hp)   { this.hp = Math.max(0, Math.min(maxHp, hp)); }
}