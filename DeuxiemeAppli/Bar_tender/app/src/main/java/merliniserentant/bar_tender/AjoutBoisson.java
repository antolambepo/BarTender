package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alice on 07-05-15.
 *
 * Classe qui permet au propriétaire d'ajouter une boisson à l'inventaire
 */
public class AjoutBoisson extends Activity{

    private Button appliquer;
    private Button retour;

    private Button breels6;
    private TextView breels1;
    private TextView breels2;
    private TextView breels3;
    private TextView breels4;
    private TextView breels5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_boisson);

        retour = (Button) findViewById(R.id.retourajout);
        breels6 = (Button) findViewById(R.id.breels6);
        breels1=(TextView) findViewById(R.id.breels1);
        breels2=(TextView) findViewById(R.id.breels2);
        breels3=(TextView) findViewById(R.id.breels3);
        breels4=(TextView) findViewById(R.id.breels4);
        breels5=(TextView) findViewById(R.id.breels5);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            breels1.setText("Logotype");
            breels2.setText("Stock");
            breels3.setText("Maximum stock");
            breels4.setText("threshold");
            breels5.setText("Price");
            breels6.setText("Toepassen");

        }
        else if(Langue.equals("Néerlandais")){
            breels1.setText("Logotype");
            breels2.setText("Stock");
            breels3.setText("Maximale Stock");
            breels4.setText("Drempel");
            breels5.setText("Prijs");
            breels6.setText("Apply");
        }


        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        breels6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });


    }



    private boolean save() {

        BoissonDAO boissondao = new BoissonDAO(this);
        boissondao.open();
        String nom = getNameFr();
        String name = getNameEn();
        String naam = getNameNl();

        String description = getDescriptionFr();
        String descrip = getDescriptionEn();
        String desc = getDescriptionNl();

        String logo = getLogo();

        int stock = getStock();
        int stockmax = getStockMax();
        int seuil = getSeuil();
        double price = getPrice();

        if(boissondao.create(nom, name,naam,description, descrip, desc, logo, price, stock, stockmax, seuil)){
            //CollectorApp.notifyLong(R.string.boisson_ajoutée);
            boissondao.close();
            return true;
        }
        else {
           // CollectorApp.notifyLong(R.string.boisson_erreur);
            boissondao.close();
            return false;
        }

    }



    private String getNameFr() {
        /* Récupération du nom  */
        EditText nameEditText = (EditText) findViewById(R.id.nameboisson);
        String name = String.valueOf(nameEditText.getText());

        if (name.isEmpty()) {
            CollectorApp.notifyLong(R.string.Remplir_champs_boisson);
            return null;
        }
        return name;
    }
    private String getNameEn() {
        /* Récupération du nom  */
        EditText nameEditText = (EditText) findViewById(R.id.nomboisson);
        String name = String.valueOf(nameEditText.getText());

        if (name.isEmpty()) {
            CollectorApp.notifyLong(R.string.Remplir_champs_boisson);
            return null;
        }
        return name;
    }
    private String getNameNl() {
        /* Récupération du nom  */
        EditText nameEditText = (EditText) findViewById(R.id.naamboisson);
        String name = String.valueOf(nameEditText.getText());

        if (name.isEmpty()) {
            CollectorApp.notifyLong(R.string.Remplir_champs_boisson);
            return null;
        }
        return name;
    }

    private String getDescriptionFr() {
        /* Récupération de la description */
        EditText descriptionEditText = (EditText) findViewById(R.id.descriptionboisson);
        String description = String.valueOf(descriptionEditText.getText());
        if(description.isEmpty()){
            return null;
        }
        return description ;
    }

    private String getDescriptionEn() {
        /* Récupération de la description */
        EditText descriptionEditText = (EditText) findViewById(R.id.descriptboisson);
        String description = String.valueOf(descriptionEditText.getText());
        if(description.isEmpty()){
            return null;
        }
        return description ;
    }

    private String getDescriptionNl() {
        /* Récupération de la description */
        EditText descriptionEditText = (EditText) findViewById(R.id.descboisson);
        String description = String.valueOf(descriptionEditText.getText());
        if(description.isEmpty()){
            return null;
        }
        return description ;
    }

    private String getLogo() {
        /* Récupération de la description */
        EditText logoEditText = (EditText) findViewById(R.id.logotypeboisson);
        String logo = String.valueOf(logoEditText.getText());
        if(logo.isEmpty()){
            return null;
        }
        return logo ;
    }


    private int getStock() {
        /* Récupération du stock */
        EditText stockEditText = (EditText) findViewById(R.id.stockboisson);
        String stock = String.valueOf(stockEditText.getText());
        if(stock.isEmpty()){
            return 0;
        }
        return Integer.parseInt(stock);
    }
    private int getStockMax() {
        /* Récupération du stockmax */
        EditText stockmaxEditText = (EditText) findViewById(R.id.stockmaxboisson);
        String stockmax = String.valueOf(stockmaxEditText.getText());
        if(stockmax.isEmpty()){
            return 0;
        }
        return Integer.parseInt(stockmax);
    }
    private int getSeuil() {
        /* Récupération de la description */
        EditText seuilEditText = (EditText) findViewById(R.id.seuilboisson);
        String seuil = String.valueOf(seuilEditText.getText());
        if(seuil.isEmpty()){
            return 0;
        }
        return Integer.parseInt(seuil);
    }
    private double getPrice() {
        /* Récupération de la description */
        EditText priceEditText = (EditText) findViewById(R.id.priceboisson);
        String price = String.valueOf(priceEditText.getText());
        if(price.isEmpty()){
            return 0.0;
        }
        return Double.parseDouble(price);
    }
}
