package android.mehdi.soatunisitatrip;

public class Ville {
    private  int id ;
    private String nomville;
    private String image;
    private String latitude;
    private String  longitude;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomville() {
        return nomville;
    }

    public void setNomville(String nomville) {
        this.nomville = nomville;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ville(String nomville, String image) {
        this.nomville = nomville;
        this.image = image;
    }

    public Ville(int id, String nomville, String image) {
        this.id = id;
        this.nomville = nomville;
        this.image = image;
    }

    public Ville() {

    }


}

