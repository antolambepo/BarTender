package merliniserentant.bartender.Activites;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import merliniserentant.bartender.R;
import merliniserentant.bartender.Utilisateur;


public class Main extends Activity {

    private Button retour = null;
    private Button carte = null;
    private Button addition = null;
    private Button commande = null;
    private Button inventaire = null;


    @Override
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);

        setContentView(R.layout.main);
        retour = (Button) findViewById(R.id.retour);
        addition = (Button) findViewById(R.id.addition);
        commande = (Button) findViewById(R.id.commande);
        inventaire = (Button) findViewById(R.id.inventaire);
        carte = (Button) findViewById(R.id.carte);


        String typeoflogin = Utilisateur.connectedUser.gettype();
        if (typeoflogin.equals("client")) {
            addition.setVisibility(View.INVISIBLE);
            commande.setVisibility(View.INVISIBLE);
            inventaire.setVisibility(View.INVISIBLE);
        }

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilisateur.connectedUser = null;
                finish();
            }
        });
        carte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(Main.this, ???.class);
               // startActivity(intent);
            }
        });
        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Main.this, ???.class);
                //Intent i = getIntent();
                //intent.putExtra("langue", i.getStringExtra("langue") );
                //startActivity(intent);
            }
        });
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(Main.this, ???.class);
                //Intent i = getIntent();
                //intent.putExtra("langue", i.getStringExtra("langue") );
                // startActivity(intent);
            }
        });
        inventaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(Main.this, ???.class);
                //Intent i = getIntent();
                //intent.putExtra("langue", i.getStringExtra("langue") );
                // startActivity(intent);
            }
        });

    }
}