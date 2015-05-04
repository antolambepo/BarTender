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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Ajouter extends Activity implements View.OnClickListener {

    private AutoCompleteTextView boisson;
    private EditText quantité;
    private String qté;
    private String bsn;
    private Button ajouter;
    private Button annuler;
    public static List<String> newBoisson;
    public static List<Integer> newQté;
    BoissonDAO bdao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newBoisson = new ArrayList<String>();
        newQté = new ArrayList<Integer>();
        bdao = new BoissonDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

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
        
        quantité = (EditText) findViewById(R.id.quantité);
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
                qté = quantité.getText().toString();
                System.out.println("-----------"+qté);
                bsn = boisson.getText().toString();
                System.out.println("-----------"+bsn);
                if (Integer.parseInt(qté) == 0 || bsn == null){
                    Toast.makeText(Ajouter.this, "Erreur", Toast.LENGTH_SHORT).show(); // message d'erreur
                }
                else {
                    System.out.println("-----------"+qté);
                        newBoisson.add(bsn);
                    System.out.println("-----------"+qté);

                    newQté.add(Integer.parseInt(qté));
                }
                finish();
            case R.id.annuler:
                // retourner à la page précédente
                finish();
        }
    }
}
