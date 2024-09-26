package models.abilities;

import models.ASkill;
import models.Character;
import models.Element;

public class HealSkill extends ASkill {
    private int healAmount;

    public HealSkill(String name, int healAmount, int manaCost, Element element) {
        super(name, manaCost, element);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Character user, Character target) {
        if (user.getMana() < manaCost) {
            System.out.println(user.getName() + " no tiene suficiente maná para usar " + name);
            return;
        }

        user.setMana(user.getMana() - manaCost);
        target.setHealth(target.getHealth() + healAmount);
        System.out.println(user.getName() + " usa " + name + " curando a " + target.getName() +
                " por " + healAmount + " puntos de vida.");
    }
}
