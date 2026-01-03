package model;

public enum TypeChat {

    GINGER("Ginger", "ginger.png", 12, 80, false),
    NOVA("Nova", "nova.png", 15, 70, false),
    FLORA("Flora", "flora.png", 10, 90, true),
    COOKIE("Cookie", "cookie.png", 8, 120, false);

    private final String nom;
    private final String image;
    private final int attaque;
    private final int vieMax;
    private final boolean peutSoigner;

    TypeChat(String nom, String image, int attaque, int vieMax, boolean peutSoigner) {
        this.nom = nom;
        this.image = image;
        this.attaque = attaque;
        this.vieMax = vieMax;
        this.peutSoigner = peutSoigner;
    }

    public String getNom() {
        return nom;
    }

    public String getImage() {
        return image;
    }

    public int getAttaque() {
        return attaque;
    }

    public int getVieMax() {
        return vieMax;
    }

    public boolean peutSoigner() {
        return peutSoigner;
    }
}
