package models.skills;

import models.ASkill;
import models.Character;
import models.Element;
import models.Entity;

public class HealSkill extends ASkill {
    private final int healAmount;

    public HealSkill(String name, int healAmount, int manaCost, Element element) {
        super(name, manaCost, element);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Character user, Entity target) {
        target.setHealth(target.getHealth() + healAmount);
    }
}
