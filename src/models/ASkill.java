package models;

public abstract class ASkill {
    protected String name;
    protected int manaCost;
    protected Element element; // Fuego, Agua, Tierra, Aire

    public ASkill(String name, int manaCost, Element element) {
        this.name = name;
        this.manaCost = manaCost;
        this.element = element;
    }

    // Método abstracto que se implementará en cada tipo de habilidad
    public abstract void use(Character user, Character target);

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
