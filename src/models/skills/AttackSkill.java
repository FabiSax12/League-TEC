package models.skills;

import models.Character;
import models.ASkill;
import models.Element;

public class AttackSkill extends ASkill {
    private final int damage;

    public AttackSkill(String name, int damage, int manaCost, Element element) {
        super(name, manaCost, element);
        this.damage = damage;
    }

    @Override
    public void use(Character user, Character target) {}
}
