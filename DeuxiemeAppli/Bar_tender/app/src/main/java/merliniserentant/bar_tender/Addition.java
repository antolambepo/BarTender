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
    private Button retour;
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
        retour = (Button) findViewById(R.id.annulerAddition);
        table = (EditText) findViewById(R.id.tableAddition);
        tablelangue=(TexView) findViewById(R.id.tableA);
        //Problème ici parce que tu demandes direct le numéro de la table alors que la personne a pas eu le temps de l'encoder

        mPasserelle = (Button) findViewById(R.id.premier);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Néerlandais")){
            mPasserelle.setText("Betaling");
            tablelangue.setText("Tafel");
            retour.setText("Terug")
        }
        mPasserelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numtable = Integer.parseInt(table.getText().toString());
                ldao.open();
                if (ldao.tableExist(numtable)){
                    adao.open();
                    System.out.println("BDD ouverte");
                    if (adao.getAdditionToPay(numtable).size()==0) {
                        Toast.makeText(Addition.this, "Aucune addition pour cette table", Toast.LENGTH_SHORT).show();
                    }
                    else {
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
                    }
                    adao.close();
                }
                else {
                    Toast.makeText(Addition.this, "Cette table n'existe pas", Toast.LENGTH_SHORT).show();
                }
                ldao.close();
            }
        });


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





}
