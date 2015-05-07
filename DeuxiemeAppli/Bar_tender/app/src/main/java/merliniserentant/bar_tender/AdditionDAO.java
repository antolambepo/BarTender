package merliniserentant.bar_tender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roselien on 03/05/15.
 */
public class AdditionDAO {

    public LigneDeCommandeDAO ldao;
    public BoissonDAO bdao;

    private final static String TABLE_COMMANDE = "COMMANDE";
    private final static String COL_NUMCOMMANDE = "NUMCOMMANDE";
    private final static int NUM_COL_NUMCOMMANDE = 0;
    private final static String COL_NUMLIGNE = "NUMLIGNE";
    private final static int NUM_COL_NUMLIGNE = 1;
    private final static String COL_TYPEPAIEMENT = "TYPEPAIEMENT";
    private final static int NUM_COL_TYPEPAIEMENT = 2;


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

    public void insertCommande (AdditionClass add){
        ContentValues values = new ContentValues();

        values.put(COL_NUMCOMMANDE, add.getNumAddition());
        values.put(COL_NUMLIGNE, add.getNumLignedeCommande());
        values.put(COL_TYPEPAIEMENT, add.getTypePaiement());
        db.insert(TABLE_COMMANDE, null, values);
    }
    // recherche la commande avec le numéro de ligne de commande
    public AdditionClass getCommandeWithNumLigne(int numLigne){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMCOMMANDE, COL_NUMLIGNE, COL_TYPEPAIEMENT}, COL_NUMLIGNE + " LIKE \"" + numLigne +"\"" , null, null, null, null);
        int count = c.getCount();
        if (count == 0){
            return null;
        }
        return cursorToAddition(c);
    }

    // ne retient que les commandes non payées (où le payement est null)
    public ArrayList<AdditionClass> getAdditionToPay(int table){
        ldao.open();
        int[] numlignes = ldao.getNumLignedeCommandeWithTable(table);
        ldao.close();
        int count = numlignes.length;
        ArrayList<AdditionClass> additionsApayer = new ArrayList<AdditionClass>();
        for (int i = 0; i < count; i++){
            AdditionClass add = getCommandeWithNumLigne(numlignes[i]);
            if (add.getTypePaiement() == null){
                additionsApayer.add(add);
            }
        }
        return additionsApayer;
    }

    private boolean isnumcomintable(int i){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMCOMMANDE, COL_NUMLIGNE, COL_TYPEPAIEMENT}, COL_NUMCOMMANDE + "=" + i , null, null, null, null);
        return !(c.getCount()==0);
    }
    public int nextnumcommande(){
        int i =1;
        while(isnumcomintable(i)){
            i++;
        }
        return i;
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

    public void setAdditionPayed (int numCommande, String TypePaiemennt){
        Cursor c = db.query(TABLE_COMMANDE, new String[]{COL_NUMCOMMANDE, COL_NUMLIGNE, COL_TYPEPAIEMENT}, COL_NUMCOMMANDE + "=" + numCommande +  "AND" + COL_TYPEPAIEMENT + "=" + null, null, null, null, null);
        int count = c.getCount();
        c.moveToFirst();
        for (int i = 0; i < count; i++){
            cursorToAddition(c).setTypePaiement(TypePaiemennt);
            c.moveToNext();
        }
        cursorToAddition(c).setTypePaiement(TypePaiemennt);
    }
}
