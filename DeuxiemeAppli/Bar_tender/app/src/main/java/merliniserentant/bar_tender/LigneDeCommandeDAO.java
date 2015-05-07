package merliniserentant.bar_tender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by merliniserentant on 29/04/15.
 */
public class LigneDeCommandeDAO {



        private static final String TABLE_LIGNEDECOMMANDE = "LIGNECOMMANDE";
        private static final String COL_NUMLIGNE = "NUMLIGNE";
        private static final int NUM_COL_NUMLIGNE = 0;
        private static final String COL_NUMTABLE = "NUMTABLE";
        private static final int NUM_COL_NUMTABLE = 1;
        private static final String COL_LOGINCODE = "LOGINCODE";
        private static final int NUM_COL_LOGINCODE = 2;
        private static final String COL_QUANTITE = "QUANTITE";
        private static final int NUM_COL_QUANTITE = 3;
        private static final String COL_NUMBOISSON = "NUMBOISSON";
        private static final int NUM_COL_NUMBOISSON = 4;

    public final static String TABLE_ADDITION = "TABLE_ADDITION";
    public final static String COL_NUMADDITION = "NUMADDITION";
    public final static int NUM_COL_NUMADDITION = 0;
    public final static String COL_NUMLIGNECOMMANDE = "NUMCOMMANDE";
    public final static int NUM_COL_NUMLIGNECOMMANDE = 1;
    public final static String COL_TYPEPAIEMENT = "TYPEPAIEMENT";
    public final static int NUM_COL_TYPEPAIEMENT = 2;

        private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
        private SQLiteDatabase db;

        // Constructeur
        public LigneDeCommandeDAO(Context context)
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

        public void close()
        {
            //on ferme l'accès à la BDD
            db.close();
        }
        public void insertLignedecommande(LigneDeCommande lignedecommande){

            ContentValues values = new ContentValues();

            values.put(COL_NUMLIGNE, lignedecommande.getNum());
            values.put(COL_NUMTABLE, lignedecommande.getTable());
            values.put(COL_LOGINCODE, lignedecommande.getLogin());
            values.put(COL_QUANTITE, lignedecommande.getQuantité());
            values.put(COL_NUMBOISSON, lignedecommande.getBoisson());
            //System.out.println(Commander.num+ " Verfier que bien incrementer et que recommence pas à 6 a chaque fois qu'on relance l'app!");
            //on insère l'objet dans la BDD via le ContentValues
            db.insert(TABLE_LIGNEDECOMMANDE,null,values);
        }

        public LigneDeCommande getLignewithnumboisson(int numboisson){
            Cursor c = db.query(TABLE_LIGNEDECOMMANDE, new String[] {COL_NUMLIGNE, COL_NUMTABLE,COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMBOISSON + " LIKE \"" + numboisson +"\"", null, null, null, null);
            //pas oublier COL_TYPE au dessus
            return cursorToLigne(c);
        }
     
     public LigneDeCommande getLignewithnum(int num){
        Cursor c = db.query(TABLE_LIGNEDECOMMANDE, new String[] {COL_NUMLIGNE, COL_NUMTABLE,COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMLIGNE + " LIKE \"" + num +"\"", null, null, null, null);
        //pas oublier COL_TYPE au dessus
        return cursorToLigne(c);
    }
        private LigneDeCommande cursorToLigne(Cursor c){
            //si aucun élément n'a été retourné dans la requête, on renvoie null
            if (c.getCount() == 0)
                return null;

            //Sinon on se place sur le premier élément
            c.moveToFirst();
            //On créé un utilisateur
            LigneDeCommande lignedecommande = new LigneDeCommande();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            lignedecommande.setBoisson(c.getInt(NUM_COL_NUMBOISSON));
            lignedecommande.setQuantité(c.getInt(NUM_COL_QUANTITE));
            lignedecommande.setLogin(c.getString(NUM_COL_LOGINCODE));
            lignedecommande.setTable(c.getInt(NUM_COL_NUMTABLE));
            lignedecommande.setNum(c.getInt(NUM_COL_NUMLIGNE));

            return lignedecommande;
        }
    // crée une additionClass à partir d'un cursor
    public AdditionClass cursorToAddition (Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //on créée un addition vide
        AdditionClass addition = new AdditionClass(0, 0, null);
        // on lui affecte les infos du cursor
        addition.setNumAddition(c.getInt(NUM_COL_NUMADDITION));
        addition.setNumLignedeCommande(c.getInt(NUM_COL_NUMLIGNECOMMANDE));
        addition.setTypePaiement(c.getString(NUM_COL_TYPEPAIEMENT));
        return addition;
    }
//A SUPRIMER NORMALEMENT
    //public void insertCommande (AdditionClass add){
        //ContentValues values = new ContentValues();

        //values.put(COL_NUMADDITION, add.getNumAddition());
        //values.put(COL_NUMLIGNECOMMANDE, add.getNumLignedeCommande());
        //values.put(COL_TYPEPAIEMENT, add.getTypePaiement());
        //db.insert(TABLE_ADDITION, null, values);
    //}

    //Retourne true si une ligne de commande a ce numéro de ligne là.
    private boolean isnumligneintable(int i){
        Cursor c = db.query(TABLE_LIGNEDECOMMANDE, new String[]{COL_NUMLIGNE, COL_NUMTABLE, COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMLIGNE + "=" + i , null, null, null, null);
        return !(c.getCount()==0);
    }
    public int nextnumligne(){
        int i =1;
        while(isnumligneintable(i)){
            i++;
        }
        return i;
    }
    // Regroupe toutes les commandes qui ont le même numéro de tables
    public int[] getNumLignedeCommandeWithTable(int table){
        Cursor c = db.query(TABLE_LIGNEDECOMMANDE, new String[]{COL_NUMLIGNE, COL_NUMTABLE, COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMTABLE + " LIKE \"" + table +"\"" , null, null, null, null);
        int count = c.getCount();
        if (count == 0){
            return null;
        }
        int[] ligneCommandes = new int[count];
        c.moveToFirst();
        for (int i = 0; i < count -1; i++){
            ligneCommandes[i] = c.getInt(NUM_COL_NUMLIGNE);
            c.moveToNext();
        }
        ligneCommandes[count-1] = c.getInt(NUM_COL_NUMLIGNE);
        return ligneCommandes;
    }

    // Vérifie si la table existe bien
    public boolean tableExist(int table){
        Cursor c = db.query(TABLE_LIGNEDECOMMANDE, new String[]{COL_NUMLIGNE, COL_NUMTABLE, COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMTABLE + "=" + table, null, null, null, null);
        int count = c.getCount();
        if (count == 0){
            return false;
        }
        else {
            return true;
        }
    }

}
