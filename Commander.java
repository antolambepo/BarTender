
package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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







public class Commander extends Activity{

    private Button ajouter;
    private Button commander;
    private Button annuler;
    private EditText tabl;
    private static int num = 7;
    private static int numCom =6;
    private String bsn;
    private int numBsn;
    private int qté;
    private String table;
    BoissonDAO bdao = null;
    LigneDeCommandeDAO ldao = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bdao = new BoissonDAO(this);
        ldao = new LigneDeCommandeDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander);
        bdao.open();
        ldao.open();
        tabl = (EditText) findViewById(R.id.table);

        // Créer tableau de commandes à valider pour la table sélectionnée

        if (Ajouter.newBoisson != null) {
            //System.out.println(Ajouter.newBoisson.get(0));

            ArrayAdapter<String> ListAdapterBsn = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Ajouter.newBoisson);
            ListView listBsn = (ListView) findViewById(R.id.list_Boisson);
            listBsn.setAdapter(ListAdapterBsn);

            ArrayAdapter<Integer> ListAdapterQté = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Ajouter.newQté);
            ListView listQté = (ListView) findViewById(R.id.list_Qté);
            listQté.setAdapter(ListAdapterQté);
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
        }
    };
    private  View.OnClickListener onClickcommander = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bdao.open();
            ldao.open();
            // insérer les nouvelles ligne de commande dans la BDD
            if (Ajouter.newBoisson == null || Ajouter.newQté == null){
                Toast.makeText(Commander.this, "Erreur1", Toast.LENGTH_SHORT).show(); // message d'erreur
            }
            else {
                while (Ajouter.newBoisson != null) { // parcourir la liste des boissons ajoutées
                    bsn = Ajouter.newBoisson.get(0);
                    System.out.println("++++++++" + bsn);
                    bdao.open();
                    numBsn = bdao.getBoissonwithName(bsn).getNumboisson();
                    qté = Ajouter.newQté.get(0);
                    System.out.println(qté);
                    bdao.close();
                    // créér une nouvelle ligne de commande
                    LigneDeCommande newLigne = new LigneDeCommande(num, Utilisateur.connectedUser.getlogin(), numBsn, qté, Integer.parseInt(table));
                    AdditionClass newCommande = new AdditionClass(numCom, num, null);
                    ldao.open();
                    ldao.insertLignedecommande(newLigne); // ajouter la nouvelle ligne de commande à la BDD
                    ldao.insertCommande(newCommande); // ajouter la commande dans la BDD
                    ldao.close();
                    num = num + 1;
                    Ajouter.newBoisson.remove(0); // enlever les éléments ajoutés de la liste
                    Ajouter.newQté.remove(0);
                }
                numCom = numCom +1;
            }
            ldao.close();
            bdao.close();
            finish();
        }
    };
    private View.OnClickListener onClickannuler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Ajouter.newBoisson = null;
            Ajouter.newQté = null;

        }
    };

}
