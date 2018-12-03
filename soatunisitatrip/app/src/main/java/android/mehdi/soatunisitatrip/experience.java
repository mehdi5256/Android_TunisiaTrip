package android.mehdi.soatunisitatrip;


import android.mehdi.soatunisitatrip.Model.User;

public class experience {

    /*private int id ;
    private String descrition;
    private String date_upload;
    private User id_user;
    private int id_ville;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getDate_upload() {
        return date_upload;
    }

    public void setDate_upload(String date_upload) {
        this.date_upload = date_upload;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public int getId_ville() {
        return id_ville;
    }

    public void setId_ville(int id_ville) {
        this.id_ville = id_ville;
    }

    public experience(String descrition, String date_upload) {
        this.descrition = descrition;
        this.date_upload = date_upload;
    }

    public experience()
    {

    }*/

    private String nom;
    private String upload_date;
    private String nom_image;
    private String description;

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
}
