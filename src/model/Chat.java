package model;

public class Chat {

    private final TypeChat type;
    private int vie;
    private int potions;
    private int nourriture;

    public Chat(TypeChat type) {
        this.type = type;
        this.vie = type.getVieMax();
        this.potions = 0;
        this.nourriture = 0;
    }

    public TypeChat getType() {
        return type;
    }

    public int getVie() {
        return vie;
    }

    public int getVieMax() {
        return type.getVieMax();
    }

    public int getAttaque() {
        return type.getAttaque();
    }

    public boolean peutSoigner() {
        return type.peutSoigner();
    }

    public int getPotions() {
        return potions;
    }

    public int getNourriture() {
        return nourriture;
    }

    public void ajouterPotion() {
        potions++;
    }

    public void ajouterNourriture() {
        nourriture++;
    }

    public void attaquer(Boss boss) {
        boss.subirDegats(getAttaque());
    }

    public void soigner() {
        if (peutSoigner() && potions > 0) {
            vie += 30;
            if (vie > getVieMax()) {
                vie = getVieMax();
            }
            potions--;
        }
    }

    public void subirDegats(int degats) {
        vie -= degats;
        if (vie < 0) {
            vie = 0;
        }
    }

    public boolean estVivant() {
        return vie > 0;
    }
}
