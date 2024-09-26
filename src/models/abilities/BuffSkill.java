package models.abilities;

import models.ASkill;
import models.Character;
import models.Element;

public class BuffSkill extends ASkill {
    private String statToBoost;
    private double boostFactor;

    public BuffSkill(String name, String statToBoost, double boostFactor, int manaCost, Element element) {
        super(name, manaCost, element);
        this.statToBoost = statToBoost;
        this.boostFactor = boostFactor;
    }

    @Override
    public void use(Character user, Character target) {
        if (user.getMana() < manaCost) {
            System.out.println(user.getName() + " no tiene suficiente maná para usar " + name);
            return;
        }

        user.setMana(user.getMana() - manaCost);

        switch (statToBoost.toLowerCase()) {
            case "ataque":
                target.setAttackMax((int) (target.getAttackMax() * boostFactor));
                System.out.println(user.getName() + " usa " + name + " para aumentar el ataque de " +
                        target.getName());
                break;
            case "vida":
                target.setHealth((int) (target.getHealth() * boostFactor));
                System.out.println(user.getName() + " usa " + name + " para aumentar la vida de " +
                        target.getName());
                break;
            default:
                System.out.println("Estadística no reconocida.");
                break;
        }
    }
}
