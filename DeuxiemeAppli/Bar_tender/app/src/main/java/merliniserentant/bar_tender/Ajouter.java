package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        bdao = new BoissonDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        // localise les EditText et TextView
        boisson = (AutoCompleteTextView) findViewById(R.id.giveBoisson);
        List<String> listNomBoisson = new ArrayList<String>(); // à remplir avec liste de boisson
        listNomBoisson.add("Orval");
        //bdao.open();
        //Boisson[] listBoisson = bdao.aboveSeuil();
        //if (listBoisson !=null){
        //    for (int i = 0; i<listBoisson.length - 1; i++){
        //        listNomBoisson.add(listBoisson[i].getNom());
        //    }
        //}
        //bdao.close();
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
        qté = quantité.getText().toString();
        bsn = boisson.getText().toString();

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
                if (Integer.parseInt(qté) == 0 || bsn == null){
                    Toast.makeText(Ajouter.this, "Erreur", Toast.LENGTH_SHORT).show(); // message d'erreur
                }
                else {
                    if (bdao.getBoissonwithName(bsn).getStock() >= Integer.parseInt(qté)) { // vérifie si il y a assez de stock
                        newBoisson.add(bsn);
                        newQté.add(Integer.parseInt(qté));
                    } else {
                        Toast.makeText(Ajouter.this, "Plus de stock", Toast.LENGTH_SHORT).show(); // message d'erreur
                    }
                }
                finish();
            case R.id.annuler:
                // retourner à la page précédente
                finish();
        }
    }
}
