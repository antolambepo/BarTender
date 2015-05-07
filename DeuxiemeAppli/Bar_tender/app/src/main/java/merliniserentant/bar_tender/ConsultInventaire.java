package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Alice on 04-05-15.
 */
public class ConsultInventaire extends Activity {

    Button retour = null;
    private ArrayList<Boisson> listboisson;
    private MyListViewAdapter myListViewAdapter;
    private Button boutonNomBoisson;
    private Button boutonStock;
    private Button boutonStockMax;
    private Button boutonSeuil;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);

        setContentView(R.layout.affichage_inventaire);
        ListView myListView = (ListView) findViewById(R.id.show_listView);

        //Chargement des boissons
        BoissonDAO boissondao = new BoissonDAO(this);
        listboisson = new ArrayList<Boisson>();
        boissondao.open();
        int i;
        for(i=0;i<50;i++) {
            Boisson myboisson = boissondao.getBoissonwithNumboisson(i);
            if(myboisson!=null){
                listboisson.add(myboisson);
            }
        }

        //Creation de l'adapter pour faire la liaison entre les données (Boissons)
        //et l'affichage de caque ligne de la liste
        myListViewAdapter = new MyListViewAdapter(this, listboisson);
        myListView.setAdapter(myListViewAdapter);
        retour = (Button)findViewById(R.id.returninv);
        boutonNomBoisson = (Button) findViewById(R.id.boutonNomBoisson);
        boutonStock = (Button) findViewById(R.id.boutonStock);
        boutonStockMax = (Button) findViewById(R.id.boutonStockMax);
        boutonSeuil = (Button) findViewById(R.id.boutonSeuil);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            retour.setText("Return");
            boutonNomBoisson.setText("Name of beverage");
            boutonStock.setText("Stock");
            boutonStockMax.setText("Maximum Stock");
            boutonSeuil.setText("Threshold");
        }
        else if(Langue.equals("Néerlandais")){
            retour.setText("Terug");
            boutonNomBoisson.setText("Name van drank");
            boutonStock.setText("Voorraad");
            boutonStockMax.setText("Maximale voorraad");
            boutonSeuil.setText("Drempel");

        }



        boissondao.close();
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
     //   listviewboisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       //     @Override
    //        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //          Intent myintent = new Intent(Consult.this, consult_details.class);
     //           myintent.putExtra("numBoisson", listboisson.get(position).getNumboisson());
       //         startActivity(myintent);
         //   }
       // });



        //myListView.setOnItemClickListener(this)
    }

}
