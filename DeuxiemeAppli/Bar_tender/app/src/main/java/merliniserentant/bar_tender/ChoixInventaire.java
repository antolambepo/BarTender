package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alice on 06-05-15.
 */
public class ChoixInventaire extends Activity {

    private Button ajout = null;
    private Button retrait = null;
    private Button modification = null;
    private Button affichage = null;
    private Button retour = null;

    private BoissonDAO boissondao = null;

    @Override
    public void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.inventaire);
        ajout = (Button) findViewById(R.id.addboisson);
        retrait = (Button) findViewById(R.id.removeboisson);
        modification = (Button) findViewById(R.id.changedata);
        affichage = (Button) findViewById(R.id.showinventaire);
        retour = (Button) findViewById(R.id.returnbutton);

        ajout.setOnClickListener(ajoutListener);
        retrait.setOnClickListener(retraitListener);
        modification.setOnClickListener(modificationListener);
        affichage.setOnClickListener(affichageListener);
        retour.setOnClickListener(retourListener);
        boissondao = new BoissonDAO(this);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            ajout.setText("Add a drink");
            retrait.setText("Remove a drink");
            modification.setText("Edit the stock");
            affichage.setText("Show inventory");
            retour.setText("Return");
        }
        else if(Langue.equals("Néerlandais")){
            ajout.setText("Drankje toevoegen");
            retrait.setText("Drankje verwijderen");
            modification.setText("Voorraad wijzigen");
            affichage.setText("Inventaris tonen");
            retour.setText("Terug");
        }
        else{

        }

    }
    private View.OnClickListener ajoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent = new Intent(ChoixInventaire.this, AjoutBoisson.class);

            startActivity(intent);
        }
    };
    private View.OnClickListener retraitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ChoixInventaire.this, RetraitBoisson.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener modificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

             Intent intent = new Intent(ChoixInventaire.this, ModificationInventaire.class);
             startActivity(intent);
        }
    };
    private View.OnClickListener affichageListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChoixInventaire.this, ConsultInventaire.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener retourListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
