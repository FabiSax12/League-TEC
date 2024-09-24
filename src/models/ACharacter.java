package models;

public abstract class ACharacter {
    protected String name;
    protected int level;
    protected int hitPoints;
    protected int mana;
    protected int attackMin;
    protected int defenseMin;
    protected int attackMax;
    protected int defenseMax;
    protected int movements;
    protected Element element;

    public abstract void move();
    public abstract void attack();
    public abstract void defend();
    public abstract void useAbility();
    public abstract void addAbility();
    public abstract void applyElementalBoost();

    // Getters
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getMana() {
        return mana;
    }

    public int getAttackMin() {
        return attackMin;
    }

    public int getDefenseMin() {
        return defenseMin;
    }

    public int getAttackMax() {
        return attackMax;
    }

    public int getDefenseMax() {
        return defenseMax;
    }

    public int getMovements() {
        return movements;
    }

    public Element getElement() {
        return element;
    }

    // Setters
    public void setLevel(int level) {
        this.level = level;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }
}
