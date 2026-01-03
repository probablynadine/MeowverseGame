package model;

public class Boss {

    private final String nom;
    private int vie;
    private final int attaque;

    public Boss(String nom, int vie, int attaque) {
        this.nom = nom;
        this.vie = vie;
        this.attaque = attaque;
    }

    public String getNom() {
        return nom;
    }

    public int getVie() {
        return vie;
    }

    public int getAttaque() {
        return attaque;
    }

    public void attaquer(Chat chat) {
        chat.subirDegats(attaque);
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
