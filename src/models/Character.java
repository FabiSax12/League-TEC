package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Represents a character in the game with various attributes such as health, mana, damage,
 * defense, movements, element, and skills.
 *
 * <p>The character can perform actions such as moving, using skills, and leveling up.
 * Skills are stored in a list and the character has an elemental affinity, which can be boosted.</p>
 */
public class Character extends Entity {
    private String name;
    private String spritePath;
    private int level;
    private int maxMana;
    private int mana;
    private int movements;
    private boolean dead;
    private Element element;
    private final ArrayList<ASkill> skills;

    /**
     * Constructs a new Character with the specified attributes.
     *
     * @param name the name of the character.
     * @param health the initial health of the character.
     * @param mana the initial mana of the character.
     * @param damage the damage value of the character.
     * @param defense the defense value of the character.
     * @param movements the number of movements the character can make.
     * @param element the elemental type of the character.
     * @param spritePath the path to the character's sprite image.
     */
    public Character(String name, float health, int mana, int damage, int defense, int movements, Element element, String spritePath) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.damage = damage;
        this.element = element;
        this.defense = defense;
        this.movements = movements;
        this.level = 1;
        this.skills = new ArrayList<>();
        this.spritePath = spritePath;
    }

    /**
     * Increases the character's mana regeneration by 25%.
     */
    public void regenerateMana() {
        int augment = (int) (this.maxMana * 0.25);

        if(this.mana + augment > this.maxMana){
            this.mana = this.maxMana;
        } else {
            this.mana += augment;
        }
    }

    /**
     * Applies a buff to one of the character's stats (damage, defense, or movements) for a certain number of rounds.
     *
     * @param stat the stat to be boosted (e.g., "damage", "defense", "movements").
     * @param boost the multiplier to apply to the stat.
     * @param rounds the number of rounds the buff will last.
     */
    public void statBuff(String stat, double boost, int rounds) {
        switch (stat) {
            case "attack":
                this.setDamage((int) (this.getDamage() + this.getDamage() * boost));
                break;
            case "defense":
                this.setDefense((int) (this.getDefense() + this.getDefense() * boost));
                break;
            case "movements":
                this.setMovements((int) (this.getMovements() + this.getMovements() * boost));
                break;
            case "health":
                this.setHealth((int) (this.getHealth() + this.getHealth() * boost));
                break;
        }
    }

    /**
     * Uses a skill from the character's skill list.
     *
     * @param skill the index of the skill in the list.
     * @param target the entity being targeted by the skill.
     */
    public void useSkill(ASkill skill, Entity target) {
//        ASkill skill = skills.get(skillIndex);
        skill.use(this, target);
        this.mana = this.mana - skill.getManaCost();
    }

    /**
     * Applies an elemental boost to the character based on the arena's element.
     * This method should contain the logic for boosting the character's stats
     * based on their elemental affinity.
     */
    public void applyElementalBoost() {
        this.damage += (int) (this.damage * 0.15);
        this.defense += (int) (this.defense * 0.15);
        this.maxHealth += (int) (this.maxHealth * 0.15);
        this.health = this.maxHealth;
    }

    /**
     * Adds a new skill to the character's skill list.
     *
     * @param skill the skill to be added.
     */
    public void addSkill(ASkill skill) {
        this.skills.add(skill);
    }

    /**
     * Levels up the character, increasing health, mana, defense, and damage by 25%.
     */
    public void levelUp() {
        this.level++;
        this.maxHealth += (int) (this.maxHealth * 0.25);
        this.maxMana += (int) (this.maxMana * 0.25);
        this.defense += (int) (this.defense * 0.25);
        this.damage += (int) (this.damage * 0.25);
    }

    /**
     * Returns the character's current skill list in JSON format.
     *
     * @return a {@code JSONArray} containing the character's skills.
     */
    private JSONArray getSkillsAsJson() {
        JSONArray jsonSkills = new JSONArray();
        for (ASkill skill : skills) {
            jsonSkills.put(skill.toJson());
        }
        return jsonSkills;
    }

    /**
     * Converts the character's attributes to a JSON representation.
     *
     * @return a {@code JSONObject} representing the character's attributes and skills.
     */
    public JSONObject toJson() {
        JSONObject jsonCharacter = new JSONObject();
        jsonCharacter.put("name", this.getName());
        jsonCharacter.put("health", this.getHealth());
        jsonCharacter.put("mana", this.getMana());
        jsonCharacter.put("attack", this.getDamage());
        jsonCharacter.put("element", this.getElement().toString());
        jsonCharacter.put("sprite", this.getSpritePath());
        jsonCharacter.put("defense", this.getDefense());
        jsonCharacter.put("movements", this.getMovements());
        jsonCharacter.put("skills", this.getSkillsAsJson());
        return jsonCharacter;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHealth() {
        return health;
    }
    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public ASkill[] getSkills() {
        return skills.toArray(new ASkill[0]);
    }

    public void clearSkills() {
        this.skills.clear();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}