package merliniserentant.bar_tender;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roselien on 03/05/15.
 */
public class AdditionDAO {

    public static int num = 1;
    public LigneDeCommandeDAO ldao;
    public BoissonDAO bdao;

    public final static String TABLE_COMMANDE = "TABLE_COMMANDE";
    public final static String COL_NUMCOMMANDE = "NUMCOMMANDe";
    public final static int NUM_COL_NUMCOMMANDE = 0;
    public final static String COL_NUMLIGNECOMMANDE = "NUMCOMMANDE";
    public final static int NUM_COL_NUMLIGNECOMMANDE = 1;
    public final static String COL_TYPEPAIEMENT = "TYPEPAIEMENT";
    public final static int NUM_COL_TYPEPAIEMENT = 2;

    private static final String TABLE_LIGNECOMMANDE = "LIGNECOMMANDE";
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
    public AdditionDAO(Context context) {
        maBaseSQLite = new MySQLite(context);
        ldao = new LigneDeCommandeDAO(context);
        bdao = new BoissonDAO(context);
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

    public boolean test() {
        return (db == null);
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
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
        addition.setNumAddition(c.getInt(NUM_COL_NUMCOMMANDE));
        addition.setNumLignedeCommande(c.getInt(NUM_COL_NUMLIGNE));
        addition.setTypePaiement(c.getString(NUM_COL_TYPEPAIEMENT));
        return addition;
    }
    // Vérifie si la table existe bien
    public boolean tableExist(int table){
        Cursor c = db.query(TABLE_LIGNECOMMANDE, new String[]{COL_NUMLIGNE, COL_NUMTABLE, COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMTABLE + "LIKE \"" + table + "\"", null, null, null, null);
        int count = c.getCount();
        if (count == 0){
            return false;
        }
        else {
            return true;
        }
    }

    // Regroupe toutes les commandes qui ont le même numéro de tables
    public int[] getNumLignedeCommandeWithTable(int table){
        Cursor c = db.query(TABLE_LIGNECOMMANDE, new String[]{COL_NUMLIGNE, COL_NUMTABLE,COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMTABLE + "LIKE \"" + table + "\"", null, null, null, null);
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

    // recherche la commande avec le numéro de ligne de commande
    public AdditionClass getCommandeWithNumLigne(int numLigne){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMCOMMANDE, COL_NUMLIGNECOMMANDE, COL_TYPEPAIEMENT}, COL_NUMLIGNECOMMANDE + " LIKE " + numLigne , null, null, null, null);
        int count = c.getCount();
        if (count == 0){
            return null;
        }
        return cursorToAddition(c);
    }

    // ne retient que les commandes non payées (où le payement est null)
    public ArrayList<AdditionClass> getAdditionToPay(int table){
        int[] numlignes = getNumLignedeCommandeWithTable(table);
        int count = numlignes.length;
        ArrayList<AdditionClass> additionsApayer = new ArrayList<AdditionClass>();
        for (int i = 0; i < count -1; i++){
            AdditionClass add = getCommandeWithNumLigne(numlignes[i]);
            if (add.getTypePaiement() == null){
                additionsApayer.add(add);
            }
        }
        return additionsApayer;
    }

    public double getTotalPrix (int table){
        ArrayList<AdditionClass> additionsApayer = getAdditionToPay(table);
        double prix = 0;
        for (int i = 0; i < additionsApayer.size(); i++){
            int numCommande = (additionsApayer.get(i)).getNumLignedeCommande();
            ldao.open();
            int numBoisson = (ldao.getLignewithnum(numCommande)).getBoisson();
            int qté = (ldao.getLignewithnum(numCommande)).getQuantité();
            ldao.close();
            bdao.open();
            double prixBoisson = (bdao.getBoissonwithNumboisson(numBoisson)).getPrix();
            bdao.close();
            prix = prix + qté * prixBoisson; //possible? pas de perte de précision ?
        }
        return prix;
    }

    public void setAdditionPayed (int table, String TypePaiemennt){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMCOMMANDE, COL_NUMLIGNECOMMANDE, COL_TYPEPAIEMENT}, COL_NUMTABLE + "LIKE \"" + num +  "AND" + COL_TYPEPAIEMENT + "LIKE \"" + null, null, null, null, null);
        int count = c.getCount();
        c.moveToFirst();
        for (int i = 0; i < count -1; i++){
            cursorToAddition(c).setTypePaiement(TypePaiemennt);
            c.moveToNext();
        }
        cursorToAddition(c).setTypePaiement(TypePaiemennt);
    }
}
