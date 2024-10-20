package models.skills;

import models.ASkill;
import models.Character;
import models.Element;
import models.Entity;
import org.json.JSONObject;

public class HealSkill extends ASkill {
    private final int healAmount;

    public HealSkill(String name, int healAmount, int manaCost, Element element) {
        super(name, manaCost, element);
        this.healAmount = healAmount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonSkill = super.toJson();
        jsonSkill.put("type", "heal");
        jsonSkill.put("heal", healAmount);
        return jsonSkill;
    }

    @Override
    public void use(Character user, Entity target) {
        System.out.println("Usando " + getName() + " de " + user.getName() + " a " + target.toString());

        if (target.getHealth() + healAmount > target.getMaxHealth()) {
            target.setHealth(target.getMaxHealth());
        } else {
            target.setHealth(target.getHealth() + healAmount);
        }
    }

    public int getHealAmount() {
        return healAmount;
    }
}
