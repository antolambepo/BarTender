package com.example.roselien.myapplication;

/**
 * Created by Roselien on 27/04/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LigneCommandeDAO {

    private static final String TABLE_LIGNECOM = "LIGNECOM";
    private static final String COL_NUM = "NumCom";
    private static final int NUM_COL_NUM = 0;
    private static final String COL_BOISSON = "Boisson";
    private static final int NUM_COL_BOISSON = 1;
    private static final String COL_QTE = "Quantité";
    private static final int NUM_COL_QTE = 2;
    private static final String COL_TABLE = "Table";
    private static final int NUM_COL_TABLE = 3;
    private static final String COL_LOGIN = "Login";
    private static final int NUM_COL_LOGIN = 4;

    private SQLiteDatabase bdd;

    private MySQLiteHelper mySQLiteHelper;

    public LigneCommandeDAO(Context context){
        //On crée la BDD et sa table
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = mySQLiteHelper.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }



    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertLigneCommande (LigneCommande ligneCommande){

        // insère une ligne de commande dans la base de donnée

        ContentValues values = new ContentValues();

        values.put(COL_BOISSON, ligneCommande.getBoisson());
        values.put(COL_QTE, ligneCommande.getQuantité());
        values.put(COL_TABLE, ligneCommande.getBoisson());
        values.put(COL_NUM, ligneCommande.getNum());
        values.put(COL_LOGIN, ligneCommande.getLogin());
        return bdd.insert(TABLE_LIGNECOM, null, values);
    }

    public int removeLigneCommande(int num) {
        // Suppression d'une ligne de commande de la BDD grâce au numéro de ligne de commande
        return bdd.delete(TABLE_LIGNECOM, COL_NUM + "=" + num, null);
    }

    public LigneCommande getLigneCommande (int num){
        // Récupère une ligne de commande avec le numéro de ligne de commande
        Cursor c = bdd.query(TABLE_LIGNECOM, new String[] {COL_NUM, COL_BOISSON, COL_QTE, COL_TABLE, COL_LOGIN}, COL_NUM + "LIKE\"" + num + "\"", null, null, null, null);
        return cusorToLigneCommande(c);
    }

    public LigneCommande cursorToLigneCommande (Cursor c){
        // Convertit un cursor en ligne de commande
        
        if (c.getCount() == 0) // Si le cursor est vide
            return null;
        
        // On se place sur le premier élément
        c.moveToFirst();
        // On crée la ligne de commande
        LigneCommande ligneCommande = new LigneCommande(0, null, null, 0, 0);
        ligneCommande.setNum(c.getInt(NUM_COL_NUM));
        ligneCommande.setBoisson(c.getString(NUM_COL_BOISSON));
        ligneCommande.setQuantité(c.getInt(NUM_COL_QTE));
        ligneCommande.setTable(c.getInt(NUM_COL_TABLE));
        ligneCommande.setLogin(c.getString(NUM_COL_LOGIN));
        
        return ligneCommande;
        
    }
}
