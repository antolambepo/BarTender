package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by merliniserentant on 29/04/15.
 */
public class ChoixLangue extends Activity {
    private Button francais = null;
    private Button english = null;
    private Button neederlands =null;
    private UtilisateurDAO utilisateurdao=null;

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
        utilisateurdao = new UtilisateurDAO(this);



    }
    private View.OnClickListener francaisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            utilisateurdao.open();
            utilisateurdao.setLangue("Français");
            utilisateurdao.close();
            Intent intent = new Intent(ChoixLangue.this, Login.class);

            startActivity(intent);
        }
    };
    private View.OnClickListener anglaisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            utilisateurdao.open();
            utilisateurdao.setLangue("Anglais");
            utilisateurdao.close();
            Intent intent = new Intent(ChoixLangue.this, Login.class);

            startActivity(intent);
        }
    };private View.OnClickListener neerlandaisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            utilisateurdao.open();
            utilisateurdao.setLangue("Néerlandais");
            utilisateurdao.close();
            Intent intent = new Intent(ChoixLangue.this, Login.class);

            startActivity(intent);
        }
    };
}
