package merliniserentant.bar_tender;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//public class Commander extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

  //  private Button commander;
   // private Button annuler;
    //private Spinner boisson;
    //private EditText quantité;
    //private EditText tabl;
    //private int num = 1;
    //private String login;
    //private int bsn;
    //private int qté;
    //private int table;

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_commander);

        // localise le spinner
//        boisson = (Spinner) findViewById(R.id.boisson);
  //      boisson.setOnItemClickListener(this);
    //    // créer la liste
        //List<String> listBoisson = new ArrayList<String>();
        // compléter avec boisson de la BDD
        //listBoisson.add("Orval"); // faire une boucle pour remplir ?
        // créer un adaptateur
        //ArrayAdapter list = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listBoisson);
        // layout
        //list.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // relie l'adaptateur au spinner
       // boisson.setAdapter(list);

        // localise les EditText
      //  quantité = (EditText) findViewById(R.id.quantité);
        //tabl = (EditText) findViewById(R.id.table);
        // Reprend les valeurs des EditText
        //qté = Integer.parseInt(quantité.getText().toString());
        //table = Integer.parseInt(tabl.getText().toString());
        // récupérer login ??

        // localise les Button
        //commander = (Button) findViewById(R.id.commander);
        //annuler = (Button) findViewById(R.id.annuler);
        //commander.setOnClickListener(this);
        //annuler.setOnClickListener(this);
    //}


    //@Override
    //public void onClick(View v) {
      //  switch (v.getId()) {
        //    case R.id.commander:
                // insérer nouvelle ligne de commande dans la BDD
          //      LigneCommande newLigne = new LigneCommande(num, login, bsn, qté, table);
                // insertLigneCommande (newLigne);
            //    num = num + 1;
            //case R.id.annuler:
                // retourner à la page précédente
              //  Intent intent = new Intent(Commander.this, MainActivity.class); // MenuFr = main
               // startActivity(intent);
        //}
    //}

    //@Override
    //public void onItemClick (AdapterView parent, View view, int position, long id){
        // récupérer l'item sélectionner
     //   String item = parent.getItemAtPosition(position).toString();
        // montrer l'item sélectionner
       // Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    //}
//}
