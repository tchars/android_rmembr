package br.com.tchars.rmembr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final String DATABASE_NAME = "rmmbr";
    private static final String TABELA_MEMORIAS = "memorias";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MEMORIAS = "CREATE TABLE IF NOT EXISTS memorias(id INTEGER PRIMARY KEY AUTOINCREMENT, urlImagem VARCHAR, titulo VARCHAR)";

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMORIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_MEMORIAS);
        onCreate(db);
    }
}
