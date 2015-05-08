package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Ajouter extends Activity implements View.OnClickListener {

    private AutoCompleteTextView boisson;
    private EditText quantité;
    private EditText table;
    private int qté;
    private String bsn;
    private int tbl;
    private Button ajouter;
    private Button annuler;
    private TextView tableTexte;
    private TextView boissonTexte;
    private TextView texteQuantité;

    BoissonDAO bdao = null;


    private String Langue=MySQLite.Langue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bdao = new BoissonDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        tableTexte = (TextView) findViewById(R.id.tableTexte);
        boissonTexte = (TextView) findViewById(R.id.boissonTexte);
        texteQuantité = (TextView) findViewById(R.id.texteQuantité);
        ajouter = (Button) findViewById(R.id.ajouterBoisson);
        annuler = (Button) findViewById(R.id.annuler);

        if(Langue.equals("Anglais")){
            tableTexte.setText("table");
            boissonTexte.setText("drink");
            texteQuantité.setText("amount");
            ajouter.setText("Add");
            annuler.setText("Cancel");
        }
        else if(Langue.equals("Néerlandais")){
            tableTexte.setText("tafel");
            boissonTexte.setText("drank");
            texteQuantité.setText("hoeveelheid");
            ajouter.setText("Toevoegen");
            annuler.setText("Annuleren");
        }

        // localise les EditText et TextView
        boisson = (AutoCompleteTextView) findViewById(R.id.giveBoisson);
        List<String> listNomBoisson = new ArrayList<String>(); // à remplir avec liste de boisson
       // listNomBoisson.add("Orval");
        bdao.open();
        int i;
        for(i=1;i<20;i++){
           Boisson boisson  = bdao.getBoissonwithNumboisson(i);
            if(boisson!=null && boisson.getStock()>0){
                listNomBoisson.add(boisson.getNom());
            }
        }
        bdao.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNomBoisson);
        boisson.setAdapter(adapter);
        boisson.setThreshold(1); //nombre de caractère pour suggestion
        // Quand l'utilisateur appuie sur une boisson de la liste
        boisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bsn = (String) parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent){
                if (Langue.equals("Néerlandais")){
                    Toast.makeText(Ajouter.this, "Selecteer drank", Toast.LENGTH_SHORT).show();
                }
                else if (Langue.equals("Anglais")){
                    Toast.makeText(Ajouter.this, "Select beverage", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Ajouter.this, "Sélectionner boisson", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        quantité = (EditText) findViewById(R.id.quantitéAjouter);
        table = (EditText) findViewById(R.id.tableAjouter);

        // Reprend les valeurs

        // localise les boutons

        ajouter.setOnClickListener(this);
        annuler.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ajouterBoisson:
                qté = Integer.parseInt(quantité.getText().toString());
                tbl = Integer.parseInt(table.getText().toString());


                bsn = boisson.getText().toString();
                if (qté == 0 || bsn == null){
                    if (Langue.equals("Néerlandais")){
                        Toast.makeText(Ajouter.this, "Error: leeg command", Toast.LENGTH_SHORT).show(); // message d'erreur
                    }
                    else if (Langue.equals("Anglais")){
                        Toast.makeText(Ajouter.this, "Error: empty command", Toast.LENGTH_SHORT).show(); // message d'erreur
                    }
                    else {
                        Toast.makeText(Ajouter.this, "Erreur: commande vide", Toast.LENGTH_SHORT).show(); // message d'erreur
                    }


                }
                else {
                        Login.newBoisson.add(bsn);

                    Login.newQté.add(qté);

                    Login.newTable.add(tbl);

                }
                Intent intent2 = new Intent(Ajouter.this, Commander.class);
                startActivity(intent2);
                finish();
            case R.id.annuler:
                // retourner à la page précédente
                Intent intent3 = new Intent(Ajouter.this, Commander.class);
                startActivity(intent3);
                finish();
        }
    }
}
