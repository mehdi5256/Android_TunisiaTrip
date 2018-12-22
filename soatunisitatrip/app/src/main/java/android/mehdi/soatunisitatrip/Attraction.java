package android.mehdi.soatunisitatrip;


public class Attraction {
    private int id ;
    private String nomAttraction;
    private String image;
    private int idville;
    private int id_type_attraction;
    private String description;
    private String siteweb;
    private String mail;
    private String telephone;
    private String adresse;
    private float latitude;
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomAttraction() {
        return nomAttraction;
    }

    public void setNomAttraction(String nomAttraction) {
        this.nomAttraction = nomAttraction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdville() {
        return idville;
    }

    public void setIdville(int idville) {
        this.idville = idville;
    }

    public int getId_type_attraction() {
        return id_type_attraction;
    }

    public void setId_type_attraction(int id_type_attraction) {
        this.id_type_attraction = id_type_attraction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Attraction(String nomAttraction, String image, String adresse) {
        this.nomAttraction = nomAttraction;
        this.image = image;
        this.adresse = adresse;
    }

    public Attraction() {
    }

    public Attraction(String nomAttraction, String image, String description, String siteweb, String mail, String telephone, String adresse) {
        this.nomAttraction = nomAttraction;
        this.image = image;
        this.description = description;
        this.siteweb = siteweb;
        this.mail = mail;
        this.telephone = telephone;
        this.adresse = adresse;
    }
}
