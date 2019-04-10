package android.mehdi.soatunisitatrip.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.mehdi.soatunisitatrip.Attraction;

import java.util.ArrayList;
import java.util.List;


public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavoriteEntry.COLUMN_ATTRACTIONID + " INTEGER, " +
                FavoriteContract.FavoriteEntry.COLUMN_NOM + " TEXT , " +
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE+ " TEXT , " +
                FavoriteContract.FavoriteEntry.COLUMN_DESCRIPTION + " TEXT ," +
                FavoriteContract.FavoriteEntry.COLUMN_SITEWEB + " TEXT ," +
                FavoriteContract.FavoriteEntry.COLUMN_EMAIL + " TEXT ," +
                FavoriteContract.FavoriteEntry.COLUMN_TELEPHONE + " INTEGER," +
                FavoriteContract.FavoriteEntry.COLUMN_ADRESSE + " TEXT " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(Attraction attraction){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_ATTRACTIONID,  attraction.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_NOM, attraction.getNomAttraction());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE, attraction.getImage());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_DESCRIPTION, attraction.getDescription());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_SITEWEB, attraction.getSiteweb());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_EMAIL, attraction.getMail());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TELEPHONE , attraction.getTelephone());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_ADRESSE, attraction.getAdresse());





        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_ATTRACTIONID+ "=" + id, null);

    }

    public void uniqueFavorite ( ){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql ="delete from favorite where attraction_name not in (select min(id) from favorite group by attraction_id)";
        db.execSQL(sql);
    }



    public List<Attraction> getAllFavorite(){
        String[] columns = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_ATTRACTIONID,
                FavoriteContract.FavoriteEntry.COLUMN_NOM,
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE,
                FavoriteContract.FavoriteEntry.COLUMN_DESCRIPTION,
                FavoriteContract.FavoriteEntry.COLUMN_SITEWEB,
                FavoriteContract.FavoriteEntry.COLUMN_EMAIL,
                FavoriteContract.FavoriteEntry.COLUMN_TELEPHONE,
                FavoriteContract.FavoriteEntry.COLUMN_ADRESSE,


        };
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<Attraction> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {

                Attraction attraction = new Attraction();

                attraction.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ATTRACTIONID))));
                attraction.setNomAttraction(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_NOM)));
                attraction.setImage(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_IMAGE)));
                attraction.setDescription(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_DESCRIPTION)));
                attraction.setSiteweb(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_SITEWEB)));
                attraction.setMail(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_EMAIL)));
                attraction.setTelephone(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TELEPHONE)));
                attraction.setAdresse(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ADRESSE)));
                int s = favoriteList.size();
                boolean verif = false;
                for (int i = 0 ; i <s  ; i ++){
                    if ( favoriteList.get(i).getId() == attraction.getId()) {

                        verif = true;

                    }
                }
                if (verif != true) {
                    favoriteList.add(attraction);
                }


            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

}
