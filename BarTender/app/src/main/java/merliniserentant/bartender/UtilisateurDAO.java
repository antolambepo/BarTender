package merliniserentant.bartender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by merliniserentant on 23/04/15.
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



    private SQLiteDatabase bdd;

    private MySQLiteHelper mySQLiteHelper;

    public UtilisateurDAO(Context context){
        //On créer la BDD et sa table
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

    public long insertLogin(Utilisateur utilisateur){

        ContentValues values = new ContentValues();

        values.put(COL_LOGIN, utilisateur.getlogin());
        values.put(COL_MDP, utilisateur.getmdp());
        values.put(COL_TYPE, utilisateur.gettype());
        values.put(COL_NAME, utilisateur.getname());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_LOGIN, null, values);
    }

    public int updateLogin(String mylogin, Utilisateur utilisateur){

        ContentValues values = new ContentValues();
        values.put(COL_MDP, utilisateur.getmdp());

        return bdd.update(TABLE_LOGIN, values, COL_LOGIN + " = " +mylogin, null);
    }

    public int removeLoginWithlogin(String login){
        //Suppression d'un utilisateur de la BDD grâce à l'ID
        return bdd.delete(TABLE_LOGIN, COL_LOGIN + " = " +login, null);
    }

    public Utilisateur getLoginWithlogin(String login){
        //Récupère dans un Cursor les valeur correspondant à un utilisateur contenu dans la BDD (ici on sélectionne l'utilisateur grâce à son login)
        Cursor c = bdd.query(TABLE_LOGIN, new String[] {COL_LOGIN, COL_MDP,COL_NAME, COL_TYPE}, COL_LOGIN + " LIKE \"" + login +"\"", null, null, null, null);
        //pas oublier COL_TYPE au dessus
        return cursorToLogin(c);
    }

    //Cette méthode permet de convertir un cursor en un utilisateur
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