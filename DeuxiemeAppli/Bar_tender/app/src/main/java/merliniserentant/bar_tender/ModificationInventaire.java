package merliniserentant.bar_tender;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alice on 07-05-15.
 */
public class ModificationInventaire extends Activity {

    private Button appliquer;
    private Button retour;

    private TextView textmodification;
    private TextView textboisson;
    private TextView textquantite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification_datastock);

        retour = (Button) findViewById(R.id.retourUp);
        appliquer = (Button) findViewById(R.id.appUp);

        textmodification = (TextView) findViewById(R.id.Modif);
        textboisson = (TextView) findViewById(R.id.ModifBoisson);
        textquantite = (TextView) findViewById(R.id.ModifQuantite);

        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            textmodification.setText("Change");
            textboisson.setText("Drink");
            retour.setText("Return");
            appliquer.setText("To Apply");
        }
        else if(Langue.equals("Néerlandais")){
            textmodification.setText("Wijziging");
            textboisson.setText("Drank");
            retour.setText("Terug");
            appliquer.setText("Toepassen");
        }


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appliquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChange();
                System.out.println("C'est fini!");
                finish();
            }
        });


    }
    private void saveChange() {
        BoissonDAO boissondao = new BoissonDAO(this);
        boissondao.open();
        String name = getName();
        int quantite = getStock();
        boissondao.upStock(name,quantite);

    }

    private String getName() {
        /* Récupération du nom  */
        EditText nameEditText = (EditText) findViewById(R.id.name);
        String name = String.valueOf(nameEditText.getText());

        if (name.isEmpty()) {
            System.out.println("Remplir le champ Boisson !");
            return null;
        }
        return name;
    }
    private int getStock() {
        /* Récupération du stock */
        EditText stockEditText = (EditText) findViewById(R.id.stock1);
        String stock = String.valueOf(stockEditText.getText());
        if(stock.isEmpty()){
            return 0;
        }
        return Integer.parseInt(stock);
    }

}
