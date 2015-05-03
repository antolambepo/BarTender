package merliniserentant.bar_tender;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class carte extends Activity {

    private Button carteBoisson = null;
    private Button recherche = null;
    private EditText texte_recherche =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        carteBoisson = (Button) findViewById(R.id.button_carte);
        recherche = (Button) findViewById(R.id.button_recherche);
        texte_recherche = (EditText) findViewById(R.id.texte_recherche);
        carteBoisson.setOnClickListener(carteBoissonListener);
        recherche.setOnClickListener(rechercheListener);
    }

    private View.OnClickListener carteBoissonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(carte.this, Consult.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener rechercheListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if(texte_recherche.getText().toString().equals("")) {
                Toast.makeText(carte.this, R.string.beug, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(carte.this, ConsultTag.class);
                intent.putExtra("Tag",texte_recherche.getText().toString());
                startActivity(intent);}
        }
    };


}
