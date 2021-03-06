
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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class Commander extends Activity  {

    private Button ajouter;
    private Button commander;
    private Button annuler;
    private TextView Nicomachine_Boisson;
    private TextView Nicomachine_Table;
    private TextView Nicomachine_Quantite;
    private int table;

    private String bsn;
    private int numBsn;
    private int qté;

    BoissonDAO bdao = null;
    LigneDeCommandeDAO ldao = null;
    CommandeDAO adao =  null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bdao = new BoissonDAO(this);
        ldao = new LigneDeCommandeDAO(this);
        adao = new CommandeDAO(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander);
        bdao.open();
        ldao.open();
        adao.open();
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



        // localise les Button
        ajouter = (Button) findViewById(R.id.ajouter);
        commander = (Button) findViewById(R.id.commander);
        annuler = (Button) findViewById(R.id.annulerCom);
        Nicomachine_Boisson = (TextView) findViewById(R.id.Nicomachine_Boisson);
        Nicomachine_Quantite =(TextView) findViewById(R.id.Nicomachine_Quantite);
        Nicomachine_Table = (TextView) findViewById(R.id.Nicomachine_Table);
        ajouter.setOnClickListener(onClickajouter);
        commander.setOnClickListener(onClickcommander);
        annuler.setOnClickListener(onClickannuler);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            ajouter.setText("Add");
            commander.setText("Order");
            annuler.setText("Cancel");
            Nicomachine_Boisson.setText("Drink");
            Nicomachine_Quantite.setText("Amount");
            Nicomachine_Table.setText("Table");
        }
        else if(Langue.equals("Néerlandais")){
            ajouter.setText("Toevoegen");
            commander.setText("Bestalen");
            annuler.setText("Canceleren");
            Nicomachine_Boisson.setText("Drank");
            Nicomachine_Quantite.setText("Hoeveelheid");
            Nicomachine_Table.setText("Tafel");
        }


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

            // insérer les nouvelles ligne de commande dans la BDD
            if (Login.newBoisson == null || Login.newQté == null) {
                Toast.makeText(Commander.this, "Erreur1", Toast.LENGTH_SHORT).show(); // message d'erreur
            } else {
                int numCom = adao.nextnumcommande();
                while (!Login.newBoisson.isEmpty()) { // parcourir la liste des boissons ajoutées
                    bsn = Login.newBoisson.get(0);

                    numBsn = bdao.getBoissonwithName(bsn).getNumboisson();

                    qté = Login.newQté.get(0);
                    table = Login.newTable.get(0);

                    // créér une nouvelle ligne de commande
                    int num = ldao.nextnumligne();
                    LigneDeCommande newLigne = new LigneDeCommande(num, Utilisateur.connectedUser.getlogin(), numBsn, qté, table);
                    Commande newCommande = new Commande(numCom, num, null);
                    bdao.downStock(numBsn, qté);
                    ldao.insertLignedecommande(newLigne); // ajouter la nouvelle ligne de commande à la BDD
                    adao.insertCommande(newCommande); // ajouter la commande dans la BDD
                    Login.newBoisson.remove(0); // enlever les éléments ajoutés de la liste
                    Login.newQté.remove(0);
                    Login.newTable.remove(0);
                }
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
            adao.close();
            ldao.close();
            bdao.close();
            finish();
        }
    };

}
