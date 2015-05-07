package merliniserentant.bar_tender;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by Alice on 07-05-15.
 */
public class ModificationInventaire extends Activity {



    private void saveChange() {
        BoissonDAO boissondao = new BoissonDAO(this);
        boissondao.open();

        String name = getName();
        Boisson changeboisson = boissondao.getBoissonwithName(name);

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
    private int getStockMax() {
        /* Récupération du stockmax */
        EditText stockmaxEditText = (EditText) findViewById(R.id.stockmax1);
        String stockmax = String.valueOf(stockmaxEditText.getText());
        if(stockmax.isEmpty()){
            return 0;
        }
        return Integer.parseInt(stockmax);
    }
    private int getSeuil() {
        /* Récupération de la description */
        EditText seuilEditText = (EditText) findViewById(R.id.seuil1);
        String seuil = String.valueOf(seuilEditText.getText());
        if(seuil.isEmpty()){
            return 0;
        }
        return Integer.parseInt(seuil);
    }

}
