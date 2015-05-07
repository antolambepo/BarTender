package merliniserentant.bar_tender;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Alice on 07-05-15.
 *
 * Classe qui permet au propriétaire de retirer une boisson de l'inventaire
 */
public class RetraitBoisson extends Activity{

    private Button retirer;
    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrait_boisson);

        retour = (Button) findViewById(R.id.returnbutton);
        retirer = (Button) findViewById(R.id.removebutton);


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
                System.out.println("C'est fini!");
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
            CollectorApp.notifyLong(R.string.Remplir_champs_boisson);
            return null;
        }
        return name;
    }



}
