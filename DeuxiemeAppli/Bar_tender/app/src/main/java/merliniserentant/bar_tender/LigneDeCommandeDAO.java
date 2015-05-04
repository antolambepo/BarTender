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

    }
