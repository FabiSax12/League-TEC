package models.skills;

import models.*;
import models.Character;

public class BuffSkill extends ASkill {
    private final String statToBoost;
    private final double boostFactor;

    public BuffSkill(String name, String statToBoost, double boostFactor, int manaCost, Element element) {
        super(name, manaCost, element);
        this.statToBoost = statToBoost;
        this.boostFactor = boostFactor;
    }

    @Override
    public void use(Character user, Entity target) {
        if (target instanceof Tower) {
            System.out.println("No se puede aplicar un buff a una torre");
        }
        if (target instanceof Character) {
            ((Character) target).statBuff(statToBoost, boostFactor, 1);
        }
    }
}
