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


public class Addition extends Activity {
    private Button mPasserelle;
    private EditText table;
    public static double prix;
    String numtbl;
    public static int numTable;
    AdditionDAO adao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        adao = new AdditionDAO(this);

        table = (EditText) findViewById(R.id.tableAddition);
        numtbl = table.getText().toString();
        //Problème ici parce que tu demandes direct le numéro de la table alors que la personne a pas eu le temps de l'encoder 
        numTable = Integer.parseInt(numtbl);

        mPasserelle = (Button) findViewById(R.id.premier);
        mPasserelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adao.open();
                if (adao.getAdditionToPay(numTable) == null){
                    Toast.makeText(Addition.this, "Aucune addition pour cette table", Toast.LENGTH_SHORT).show();
                }
                prix = adao.getTotalPrix(numTable);
                Intent secondeActivite = new Intent(Addition.this, Paiement.class);
                startActivity(secondeActivite);
                adao.close();
            }
        });
    }





}
