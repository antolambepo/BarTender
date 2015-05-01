package com.example.roselien.bartender;

/**
 * Created by Roselien on 01/05/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class CommanderDAO {

    private static final String TABLE_BOISSON = "BOISSON";
    private static final String COL_NUMBOISSON = "NUMBOISSON";
    private static final int NUM_COL_NUMBOISSON = 0;
    private static final String COL_STOCK = "STOCK";
    private static final int NUM_COL_STOCK = 1;
    private static final String COL_STOCKMAX = "STOCKMAX";
    private static final int NUM_COL_STOCKMAX = 2;
    private static final String COL_LOGOTYPE = "LOGOTYPE";
    private static final int NUM_COL_LOGOTYPE = 3;
    private static final String COL_SEUIL = "SEUIL";
    private static final int NUM_COL_SEUIL = 4;
    private static final String COL_PRIX = "PRIX";
    private static final int NUM_COL_PRIX = 5;

    private static final String TABLE_IDs = "IDs";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NOMBOISSON = "NOMBOISSON";
    private static final int NUM_COL_NOMBOISSON = 1;
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final int NUM_COL_DESCRIPTION = 2;
    private static final String COL_TAG = "TAG";
    private static final int NUM_COL_TAG = 3;

    private static final String TABLE_LANGUE = "LANGUE";
    private static final String COL_LANGAGE = "LANGAGE";
    private static final int NUM_COL_LANGUE = 0;
    private static final int NUM_COL_IDLANGUE = 1;
    private static final int NUM_COL_NOMBOISSONLANGUE = 2;

    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public CommanderDAO(Context context) {
        maBaseSQLite = new MySQLite(context);
    }

    public String getLangue() {
        return maBaseSQLite.getLangue();
    }

    public void setLangue(String Langue) {
        maBaseSQLite.setLangue(Langue);
    }


    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    // retrouver une boisson avec le nom
    public Boisson getBoissonwithName (String NomBoisson){
        Cursor id = db.query(TABLE_IDs, new String[]{COL_ID, COL_NOMBOISSON, COL_DESCRIPTION, COL_TAG}, COL_NOMBOISSON + " LIKE \"" + NomBoisson + "\"", null, null, null, null);
        Cursor lang = db.query(TABLE_LANGUE, new String[]{COL_LANGAGE, COL_ID, COL_NUMBOISSON}, COL_ID + "LIKE\" " + id.getInt(NUM_COL_ID), null, null, null, null);
        BoissonDAO b = new BoissonDAO(null);
        return b.getBoissonwithNumboisson(lang.getInt(NUM_COL_NOMBOISSONLANGUE));
    }


    // reprend les boissons de stock non nul
    public List<Integer> getNumBoissonInStock() {
        Cursor c = db.rawQuery("SELECT * FROM" + TABLE_BOISSON, null);
        List <Integer> data = null;
        if (c.moveToFirst()) {
            do {
            if (c.getInt(NUM_COL_STOCK) > 0){
                data.add(c.getInt(NUM_COL_NUMBOISSON));
            }
            }
            while (c.moveToNext());
        }
        db.close();
        return data;
    }
}
