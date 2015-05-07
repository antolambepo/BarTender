package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merliniserentant on 29/04/15.
 */
public class Login extends Activity {

    private Button test = null;
    private Button creer = null;
    private Button retourlangue= null;
    private EditText login = null;
    private EditText mdp= null;
    private TextView textmdp=null;
    private TextView textpseudo = null;
    private TextView textconnection=null;
    public static List<String> newBoisson;
    public static List<Integer> newQté;
    public static List<Integer> newTable;
    UtilisateurDAO utilisateurdao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newBoisson = new ArrayList<String>();
        newQté = new ArrayList<Integer>();
        newTable = new ArrayList<Integer>();

        super.onCreate(savedInstanceState);
        utilisateurdao = new UtilisateurDAO(this);


        setContentView(R.layout.connection);
        test = (Button) findViewById(R.id.button1);
        creer=  (Button) findViewById(R.id.button2);
        retourlangue=  (Button) findViewById(R.id.retourlangue);
        login = (EditText) findViewById(R.id.input1);
        mdp = (EditText) findViewById(R.id.input2);
        textmdp = (TextView) findViewById(R.id.mdp);
        textpseudo = (TextView)findViewById(R.id.pseudo);
        textconnection = (TextView)findViewById(R.id.connection);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            textpseudo.setText("Username");
            textmdp.setText("Password");
            textconnection.setText("Login");
            test.setText("Login");
            creer.setText("Create");
            retourlangue.setText("Chose another language");
        }
        else if(Langue.equals("Néerlandais")){
            textpseudo.setText("gebruikersnaam");
            textmdp.setText("wachtwoord");
            textconnection.setText("Log In");
            test.setText("Log In");
            creer.setText("Creëren");
            retourlangue.setText("Kies een andere taal");
        }

        test.setOnClickListener(testlistener);
        creer.setOnClickListener(creerlistener);
        retourlangue.setOnClickListener(retourlanguelistener);


    }
    private View.OnClickListener retourlanguelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };
    private View.OnClickListener creerlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Utilisateur newlogin = new Utilisateur(login.getText().toString(),mdp.getText().toString(),"PROPRIETAIRE","Nom par défaut");
            //A changer!!!!!!!!!!!!!!!!!!!!!


            utilisateurdao.open();
            Utilisateur logintest = utilisateurdao.getLoginWithlogin(login.getText().toString());
            if(logintest!=null){
                login.getText().clear();
                Toast.makeText(Login.this, "Pseudo: " + newlogin.getlogin() + " déjà utilisé. Veuillez en choisir un autre svp." + login.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            else{
                utilisateurdao.insertLogin(newlogin);
                Toast.makeText(Login.this, "Votre compte a bien été enregistrer " + login.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            utilisateurdao.close();
            mdp.getText().clear();

        }
    };
    private View.OnClickListener testlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            utilisateurdao.open();
            Utilisateur logintest = utilisateurdao.getLoginWithlogin(login.getText().toString());
            if(logintest==null){
                Toast.makeText(Login.this, "Votre pseudo n'existe pas", Toast.LENGTH_SHORT).show();
            }
            else if((logintest.getmdp()).equals(mdp.getText().toString())) {
                Intent secondactivity = new Intent(Login.this, Main.class);
                String typeoflogin = logintest.gettype();

                Utilisateur.connectedUser=logintest;

                Intent i = getIntent();

                startActivity(secondactivity);
            }
            else{

                Toast.makeText(Login.this, "Votre mot de passe est incorrect.", Toast.LENGTH_SHORT).show();


            }
            utilisateurdao.close();
            mdp.getText().clear();

        }
    };


}
