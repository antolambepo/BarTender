package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Carte extends Activity {

    private Button carteBoisson = null;
    private Button recherche = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        carteBoisson = (Button) findViewById(R.id.button_carte);
        recherche = (Button) findViewById(R.id.button_recherche);

        carteBoisson.setOnClickListener(carteBoissonListener);
  //    recherche.setOnClickListener(rechercheListener);
    }

    private View.OnClickListener carteBoissonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Carte.this, Consult.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener rechercheListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(Carte.this, "A faire",Toast.LENGTH_LONG);
  //          Intent intent = new Intent(Carte.this, Search.class);
    //        startActivity(intent);
        }
    };
}
