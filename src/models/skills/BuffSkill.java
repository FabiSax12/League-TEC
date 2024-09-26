package models.skills;

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
    public void use(Character user, Character target) {}
}
