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
    private ListView listviewboisson;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult);
        boissondao = new BoissonDAO(this);
        listviewboisson= (ListView)findViewById(R.id.listviewboisson);
        retour = (Button)findViewById(R.id.finconsult);
        listboisson = new ArrayList<Boisson>();
        listnom = new ArrayList<String>();
        boissondao.open();
        int i;
        for(i=0;i<20;i++) {
            Boisson myboisson = boissondao.getBoissonwithNumboisson(i);
            if(myboisson!=null){listboisson.add(myboisson);
            listnom.add(myboisson.getNom());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listnom);
        listviewboisson.setAdapter(adapter);
        // Indique que le clic d'un élément de la liste doit appeler la méthode onItemClick d
        // cette classe (this).
        listviewboisson.setOnItemClickListener(this);

        boissondao.close();
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position) {
        Intent intent = new Intent(this, consult_details.class);

        // Pour la ligne en dessous j'ai un beug pour savoir comment passer le numero de la boisson
        // à la prochaine activity :/ ce qu'il y a mis après numboisson ca vient du tp 10
        intent.putExtra("numBoisson", listboisson.get(position).getNumboisson());
        startActivity(intent);
    }

}