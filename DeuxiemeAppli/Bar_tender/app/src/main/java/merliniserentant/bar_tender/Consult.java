package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

        boissondao.close();
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}