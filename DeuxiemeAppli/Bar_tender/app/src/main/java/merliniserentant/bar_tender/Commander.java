
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


public class Commander extends Activity  {

    private Button ajouter;
    private Button commander;
    private Button annuler;
    private int table;

    private String bsn;
    private int numBsn;
    private int qté;

    BoissonDAO bdao = null;
    LigneDeCommandeDAO ldao = null;
    AdditionDAO adao =  null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bdao = new BoissonDAO(this);
        ldao = new LigneDeCommandeDAO(this);
        adao = new AdditionDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander);
        bdao.open();
        ldao.open();
        // récupérer le numéro de tables

        // Créer tableau de commandes à valider pour la table sélectionnée

        if (Login.newBoisson != null) {
            System.out.println("-----------" + Login.newBoisson);
            ArrayAdapter<String> ListAdapterBsn = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Login.newBoisson);
            ListView listBsn = (ListView) findViewById(R.id.list_Boisson);
            listBsn.setAdapter(ListAdapterBsn);

            ArrayAdapter<Integer> ListAdapterQté = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Login.newQté);
            ListView listQté = (ListView) findViewById(R.id.list_Qté);
            listQté.setAdapter(ListAdapterQté);

            ArrayAdapter<Integer> ListAdapterTable = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Login.newTable);
            ListView listTable = (ListView) findViewById(R.id.list_Table);
            listTable.setAdapter(ListAdapterTable);
        }
        bdao.close();
        ldao.close();


        // localise les Button
        ajouter = (Button) findViewById(R.id.ajouter);
        commander = (Button) findViewById(R.id.commander);
        annuler = (Button) findViewById(R.id.annulerCom);
        ajouter.setOnClickListener(onClickajouter);
        commander.setOnClickListener(onClickcommander);
        annuler.setOnClickListener(onClickannuler);


    }

    private View.OnClickListener onClickajouter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Commander.this, Ajouter.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener onClickcommander = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bdao.open();
            ldao.open();
            adao.open();
            // insérer les nouvelles ligne de commande dans la BDD
            if (Login.newBoisson == null || Login.newQté == null) {
                Toast.makeText(Commander.this, "Erreur1", Toast.LENGTH_SHORT).show(); // message d'erreur
            } else {
                int numCom = adao.nextnumcommande();
                while (Login.newBoisson != null) { // parcourir la liste des boissons ajoutées
                    bsn = Login.newBoisson.get(0);

                    numBsn = bdao.getBoissonwithName(bsn).getNumboisson();
                    System.out.println("ici6");

                    qté = Login.newQté.get(0);
                    System.out.println(qté);
                    table = Login.newTable.get(0);
                    System.out.println(table);
                    bdao.close();
                    // créér une nouvelle ligne de commande
                    int num = ldao.nextnumligne();
                    LigneDeCommande newLigne = new LigneDeCommande(num, Utilisateur.connectedUser.getlogin(), numBsn, qté, table);


                    AdditionClass newCommande = new AdditionClass(numCom, num, null);
                    ldao.open();
                    System.out.println("ici7");

                    ldao.insertLignedecommande(newLigne); // ajouter la nouvelle ligne de commande à la BDD
                    System.out.println("ici8");

                    adao.insertCommande(newCommande); // ajouter la commande dans la BDD
                    System.out.println("ici9");


                    num = num + 1;
                    Login.newBoisson.remove(0); // enlever les éléments ajoutés de la liste
                    Login.newQté.remove(0);
                    System.out.println("ici10");

                }
                numCom = numCom + 1;
            }
            adao.close();
            ldao.close();
            bdao.close();
            finish();
        }
    };
    private View.OnClickListener onClickannuler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Login.newBoisson = new ArrayList<String>();
            Login.newQté = new ArrayList<Integer>();
            Login.newTable = new ArrayList<Integer>();
            finish();
        }
    };

}