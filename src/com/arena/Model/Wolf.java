// Wolf.java
package src.com.arena.Model;
import src.com.arena.Interfaces.EnemyBehaviourStrategy;

public class Wolf extends Enemy {
    private static final int BASE_HP=40, BASE_ATK=45, BASE_DEF=5, BASE_SPD=35;

    public Wolf(EnemyBehaviourStrategy behaviour) {
        super("Wolf", BASE_HP, BASE_ATK, BASE_DEF, BASE_SPD, behaviour);
    }
}