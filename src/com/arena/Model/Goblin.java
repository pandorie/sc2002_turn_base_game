// Goblin.java
package src.com.arena.Model;
import src.com.arena.Interfaces.EnemyBehaviourStrategy;

public class Goblin extends Enemy {
    private static final int BASE_HP=55, BASE_ATK=35, BASE_DEF=15, BASE_SPD=25;

    public Goblin(EnemyBehaviourStrategy behaviour) {
        super("Goblin", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD, behaviour);
    }
}