
package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private String table;

    BoissonDAO bdao = null;
    LigneCommandeDAO ldao = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CommanderDAO cdao = new CommanderDAO(this);
        LigneCommandeDAO ldao = new LigneCommandeDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander);
        b.open();
        l.open();
        // récupérer le numéro de tables
        tabl = (EditText) findViewById(R.id.table);
        table = tabl.getText().toString();

        // Créer tableau de commandes à valider pour la table sélectionnée
<<<<<<< HEAD
        ArrayAdapter<String> ListAdapterBsn = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Ajouter.newBoisson);
        ListView listBsn = (ListView) findViewById(R.id.list_Boisson);
        listBsn.setAdapter(ListAdapterBsn);

        ArrayAdapter<Integer> ListAdapterQté = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Ajouter.newQté);
        ListView listQté = (ListView) findViewById(R.id.list_Qté);
        listBsn.setAdapter(ListAdapterQté);
        b.close();
        l.close();
=======
        if (Ajouter.newBoisson != null) {
            ArrayAdapter<String> ListAdapterBsn = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Ajouter.newBoisson);
            ListView listBsn = (ListView) findViewById(R.id.list_Boisson);
            listBsn.setAdapter(ListAdapterBsn);

            ArrayAdapter<Integer> ListAdapterQté = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Ajouter.newQté);
            ListView listQté = (ListView) findViewById(R.id.list_Qté);
            listBsn.setAdapter(ListAdapterQté);
        }
>>>>>>> 6e0bce6a3b0e36a696652297f5c6d0fa3572170b

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
        b.open();
        l.open();
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
                        bdao.open();
                        numBsn = bdao.getBoissonwithName(bsn).getlNumboisson();
                        qté = Ajouter.newQté.get(0);
                        bdao.close();
                        // créér une nouvelle ligne de commande
                        LigneCommande newLigne = new LigneCommande(num, Utilisateur.connectedUser.getlogin(), numBsn, qté, Integer.parseInt(table));
                        ldao.open();
                        ldao.insertLignedecommande(newLigne); // ajouter la nouvelle ligne de commande à la BDD
                        ldao.close();
                        num = num + 1;
                        Ajouter.newBoisson.remove(0); // enlever les éléments ajoutés de la liste
                        Ajouter.newQté.remove(0);
                    }
                }
                finish();
            // IL FAUDRAIT PAS AJOUTER DES ENTREES DANS LA TABLE COMMANDE?
            case R.id.annuler:
                // retourner à la page précédente
                l.close();
                b.close();
                finish();
        }
        l.close();
        b.close();
    }

}
