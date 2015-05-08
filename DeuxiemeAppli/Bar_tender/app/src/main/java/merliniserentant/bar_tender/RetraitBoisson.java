package merliniserentant.bar_tender;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alice on 07-05-15.
 *
 * Classe qui permet au propriétaire de retirer une boisson de l'inventaire
 */
public class RetraitBoisson extends Activity{

    private Button retirer;
    private Button retour;

    private TextView textRetrait=null;
    private TextView textBoisson = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrait_boisson);

        retour = (Button) findViewById(R.id.returnbutton);
        retirer = (Button) findViewById(R.id.removebutton);

        textRetrait = (TextView) findViewById(R.id.retrait);
        textBoisson = (TextView) findViewById(R.id.boisson);

        final String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            textRetrait.setText("Removal");
            textBoisson.setText("Drink");
            retour.setText("Return");
            retirer.setText("Remove");
        }
        else if(Langue.equals("Néerlandais")){
            textRetrait.setText("Verwijdering");
            textBoisson.setText("Drank");
            retour.setText("Terug");
            retirer.setText("verwijderen");
        }

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                if(Langue.equals("Anglais")) {

                        Toast.makeText(RetraitBoisson.this, "Your drink has been added !", Toast.LENGTH_SHORT).show();

                }
                else if(Langue.equals("Néerlandais")) {

                    Toast.makeText(RetraitBoisson.this, "Uw drank was verweden !", Toast.LENGTH_SHORT).show();

                }
                else
                        Toast.makeText(RetraitBoisson.this, "Votre boisson a été retirée !", Toast.LENGTH_SHORT).show();

                finish();
            }
        });


    }

    private void delete(){
        String nom = getName();
        BoissonDAO boissondao = new BoissonDAO(this);
        boissondao.open();
        Boisson toremove = boissondao.getBoissonwithName(nom);
        int num = toremove.getNumboisson();
        boissondao.remove(num);

    }

    private String getName() {
        /* Récupération du nom  */
        EditText nameEditText = (EditText) findViewById(R.id.nameremove);
        String name = String.valueOf(nameEditText.getText());

        if (name.isEmpty()) {
            return null;
        }
        return name;
    }



}
