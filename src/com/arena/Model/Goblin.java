// Goblin.java
package src.com.arena.Model;

import src.com.arena.Interfaces.EnemyBehaviourStrategy;
import src.com.arena.Interfaces.StatusEffect;

public class Goblin extends Enemy {
    private static final int BASE_HP  = 55;
    private static final int BASE_ATK = 35;
    private static final int BASE_DEF = 15;
    private static final int BASE_SPD = 25;

    public Goblin(EnemyBehaviourStrategy behaviour) {
        super("Goblin", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD, behaviour);
    }

    @Override
    public int getSpeed() {
        return getSpeed();
    }

    @Override
    public void decrementStun() {
    }

    @Override
    public void cleanExpiredEffects() {
    }

    @Override
    public java.util.List<StatusEffect> getStatusEffects() {
        return new java.util.ArrayList<>();
    }

    @Override
    public boolean isStunned() {
        return false;
    }

}