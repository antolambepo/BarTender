
package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//import android.support.v7.app.ActionBarActivity; Ca sert a quoi?? pcq ca fait une erreur
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Commander extends Activity implements View.OnClickListener{

    private Button ajouter;
    private Button commander;
    private Button annuler;
    private EditText tabl;
    private static int num = 1;
    private String bsn;
    private int numBsn;
    private int qté;
    private int table;

    CommanderDAO b = new CommanderDAO(null);
    LigneDeCommandeDAO l = new LigneDeCommandeDAO(null);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander);

        // récupérer le numéro de tables
        tabl = (EditText) findViewById(R.id.table);
        table = Integer.parseInt(tabl.getText().toString());

        // Créer tableau de commandes à valider pour la table sélectionnée
        ArrayAdapter<String> ListAdapterBsn = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Ajouter.newBoisson);
        ListView listBsn = (ListView) findViewById(R.id.list_Boisson);
        listBsn.setAdapter(ListAdapterBsn);

        ArrayAdapter<Integer> ListAdapterQté = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Ajouter.newQté);
        ListView listQté = (ListView) findViewById(R.id.list_Qté);
        listBsn.setAdapter(ListAdapterQté);


        // localise les Button
        ajouter = (Button) findViewById(R.id.ajouter);
        commander = (Button) findViewById(R.id.commander);
        annuler = (Button) findViewById(R.id.annulerCom);
        ajouter.setOnClickListener(this);
        commander.setOnClickListener(this);
        annuler.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ajouter:
                Intent intent = new Intent(Commander.this, Ajouter.class);
                startActivity(intent);

            case R.id.commander:
                // insérer les nouvelles ligne de commande dans la BDD
                if (Ajouter.newBoisson == null || Ajouter.newQté == null){
                    Toast.makeText(Commander.this, "Erreur", Toast.LENGTH_SHORT).show(); // message d'erreur
                }
                else {
                    while (Ajouter.newBoisson != null) { // parcourir la liste des boissons ajoutées
                        bsn = Ajouter.newBoisson.get(0);
                        numBsn = b.getBoissonwithName(bsn).getNumboisson();
                        qté = Ajouter.newQté.get(0);
                        // créér une nouvelle ligne de commande
                        LigneDeCommande newLigne = new LigneDeCommande(num, Utilisateur.connectedUser.getlogin(), numBsn, qté, table);
                        l.insertLignedecommande(newLigne); // ajouter la nouvelle ligne de commande à la BDD
                        num = num + 1;
                        Ajouter.newBoisson.remove(0); // enlever les éléments ajoutés de la liste
                        Ajouter.newQté.remove(0);
                    }
                }
                finish();

            case R.id.annuler:
                // retourner à la page précédente
                finish();
        }
    }

}


