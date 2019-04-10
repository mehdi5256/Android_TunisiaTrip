package android.mehdi.soatunisitatrip;

public class UserProfil {

    private String nom;
    private String email;
    private String upload_date;
    private String image;
    private String photoprofil;
    private String description;

    public String getPhotoprofil() {
        return photoprofil;
    }

    public void setPhotoprofil(String photoprofil) {
        this.photoprofil = photoprofil;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserProfil() {
    }
}
