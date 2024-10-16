package models;

import org.json.JSONObject;

public abstract class ASkill {
    protected String name;
    protected int manaCost;
    protected Element element; // Fuego, Agua, Tierra, Aire

    public ASkill(String name, int manaCost, Element element) {
        this.name = name;
        this.manaCost = manaCost;
        this.element = element;
    }

    public abstract void use(Character user, Entity target);

    public JSONObject toJson() {
        JSONObject jsonSkill = new JSONObject();
        jsonSkill.put("name", this.name);
        jsonSkill.put("mana", this.manaCost);
        jsonSkill.put("element", this.element.toString());
        return jsonSkill;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public Element getElement() {
        return element;
    }
}
