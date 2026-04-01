```
turn-based-combat-arena/
│
├── src/
│   └── com/
│       └── arena/
│           │
│           ├── interfaces/
│           │   ├── Action.java
│           │   ├── StatusEffect.java
│           │   ├── Item.java
│           │   ├── TurnOrderStrategy.java
│           │   └── EnemyBehaviourStrategy.java
│           │
│           ├── model/
│           │   ├── Combatant.java
│           │   ├── Player.java
│           │   ├── Enemy.java
│           │   ├── Warrior.java
│           │   ├── Wizard.java
│           │   ├── Goblin.java
│           │   └── Wolf.java
│           │
│           ├── effects/
│           │   ├── StunEffect.java
│           │   ├── DefendEffect.java
│           │   └── SmokeBombEffect.java
│           │
│           ├── items/
│           │   ├── Potion.java
│           │   ├── PowerStone.java
│           │   └── SmokeBomb.java
│           │
│           ├── actions/
│           │   ├── BasicAttack.java
│           │   ├── Defend.java
│           │   ├── UseItem.java
│           │   └── SpecialSkill.java
│           │
│           ├── engine/
│           │   ├── BattleContext.java
│           │   ├── BattleEngine.java
│           │   ├── Level.java
│           │   ├── SpeedBasedTurnOrder.java
│           │   └── BasicAttackBehaviour.java
│           │
│           ├── ui/
│           │   ├── GameDisplay.java
│           │   ├── InputHandler.java
│           │   └── GameController.java
│           │
│           └── Main.java
│
├── docs/
│   ├── UML_ClassDiagram.png
│   └── UML_SequenceDiagram.png
│
├── report/
│   └── SC2002_Report.pdf
│
├── .gitignore
└── README.md
```
