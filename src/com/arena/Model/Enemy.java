// Enemy.java
package src.com.arena.Model;

import src.com.arena.Interfaces.Action;
import src.com.arena.Interfaces.EnemyBehaviourStrategy;
import src.com.arena.Engine.BattleContext;
import java.util.List;

public abstract class Enemy extends Combatant {
    protected EnemyBehaviourStrategy behaviour;

    public Enemy(String name, int hp, int atk, int def, int spd,
                 EnemyBehaviourStrategy behaviour) {
        super(name, hp, atk, def, spd);
        this.behaviour = behaviour;
    }

    public void takeTurn(BattleContext ctx) {
        Action action = behaviour.decideAction(this, ctx);
        List<Combatant> targets = ctx.getTargetsFor(this);
        action.execute(this, targets, ctx);
    }

    public EnemyBehaviourStrategy getBehaviour() { return behaviour; }
}