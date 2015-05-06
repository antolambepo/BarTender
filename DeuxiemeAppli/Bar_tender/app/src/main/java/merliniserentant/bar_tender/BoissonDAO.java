package merliniserentant.bar_tender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import merliniserentant.bar_tender.MySQLite;

import java.util.ArrayList;

/**
 * Created by merliniserentant on 30/04/15.
 */
public class BoissonDAO {

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
    private static final String COL_DESCRIPTION = "DESCBOISSON";
    private static final int NUM_COL_DESCRIPTION = 2;

    private static final String TABLE_TAG = "TAG";

    private static final String COL_TAG= "TAG";
    private static final int NUM_COL_TAG = 1;


    private static final String TABLE_LANGUE = "LANGUE";
    private static final String COL_LANGAGE = "LANGAGE";
    private static final int NUM_COL_LANGUE = 0;
    private static final int NUM_COL_IDLANGUE = 1;
    private static final int NUM_COL_NUMBOISSONLANGUE = 2;

    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public BoissonDAO(Context context)
    {
        maBaseSQLite = new MySQLite(context);
    }
    public String getLangue(){return maBaseSQLite.getLangue();}
    public void setLangue(String Langue){maBaseSQLite.setLangue(Langue);}


    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();


    }
    public boolean test(){
        return (db==null);
    }
    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }
    public ArrayList<Boisson> getBoissonwithTag(String Tag){
        Cursor cccc=db.query(TABLE_TAG, new String[] {COL_ID,COL_TAG}, COL_TAG+" LIKE \"" + Tag +"\"", null, null, null, null);
        ArrayList<Boisson> list = new ArrayList<Boisson>();
        if(cccc.getCount()==0){return null;}
        cccc.moveToFirst();
        int i;
        for(i=0;i<cccc.getCount();i++){
            Boisson boisson = new Boisson();
            Cursor c = db.query(TABLE_IDs, new String[] {COL_ID, COL_NOMBOISSON,COL_DESCRIPTION}, COL_ID + " LIKE \"" + cccc.getString(NUM_COL_ID) +"\"", null, null, null, null);
            if(c.getCount() == 0){return null;}
            c.moveToFirst();
            boisson.setDescription(c.getString(NUM_COL_DESCRIPTION));
            boisson.setNom(c.getString(NUM_COL_NOMBOISSON));
            Cursor cc = db.query(TABLE_LANGUE, new String[] {COL_LANGAGE, COL_ID,COL_NUMBOISSON}, COL_ID + " LIKE \""+ c.getString(NUM_COL_ID) +"\" AND " + COL_LANGAGE+" LIKE \""+maBaseSQLite.getLangue() + "\"", null, null, null, null);
            if(cc.getCount()!=0) { // Ca devrait pas arriver! Sinon c'est que la bdd est mal remplie!
                //Info disponible dans la table BOISSON
                cc.moveToFirst();
                boisson.setNumboisson(cc.getInt(NUM_COL_NUMBOISSONLANGUE));
                Cursor ccc = db.query(TABLE_BOISSON, new String[] {COL_NUMBOISSON, COL_STOCK,COL_STOCKMAX, COL_LOGOTYPE,COL_SEUIL,COL_PRIX}, COL_NUMBOISSON + " LIKE \"" + boisson.getNumboisson()+"\"", null, null, null, null);
                if(ccc.getCount()!=0) {
                    ccc.moveToFirst();
                    boisson.setStock(ccc.getInt(NUM_COL_STOCK));
                    boisson.setStockmax(ccc.getInt(NUM_COL_STOCKMAX));
                    boisson.setSeuil(ccc.getInt(NUM_COL_SEUIL));
                    boisson.setPrix(ccc.getDouble(NUM_COL_PRIX));
                    boisson.setLogotype(ccc.getString(NUM_COL_LOGOTYPE));
                    list.add(boisson);
                }
            }
            cccc.moveToNext();

        }


        return list;
    }
    public Boisson getBoissonwithNumboisson(int Numboisson){

        //Cursor c = db.query(TABLE_IDs, new String[] {COL_ID, COL_NOMBOISSON,COL_DESCRIPTION, COL_TAG}, COL_ID + " LIKE \"" + ID +"\"", null, null, null, null);
        Cursor c = db.query(TABLE_BOISSON, new String[] {COL_NUMBOISSON, COL_STOCK,COL_STOCKMAX, COL_LOGOTYPE,COL_SEUIL,COL_PRIX}, COL_NUMBOISSON + " LIKE \"" + Numboisson+"\"", null, null, null, null);
        //pas oublier COL_TYPE au dessus

        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un utilisateur
        Boisson boisson = new Boisson();
        //On peut deja remplir quelque information
        boisson.setNumboisson(c.getInt(NUM_COL_NUMBOISSON));
        System.out.println(c.getInt(NUM_COL_NUMBOISSON));
        boisson.setStock(c.getInt(NUM_COL_STOCK));
        boisson.setStockmax(c.getInt(NUM_COL_STOCKMAX));
        boisson.setSeuil(c.getInt(NUM_COL_SEUIL));
        boisson.setPrix(c.getDouble(NUM_COL_PRIX));
        boisson.setLogotype(c.getString(NUM_COL_LOGOTYPE));

        //il faut charger le reste des données

        System.out.println("Voila ce qui est mis" + c.getInt(NUM_COL_NUMBOISSON) );

        Cursor cc = db.query(TABLE_LANGUE, new String[] {COL_LANGAGE, COL_ID,COL_NUMBOISSON}, COL_LANGAGE + " LIKE \"" + maBaseSQLite.getLangue() +"\" AND " + COL_NUMBOISSON + " LIKE \""+ boisson.getNumboisson() +"\"", null, null, null, null);

        if(cc.getCount() ==0){


        return null;}

        cc.moveToFirst();
        System.out.println(cc.getString(NUM_COL_IDLANGUE));
        Cursor ccc = db.query(TABLE_IDs, new String[] {COL_ID, COL_NOMBOISSON,COL_DESCRIPTION}, COL_ID + " LIKE \"" + cc.getString(NUM_COL_IDLANGUE) +"\"", null, null, null, null);
        if(ccc.getCount() ==0){

            return null;
        }

        ccc.moveToFirst();
        System.out.println(ccc.getString(NUM_COL_NOMBOISSON));
        boisson.setDescription(ccc.getString(NUM_COL_DESCRIPTION));

        boisson.setNom(ccc.getString(NUM_COL_NOMBOISSON));
        return boisson;
    }

    public int[] underSeuil(){

        Cursor c = db.query(TABLE_BOISSON, new String[]{COL_NUMBOISSON}, COL_STOCK+"<"+COL_SEUIL, null, null, null, null );
        int count = c.getCount();
        if (count==0)
            return null;
        int[] boisson = new int[count];
        c.moveToFirst();

        for(int i=0; i<count-1; i++) {
            boisson[i]=c.getInt(NUM_COL_NUMBOISSON);
            c.moveToNext();
        }
        boisson[count-1]=c.getInt(NUM_COL_NUMBOISSON);

        c.close();

        return boisson;
    }

    public int[] getNum() {

        Cursor c = db.query(TABLE_BOISSON, new String [] {COL_NUMBOISSON, }, null, null, null, null, null);
        int count = c.getCount();
        if(count==0)
            return null;

        int [] boisson = new int[count];
        c.moveToFirst();

        for(int i=0; i<count-1; i++) {
            boisson[i]= c.getInt(i);
            c.moveToNext();
        }
        return boisson;
    }

    public ArrayList <Boisson> getCarte(int[]numBoisson) { //Surement aussi valable pour l'inventaire.
        ArrayList<Boisson> drink = new ArrayList<Boisson>();

        for(int i =0; i<numBoisson.length; i++ ) {
            Boisson bebida = getBoissonwithNumboisson(numBoisson[i]);
            drink.add(bebida);
        }
        return drink;
    }
    
    // retrouver une boisson avec le nom
    public Boisson getBoissonwithName (String NomBoisson){
        Cursor id = db.query(TABLE_IDs, new String[]{COL_ID, COL_NOMBOISSON, COL_DESCRIPTION}, COL_NOMBOISSON + " LIKE \"" + NomBoisson + "\"", null, null, null, null);
        Cursor lang = db.query(TABLE_LANGUE, new String[]{COL_LANGAGE, COL_ID, COL_NUMBOISSON}, COL_ID + "LIKE\" " + id.getString(NUM_COL_ID), null, null, null, null);
        return getBoissonwithNumboisson(lang.getInt(NUM_COL_NUMBOISSONLANGUE));
    }

    // reprend les boissons de stock > seuil
    public Boisson[] aboveSeuil() {

        Cursor c = db.query(TABLE_BOISSON, new String[]{COL_NUMBOISSON}, COL_STOCK + ">" + COL_SEUIL, null, null, null, null);
        int count = c.getCount();
        if (count == 0)
            return null;
        Boisson[] boisson = new Boisson[count];
        c.moveToFirst();

        for (int i = 0; i < count - 1; i++) {
            boisson[i] = getBoissonwithNumboisson(c.getInt(NUM_COL_NUMBOISSON));
            c.moveToNext();
        }
        boisson[count - 1] = getBoissonwithNumboisson(c.getInt(NUM_COL_NUMBOISSON));

        c.close();

        return boisson;
    }

    public boolean create(String name, String description, String logotype, double price, int stock, int stockmax, int seuil) {

        // Définition des valeurs pour le nouvel élément dans la table BOISSON
        //A changer!!!
        ContentValues cv = new ContentValues();
        cv.put(COL_NOMBOISSON,name);
        cv.put(COL_DESCRIPTION,description);
        cv.put(COL_LOGOTYPE, logotype);
        cv.put(COL_PRIX, price);
        cv.put(COL_STOCK, stock);
        cv.put(COL_STOCKMAX,stockmax);
        cv.put(COL_SEUIL, seuil);



        // Ajout à la base de données (table songs).
        int ci_id = (int) db.insert(TABLE_BOISSON, null, cv);

        if (ci_id == -1) {
            return false; // En cas d'erreur d'ajout, on retourne false directement.
        }
        cv.clear();

        // Définition des valeurs pour le nouvel élément dans la table "owns".
       // cv.put(DB_COL_ID, ci_id);
        //cv.put(DB_COL_UID, User.getConnectedUser().getId());
        //cv.put(DB_COL_RATING, rating);

        //int result = (int) db.insert(DB_TABLE_OWNS, null, cv);

        //if (result == -1) {
            // En cas d'erreur dans l'ajout de la deuxième table, il faut supprimer la ligne qu'on
            // vient d'ajouter dans la première table pour ne pas qu'il y ait un élément qui n'est
            // dans la collection de personne.
          //  db.delete(DB_TABLE_S, DB_COL_ID + " = ?", new String[]{String.valueOf(ci_id)});
           // return false;
        //}
        return true;
    }


}
