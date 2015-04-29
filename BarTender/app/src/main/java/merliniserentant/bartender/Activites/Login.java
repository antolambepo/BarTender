package merliniserentant.bartender.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import merliniserentant.bartender.MySQLiteHelper;
import merliniserentant.bartender.R;
import merliniserentant.bartender.Utilisateur;
import merliniserentant.bartender.UtilisateurDAO;

/**
 * Created by merliniserentant on 23/04/15.
 */
public class Login extends Activity {

    private Button test = null;
    private Button creer = null;
    private Button retourlangue= null;
    private EditText login = null;
    private EditText mdp= null;
    private UtilisateurDAO utilisateurDAO=new UtilisateurDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utilisateurDAO.open();

        setContentView(R.layout.activity_main);
        test = (Button) findViewById(R.id.button1);
        creer=  (Button) findViewById(R.id.button2);
        retourlangue=  (Button) findViewById(R.id.retourlangue);
        login = (EditText) findViewById(R.id.input1);
        mdp = (EditText) findViewById(R.id.input2);

        test.setOnClickListener(testlistener);
        creer.setOnClickListener(creerlistener);
        retourlangue.setOnClickListener(retourlanguelistener);
        utilisateurDAO.close();

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
            //pas oublier "client" dans constructeur
            Utilisateur newlogin = new Login(login.getText().toString(),mdp.getText().toString(),"client");
            //,"client");

            utilisateurDAO.open();
            Utilisateur logintest = utilisateurDAO.getLoginWithlogin(login.getText().toString());
            if(logintest!=null){
                login.getText().clear();
                Toast.makeText(Login.this, "Pseudo: " + newlogin.getlogin() + " déjà utilisé. Veuillez en choisir un autre svp." + login.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            else{
                utilisateurDAO.insertLogin(newlogin);
                Toast.makeText(Login.this, "Votre compte a bien été enregistrer " + login.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            utilisateurDAO.close();
            mdp.getText().clear();
        }
    };
    private View.OnClickListener testlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            utilisateurDAO.open();
            Utilisateur logintest = utilisateurDAO.getLoginWithlogin(login.getText().toString());
            if(logintest==null){
                Toast.makeText(Login.this, "Votre pseudo n'existe pas", Toast.LENGTH_SHORT).show();
            }
            else if((logintest.getmdp()).equals(mdp.getText().toString())) {
                Intent secondactivity = new Intent(Login.this, Main.class);
                String typeoflogin = logintest.gettype();
                Utilisateur.connectedUser = logintest;
                Intent i = getIntent();
                secondactivity.putExtra("langue", i.getStringExtra("langue") );
                startActivity(secondactivity);
            }
            else{

                Toast.makeText(Login.this, "Votre mot de passe est incorrect.", Toast.LENGTH_SHORT).show();


            }

            mdp.getText().clear();
            utilisateurDAO.close();
        }
    };


}

