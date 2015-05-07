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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bdao = new BoissonDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        tableTexte = (TextView) findViewById(R.id.tableTexte);
        boissonTexte = (TextView) findViewById(R.id.boissonTexte);
        texteQuantité = (TextView) findViewById(R.id.texteQuantité);

        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            tableTexte.setText("table");
            boissonTexte.setText("drink");
            texteQuantité.setText("amount");
        }
        else if(Langue.equals("Néerlandais")){
            tableTexte.setText("tafel");
            boissonTexte.setText("drank");
            texteQuantité.setText("hoeveelheid");
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
                Toast.makeText(Ajouter.this, "Sélectionner boisson", Toast.LENGTH_SHORT).show();
            }
        });
        
        quantité = (EditText) findViewById(R.id.quantitéAjouter);
        table = (EditText) findViewById(R.id.tableAjouter);

        // Reprend les valeurs

        // localise les boutons
        ajouter = (Button) findViewById(R.id.ajouterBoisson);
        annuler = (Button) findViewById(R.id.annuler);
        ajouter.setOnClickListener(this);
        annuler.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ajouterBoisson:
                qté = Integer.parseInt(quantité.getText().toString());
                tbl = Integer.parseInt(table.getText().toString());

                System.out.println("-----------"+qté);

                bsn = boisson.getText().toString();
                System.out.println("-----------"+bsn);
                if (qté == 0 || bsn == null){
                    Toast.makeText(Ajouter.this, "Erreur", Toast.LENGTH_SHORT).show(); // message d'erreur
                }
                else {
                    System.out.println("-----------"+qté);
                        Login.newBoisson.add(bsn);
                    System.out.println("-----------"+Login.newBoisson.get(0));

                    Login.newQté.add(qté);
                    System.out.println("-----------"+qté);

                    Login.newTable.add(tbl);

                }
                System.out.println("Se fini bien");
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
