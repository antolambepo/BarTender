package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Addition extends Activity {
    private Button mPasserelle;
    private EditText table;
    public static double prix;
    private int numtable;
    public static ArrayList<Integer> numCommande;
    AdditionDAO adao;
    LigneDeCommandeDAO ldao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        adao = new AdditionDAO(this);
        ldao = new LigneDeCommandeDAO(this);

        table = (EditText) findViewById(R.id.tableAddition);
        //Problème ici parce que tu demandes direct le numéro de la table alors que la personne a pas eu le temps de l'encoder

        mPasserelle = (Button) findViewById(R.id.premier);
        mPasserelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numtable = Integer.parseInt(table.getText().toString());
                ldao.open();
                if (ldao.tableExist(numtable)){
                    adao.open();
                    if (adao.getAdditionToPay(numtable) == null) {
                        Toast.makeText(Addition.this, "Aucune addition pour cette table", Toast.LENGTH_SHORT).show();
                    }
                    ArrayList<AdditionClass> additions = adao.getAdditionToPay(numtable);
                    // reprend les numCommande à payer
                    numCommande.add(additions.get(0).getNumAddition());
                    for (int i = 1; i< additions.size(); i++ ){
                        if (additions.get(i-1).getNumAddition() != additions.get(i).getNumAddition()) {
                            numCommande.add(additions.get(i).getNumAddition());
                        }
                    }
                    prix = adao.getTotalPrix(numtable);
                    Intent secondeActivite = new Intent(Addition.this, Paiement.class);
                    startActivity(secondeActivite);
                    adao.close();
                }
                else {
                    Toast.makeText(Addition.this, "Cette table n'existe pas", Toast.LENGTH_SHORT).show();
                }
                ldao.close();
            }
        });
    }





}
