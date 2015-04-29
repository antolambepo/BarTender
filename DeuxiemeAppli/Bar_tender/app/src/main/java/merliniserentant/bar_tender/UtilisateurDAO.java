package merliniserentant.bar_tender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by merliniserentant on 29/04/15.
 */
public class UtilisateurDAO {

    private static final String TABLE_LOGIN = "LOGIN";
    private static final String COL_LOGIN = "LOGINCODE";
    private static final int NUM_COL_LOGIN = 0;
    private static final String COL_MDP = "MDP";
    private static final int NUM_COL_MDP = 1;
    private static final String COL_TYPE = "TYPE";
    private static final int NUM_COL_TYPE = 3;
    private static final String COL_NAME = "NOM";
    private static final int NUM_COL_NAME = 2;

    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public UtilisateurDAO(Context context)
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
    public void insertLogin(Utilisateur utilisateur){

        ContentValues values = new ContentValues();

        values.put(COL_LOGIN, utilisateur.getlogin());
        values.put(COL_MDP, utilisateur.getmdp());
        values.put(COL_TYPE, utilisateur.gettype());
        values.put(COL_NAME, utilisateur.getname());
        //on insère l'objet dans la BDD via le ContentValues
        db.insert("LOGIN",null,values);
    }

    public Utilisateur getLoginWithlogin(String login){



        Cursor c = db.query(TABLE_LOGIN, new String[] {COL_LOGIN, COL_MDP,COL_NAME, COL_TYPE}, COL_LOGIN + " LIKE \"" + login +"\"", null, null, null, null);
        //pas oublier COL_TYPE au dessus
        return cursorToLogin(c);
    }

    private Utilisateur cursorToLogin(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un utilisateur
        Utilisateur utilisateur = new Utilisateur();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        utilisateur.setlogin(c.getString(NUM_COL_LOGIN));
        utilisateur.setmdp(c.getString(NUM_COL_MDP));
        utilisateur.settype(c.getString(NUM_COL_TYPE));
        utilisateur.setname(c.getString(NUM_COL_NAME));

        return utilisateur;
    }

}