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
    private String Langue  = MySQLite.Langue;
    UtilisateurDAO utilisateurdao = null;
    EditText nom ;

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
        nom = (EditText) findViewById (R.id.input3);


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
            if(login.getText().toString().equals("")||mdp.getText().toString().equals("")) {
                if (!nom.getText().toString().equals("")) {
                    Utilisateur newlogin = new Utilisateur(login.getText().toString(), mdp.getText().toString(), "CLIENT", "Nom par défaut");
                    //A changer!!!!!!!!!!!!!!!!!!!!!


                    utilisateurdao.open();
                    Utilisateur logintest = utilisateurdao.getLoginWithlogin(login.getText().toString());
                    if (logintest != null) {
                        login.getText().clear();
                        if(Langue.equals("Anglais")){
                            Toast.makeText(Login.this, "Nickname: " + newlogin.getlogin() + " already used. Please choose another one please." + login.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else if(Langue.equals("Néerlandais")){
                            Toast.makeText(Login.this, "Nijnaam: " + newlogin.getlogin() + " al gebruikt. Kies een andere alsjeblieft." + login.getText().toString(), Toast.LENGTH_SHORT).show();                        }
                        else{
                            Toast.makeText(Login.this, "Pseudo: " + newlogin.getlogin() + " déjà utilisé. Veuillez en choisir un autre svp." + login.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        utilisateurdao.insertLogin(newlogin);
                        if(Langue.equals("Anglais")){
                            Toast.makeText(Login.this, "Your account has been record " + login.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else if(Langue.equals("Néerlandais")){
                            Toast.makeText(Login.this, "Uw account is opnemen " + login.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Login.this, "Votre compte a bien été enregistrer " + login.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    utilisateurdao.close();
                    mdp.getText().clear();
                } else {
                    if(Langue.equals("Anglais")){
                        Toast.makeText(Login.this, "Please enter your full name", Toast.LENGTH_SHORT).show();

                    }
                    else if(Langue.equals("Néerlandais")){
                        Toast.makeText(Login.this, "Vul uw volledige naam", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(Login.this, "Veuillez rentrer votre nom et prénom", Toast.LENGTH_SHORT).show();

                    }


                }
            }
            else{
                if(Langue.equals("Anglais")){
                    Toast.makeText(Login.this, "The username or password can not be null", Toast.LENGTH_SHORT).show();

                }
                else if(Langue.equals("Néerlandais")){
                    Toast.makeText(Login.this, "De gebruikersnaam of password kan niet leeg zijn", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(Login.this, "Le pseudo ou mot de passe ne peuvent pas être nuls", Toast.LENGTH_SHORT).show();

                }



            }

        }
    };
    private View.OnClickListener testlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            utilisateurdao.open();
            Utilisateur logintest = utilisateurdao.getLoginWithlogin(login.getText().toString());
            if(logintest==null){
                if(Langue.equals("Anglais")){
                    Toast.makeText(Login.this, "Your nickname doesn't exist", Toast.LENGTH_SHORT).show();
                }
                else if(Langue.equals("Néerlandais")){
                    Toast.makeText(Login.this, "Je nickname bestaat niet", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login.this, "Votre pseudo n'existe pas", Toast.LENGTH_SHORT).show();
                }

            }
            else if((logintest.getmdp()).equals(mdp.getText().toString())) {
                Intent secondactivity = new Intent(Login.this, Main.class);
                String typeoflogin = logintest.gettype();

                Utilisateur.connectedUser=logintest;

                Intent i = getIntent();

                startActivity(secondactivity);
            }
            else{
                if(Langue.equals("Anglais")){
                    Toast.makeText(Login.this, "Your passwoord was incorrect.", Toast.LENGTH_SHORT).show();
                }
                else if(Langue.equals("Néerlandais")){
                    Toast.makeText(Login.this, "Je passtwoord was incorrect.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login.this, "Votre mot de passe est incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
            utilisateurdao.close();
            mdp.getText().clear();

        }
    };


}
