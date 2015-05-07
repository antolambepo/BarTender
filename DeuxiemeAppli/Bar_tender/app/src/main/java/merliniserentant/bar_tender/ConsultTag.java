package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merliniserentant on 1/05/15.
 */
public class ConsultTag extends Activity {


    Button retour = null;
    BoissonDAO boissondao = null;
    private List<String> listnom;
    private ListView listviewboisson;
    private ArrayList<Boisson> listboisson;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult);
        boissondao = new BoissonDAO(this);
        listviewboisson = (ListView)findViewById(R.id.listviewboisson);
        retour = (Button)findViewById(R.id.finconsult);
        boissondao.open();
        Intent actual_intent = getIntent();
        String Tag = actual_intent.getStringExtra("Tag");


        listboisson = boissondao.getBoissonwithTag(Tag);

        listnom = new ArrayList<String>();
        int i;

        if(listboisson!=null) {
            for (i = 0; i < listboisson.size(); i++) {
                listnom.add(listboisson.get(i).getNom());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listnom);
            listviewboisson.setAdapter(adapter);
        }
        listviewboisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(ConsultTag.this, consult_details.class);
                myintent.putExtra("numBoisson", listboisson.get(position).getNumboisson());
                startActivity(myintent);
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                }
            });

        boissondao.close();

    }
}
