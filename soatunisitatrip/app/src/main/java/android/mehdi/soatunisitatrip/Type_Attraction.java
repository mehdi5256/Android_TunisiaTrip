package android.mehdi.soatunisitatrip;

public class Type_Attraction {
    private  int id ;
    private String nom;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Type_Attraction(int id, String nom, String image) {
        this.id = id;
        this.nom = nom;
        this.image = image;
    }

    public Type_Attraction(String nom, String image) {
        this.nom = nom;
        this.image = image;
    }

    public Type_Attraction()
    {

    }
}
