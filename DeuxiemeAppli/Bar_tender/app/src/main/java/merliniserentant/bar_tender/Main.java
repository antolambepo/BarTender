package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Main extends Activity {

    private Button retour = null;
    private Button carte = null;
    private Button addition = null;
    private Button commande = null;
    private Button inventaire = null;
    private BoissonDAO boissondao = null;


    @Override
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        boissondao = new BoissonDAO(this);
        setContentView(R.layout.main);
        retour = (Button) findViewById(R.id.retour);
        addition = (Button) findViewById(R.id.addition);
        commande = (Button) findViewById(R.id.commande);
        inventaire = (Button) findViewById(R.id.inventaire);
        carte = (Button) findViewById(R.id.carte);
        if(boissondao.getLangue().equals("Néerlandais")){
            retour.setText("Terug");
            addition.setText("Betalen");
            commande.setText("Bestellen");
            inventaire.setText("Stock");
            carte.setText("Kaart");
        }


        String typeoflogin = Utilisateur.connectedUser.gettype();
        if (typeoflogin.equals("CLIENT")) {
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
                Intent intent = new Intent(Main.this, carte.class);
                startActivity(intent);
            }
        });
        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Commander.class);

                startActivity(intent);
            }
        });
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Addition.class);
                startActivity(intent);
            }
        });
        inventaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(Main.this, ???.class);


                // startActivity(intent);
            }
        });

    }
}
