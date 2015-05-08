

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
    private ListView listviewboisson;
    private MyListViewAdapter2 myListViewAdapter;
    private TextView textCommande;

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
        for(i=0;i<50;i++) {
            Boisson myboisson = boissondao.getBoissonwithNumboisson(i);

            if(myboisson!=null){listboisson.add(myboisson);
                listnom.add(myboisson.getNom());
            }
        }
        textCommande = (TextView) findViewById(R.id.textCommande);
        String Langue  = MySQLite.Langue;
        if(Langue.equals("Anglais")){
            textCommande.setText("Board");
        }
        else if(Langue.equals("Néerlandais")){
            textCommande.setText("Kaart");
        }
        ListView myListView = (ListView) findViewById(R.id.listviewboisson);

        myListViewAdapter = new MyListViewAdapter2(this, listboisson);
        myListView.setAdapter(myListViewAdapter);



        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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