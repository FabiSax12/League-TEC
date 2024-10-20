package models.skills;

import models.Character;
import models.ASkill;
import models.Element;
import models.Entity;
import org.json.JSONObject;

public class AttackSkill extends ASkill {
    private final int damage;

    public AttackSkill(String name, int damage, int manaCost, Element element) {
        super(name, manaCost, element);
        this.damage = damage;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonSkill = super.toJson();
        jsonSkill.put("type", "attack");
        jsonSkill.put("damage", damage);
        return jsonSkill;
    }

    @Override
    public void use(Character user, Entity target) {
        System.out.println("Usando " + getName() + " de " + user.getName() + " a " + target.toString());
        target.takeDamage(user.getDamage() + this.damage);
    }

    public int getDamage() {
        return damage;
    }
}
