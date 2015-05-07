package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merliniserentant on 30/04/15.
 */
public class Consult extends Activity{
    Button retour = null;
    BoissonDAO boissondao = null;
    private ArrayList<Boisson> listboisson;
    private List<String> listnom;
    private List<Double> listprix;
    private ListView listviewboisson;
    private ListView listviewprix;
    private TextView textCommande;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult);
        boissondao = new BoissonDAO(this);
        listviewboisson= (ListView)findViewById(R.id.listviewboisson);
        listviewprix = (ListView) findViewById(R.id.listprix);
        retour = (Button)findViewById(R.id.finconsult);
        listboisson = new ArrayList<Boisson>();
        listnom = new ArrayList<String>();
        listprix = new ArrayList<Double>();
        boissondao.open();
        int i;
        for(i=0;i<20;i++) {
            Boisson myboisson = boissondao.getBoissonwithNumboisson(i);
            if(myboisson!=null){listboisson.add(myboisson);
                listnom.add(myboisson.getNom());
                listprix.add(myboisson.getPrix());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listnom);
        ArrayAdapter<Double> adapter1 = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, listprix);
        listviewboisson.setAdapter(adapter);
        listviewprix.setAdapter(adapter1);
        textCommande = (TextView) findViewById(R.id.textComande);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            textCommande.setText("Board");
        }
        else if(Langue.equals("Néerlandais")){
            textCommande.setText("Kaart");
        }
        listviewboisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(Consult.this, consult_details.class);
                myintent.putExtra("numBoisson", listboisson.get(position).getNumboisson());
                startActivity(myintent);
            }
        });
        boissondao.close();
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //@Override
    //public void onItemClick(AdapterView<?> parent, View view, int position) {
       // Intent intent = new Intent(this, consult_details.class);

        // Pour la ligne en dessous j'ai un beug pour savoir comment passer le numero de la boisson
        // à la prochaine activity :/ ce qu'il y a mis après numboisson ca vient du tp 10
       // intent.putExtra("numBoisson", listboisson.get(position).getNumboisson());
       // startActivity(intent);
    //}

}