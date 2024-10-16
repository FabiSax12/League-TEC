package models;

import org.json.JSONObject;

/**
 * Represents an abstract skill that can be used by a character in the game.
 *
 * <p>An {@code ASkill} defines the basic attributes of a skill such as its name, mana cost,
 * and element. Subclasses must implement the {@code use()} method to define the behavior
 * when the skill is used.</p>
 */
public abstract class ASkill {
    protected String name;
    protected int manaCost;
    protected Element element; // Fuego, Agua, Tierra, Aire

    /**
     * Constructs an ASkill with the specified name, mana cost, and element.
     *
     * @param name the name of the skill.
     * @param manaCost the mana cost required to use the skill.
     * @param element the element type of the skill (e.g., FIRE, WATER, GROUND, AIR).
     */
    public ASkill(String name, int manaCost, Element element) {
        this.name = name;
        this.manaCost = manaCost;
        this.element = element;
    }

    /**
     * Uses the skill, applying its effects from the user to the target.
     *
     * <p>This method must be implemented by subclasses to define the specific behavior
     * when the skill is used.</p>
     *
     * @param user the character using the skill.
     * @param target the entity being targeted by the skill.
     */
    public abstract void use(Character user, Entity target);

    /**
     * Converts the skill into a JSON object representation.
     *
     * @return a {@code JSONObject} representing the skill's name, mana cost, and element.
     */
    public JSONObject toJson() {
        JSONObject jsonSkill = new JSONObject();
        jsonSkill.put("name", this.name);
        jsonSkill.put("mana", this.manaCost);
        jsonSkill.put("element", this.element.toString());
        return jsonSkill;
    }

    /**
     * Returns the name of the skill.
     *
     * @return the name of the skill.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the mana cost of the skill.
     *
     * @return the mana cost of the skill.
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * Returns the element of the skill.
     *
     * @return the element of the skill.
     */
    public Element getElement() {
        return element;
    }
}
