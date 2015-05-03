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
    public LigneCommandeDAO ldao;
    public BoissonDAO bdao;

    public final static String TABLE_ADDITION = "TABLE_ADDITION";
    public final static String COL_NUMADDITION = "NUMADDITION";
    public final static int NUM_COL_NUMADDITION = 0;
    public final static String COL_NUMLIGNECOMMANDE = "NUMCOMMANDE";
    public final static int NUM_COL_NUMLIGNECOMMANDE = 1;
    public final static String COL_TYPEPAIEMENT = "TYPEPAIEMENT";
    public final static int NUM_COL_TYPEPAIEMENT = 2;

    private static final String TABLE_COMMANDE = "COMMANDE";
    private static final String COL_NUMCOMMANDE = "NUMCOMMANDE";
    private static final int NUM_COL_NUMCOMMANDE = 0;
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
        addition.setNumAddition(c.getInt(NUM_COL_NUMADDITION));
        addition.setNumLignedeCommande(c.getInt(NUM_COL_NUMLIGNECOMMANDE));
        addition.setTypePaiement(c.getString(NUM_COL_TYPEPAIEMENT));
        return addition;
    }

    // Regroupe toutes les commandes qui ont le même numéro de tables
    public int[] getNumLignedeCommandeWithTable(int table){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMCOMMANDE, COL_NUMTABLE,COL_LOGINCODE, COL_QUANTITE, COL_NUMBOISSON}, COL_NUMTABLE + "LIKE \"" + table, null, null, null, null);
        int count = c.getCount();
        if (count == 0){
            return null;
        }
        int[] ligneCommandes = new int[count];
        c.moveToFirst();
        for (int i = 0; i < count -1; i++){
            ligneCommandes[i] = c.getInt(NUM_COL_NUMLIGNECOMMANDE);
            c.moveToNext();
        }
        ligneCommandes[count-1] = c.getInt(NUM_COL_NUMLIGNECOMMANDE);
        return ligneCommandes;
    }

    // crée les additions pour les commandes de même table (même numéro pour toutes les commandes)
    public AdditionClass[] createAddition(int table){
        int[] numCommandes = getNumLignedeCommandeWithTable(table);
        int count = numCommandes.length;
        AdditionClass[] addition = new AdditionClass[count];
        for (int i = 0; i < count -1; i++){
         addition[i]= new AdditionClass(num, numCommandes[i], null);
        }
        num = num +1;
        return addition;
    }

    // ne retient que les commandes non payées (où le payement est null)
    public ArrayList<AdditionClass> getAdditionToPay(int table){
        AdditionClass[] allAdditions = createAddition(table);
        ArrayList<AdditionClass> additionsApayer = new ArrayList<AdditionClass>();
        for (int i = 0; i < allAdditions.length -1; i ++){
            if (allAdditions[i].getTypePaiement() == null){
                additionsApayer.add(allAdditions[i]);
            }
        }
        return additionsApayer;
    }

    public double getTotalPrix (int table){
        ArrayList<AdditionClass> additionsApayer = getAdditionToPay(table);
        double prix = 0;
        for (int i = 0; i < additionsApayer.size(); i++){
            int numCommande = (additionsApayer.get(i)).getNumLignedeCommande();
            int numBoisson = (ldao.getLignewithnum(numCommande)).getBoisson();
            int qté = (ldao.getLignewithnum(numCommande)).getQuantité();
            double prixBoisson = (bdao.getBoissonwithNumboisson(numBoisson)).getPrix();
            prix = prix + qté * prixBoisson; //possible? pas de perte de précision ?
        }
        return prix;
    }

    public void setAdditionPayed (int table, String TypePaiemennt){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMADDITION, COL_NUMLIGNECOMMANDE, COL_TYPEPAIEMENT}, COL_NUMTABLE + "LIKE \"" + num +  "AND" + COL_TYPEPAIEMENT + "LIKE \"" + null, null, null, null, null);
        int count = c.getCount();
        c.moveToFirst();
        for (int i = 0; i < count -1; i++){
            cursorToAddition(c).setTypePaiement(TypePaiemennt);
            c.moveToNext();
        }
        cursorToAddition(c).setTypePaiement(TypePaiemennt);
    }
}
