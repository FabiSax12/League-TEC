package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Character extends Entity {
    private final String name;
    private final String spritePath;
    private int level;
    private int maxMana;
    private int mana;
    private int defense;
    private int movements;
    private boolean dead;
    private final Element element;
    private final ArrayList<ASkill> skills;

    public Character(String name, float health, int mana, int damage, Element element, String spritePath) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.damage = damage;
        this.element = element;
        this.level = 1;
        this.defense = 20;
        this.movements = 1;
        this.skills = new ArrayList<>();
        this.spritePath = spritePath;
    }

    public void move() {

    }

    public void regenerateMana() {
        this.mana += (int) (this.mana * 0.25);
    }

    public void statBuff(String stat, double boost, int rounds) {
        switch (stat) {
            case "damage":
                this.setDamage((int) (this.getDamage() + this.getDamage() * boost));
                break;
            case "defense":
                this.setDefense((int) (this.getDefense() + this.getDefense() * boost));
                break;
            case "movements":
                this.setMovements((int) (this.getMovements() + this.getMovements() * boost));
        }
    }

    public void useSkill(int skillIndex, Entity target) {
        ASkill skill = skills.get(skillIndex);
        skill.use(this, target);
        this.mana = this.mana - skill.getManaCost();
    }

    public void applyElementalBoost() {

    }

    public void addSkill(ASkill skill) {
        this.skills.add(skill);
    }

    public void levelUp() {
        this.level++;
        this.maxHealth += (int) (this.maxHealth * 0.25);
        this.maxMana += (int) (this.maxMana * 0.25);
        this.defense += (int) (this.defense * 0.25);
        this.damage += (int) (this.damage * 0.25);
    }

    public String getName() {
        return name;
    }

    public float getHealth() {
        return health;

    public String getSpritePath() {
        return spritePath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Element getElement() {
        return element;
    }

    public ASkill[] getSkills() {
        return skills.toArray(new ASkill[0]);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public JSONObject toJson() {
        JSONObject jsonCharacter = new JSONObject();
        jsonCharacter.put("name", this.getName());
        jsonCharacter.put("health", this.getHealth());
        jsonCharacter.put("mana", this.getMana());
        jsonCharacter.put("attack", this.getDamage());
        jsonCharacter.put("element", this.getElement().toString());
        jsonCharacter.put("level", this.getLevel());
        jsonCharacter.put("defense", this.getDefense());
        jsonCharacter.put("movements", this.getMovements());
        jsonCharacter.put("skills", this.getSkillsAsJson());
        return jsonCharacter;
    }

    private JSONArray getSkillsAsJson() {
        JSONArray jsonSkills = new JSONArray();
        for (ASkill skill : skills) {
            jsonSkills.put(skill.toJson());
        }
        return jsonSkills;
    }
}
