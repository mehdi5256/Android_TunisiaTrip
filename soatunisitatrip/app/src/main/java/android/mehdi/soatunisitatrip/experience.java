package android.mehdi.soatunisitatrip;



public class experience {


    private int id;
    private String nom;
    private String email;

    private String upload_date;
    private String nom_image;



    private String image_profil;
    private String description;


    public String getImage_profil() {
        return image_profil;
    }

    public void setImage_profil(String image_profil) {
        this.image_profil = image_profil;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getDescription() {
        return description;
    }

    public String getNom_image() {
        return nom_image;
    }

    public void setNom_image(String nom_image) {
        this.nom_image = nom_image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public experience() {
    }

    public experience(String nom, String upload_date, String description) {
        this.nom = nom;
        this.upload_date = upload_date;
        this.description = description;
    }

    public experience(String nom, String upload_date, String nom_image, String description) {
        this.nom = nom;
        this.upload_date = upload_date;
        this.nom_image = nom_image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
