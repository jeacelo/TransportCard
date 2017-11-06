package es.devjacl.transportcard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeacelo on 09/10/2017.
 */

public class RecordDB extends SQLiteOpenHelper {

    Context contexto;
    public RecordDB(Context contexto) {
        super(contexto, "lugares", null, 1);
        this.contexto = contexto;
    }
    @Override public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE record ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "tipo INTEGER, " +
                "fecha BIGINT, " +
                "vehiculo INTEGER, " +
                "recarga REAL, " +
                "num INTEGER)");
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                    int newVersion) {
    }
}
