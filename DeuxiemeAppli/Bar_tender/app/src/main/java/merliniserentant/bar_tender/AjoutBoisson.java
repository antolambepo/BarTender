package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.ContentValues;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Alice on 07-05-15.
 *
 * Classe qui permet au propriétaire d'ajouter une boisson à l'inventaire
 */
public class AjoutBoisson extends Activity{



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                return true; //On retourne true pour indiquer que l'action a été gérée.

            case R.id.action_quit:
                finish(); // On termine l'activité d'ajout sans enregistrer les données saisies.
                return true; //On retourne true pour indiquer que l'action a été gérée.

        }
        return false;
    }



    private void save() {
        BoissonDAO boissondao;

        String name = getName();
        String description = getDescription();
        int stock = getStock();
        int stockmax = getStockMax();
        int seuil = getSeuil();
        double price = getPrice();



    }

    private String getName() {
        /* Récupération du nom  */
        EditText nameEditText = (EditText) findViewById(R.id.nameboisson);
        String name = String.valueOf(nameEditText.getText());

        if (name.isEmpty()) {
            System.out.println("Remplir le champ Boisson !");
            return null;
        }
        return name;
    }
    private String getDescription() {
        /* Récupération de la description */
        EditText descriptionEditText = (EditText) findViewById(R.id.descriptionboisson);
        String description = String.valueOf(descriptionEditText.getText());
        if(description.isEmpty()){
            System.out.println("Remplir le champ Description !");
            return null;
        }
        return description ;
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
            System.out.println("Remplir le champ Prix !");
            return 0.0;
        }
        return Double.parseDouble(price);
    }
}
