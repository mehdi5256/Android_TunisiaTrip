package android.mehdi.soatunisitatrip.SQLite;


import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns{

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_ATTRACTIONID = "attraction_id";
        public static final String COLUMN_NOM = "attraction_name";
        public static final String COLUMN_IMAGE = "attraction_image";
        public static final String COLUMN_DESCRIPTION = "attraction_description";
        public static final String COLUMN_SITEWEB = "attraction_siteweb";
        public static final String COLUMN_EMAIL = "attraction_email";
        public static final String COLUMN_TELEPHONE = "attraction_telephone";
        public static final String COLUMN_ADRESSE = "attraction_adresse";
    }
}
