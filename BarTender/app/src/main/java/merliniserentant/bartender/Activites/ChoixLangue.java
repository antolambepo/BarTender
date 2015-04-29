package merliniserentant.bartender.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import merliniserentant.bartender.R;

/**
 * Created by merliniserentant on 23/04/15.
 */
public class ChoixLangue extends Activity {
    private Button francais = null;
    private Button english = null;
    private Button neederlands =null;
    @Override
    public void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.ecranlangue);
        francais = (Button) findViewById(R.id.Francais);
        english = (Button) findViewById(R.id.English);
        neederlands = (Button) findViewById(R.id.Neederlands);

        francais.setOnClickListener(francaisListener);
        english.setOnClickListener(anglaisListener);
        neederlands.setOnClickListener(neerlandaisListener);

    }
    private View.OnClickListener francaisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ChoixLangue.this, Login.class);
            intent.putExtra("langue", "francais");
            startActivity(intent);
        }
    };
    private View.OnClickListener anglaisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ChoixLangue.this, Login.class);
            intent.putExtra("langue", "anglais");
            startActivity(intent);
        }
    };private View.OnClickListener neerlandaisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ChoixLangue.this, Login.class);
            intent.putExtra("langue", "neerlandais");
            startActivity(intent);
        }
    };
}
