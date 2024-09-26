package models.abilities;

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
    public void use(Character user, Character target) {
        if (user.getMana() < manaCost) {
            System.out.println(user.getName() + " no tiene suficiente maná para usar " + name);
            return;
        }

        user.setMana(user.getMana() - manaCost);
        int totalDamage = damage;

        // Aumento del 10% si el elemento de la habilidad coincide con el del personaje
        if (user.getElement().equals(element)) {
            totalDamage *= 1.10;
        }

        target.setHealth(target.getHealth() - totalDamage);
        System.out.println(user.getName() + " usa " + name + " contra " + target.getName() +
                " infligiendo " + totalDamage + " puntos de daño.");
    }
}
