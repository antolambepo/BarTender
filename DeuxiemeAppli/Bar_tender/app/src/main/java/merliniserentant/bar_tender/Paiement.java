package merliniserentant.bar_tender;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Paiement extends Activity {

    TextView montant;
    Button Payer;
    RadioButton cash;
    RadioButton bancontact;
    RadioButton mastercard;
    AdditionDAO adao;
    String typePaiement;
    private TextView nico1;
    private TextView nico2;
    private String Langue  = MySQLite.Langue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        adao = new AdditionDAO(this);

        montant = (TextView) findViewById(R.id.montant);
        montant.setText(Double.toString(Addition.prix));

        cash = (RadioButton) findViewById(R.id.radio1);
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typePaiement = "cash";
            }
        });

        bancontact = (RadioButton) findViewById(R.id.radio2);
        bancontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typePaiement = "Bancontact";
            }
        });

        mastercard = (RadioButton) findViewById(R.id.radio3);
        mastercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typePaiement = "Mastercard/Visa";
            }
        });

        Payer = (Button) findViewById(R.id.calcul);
        nico1 = (TextView)findViewById(R.id.nico1);
        nico2 = (TextView)findViewById(R.id.nico2);

        if(Langue.equals("Anglais")){
            Payer.setText("Pay");
            nico1.setText("Here is the amount to be paid:");
            nico2.setText("Ensure to choose your payment method:");

        }
        else if(Langue.equals("Néerlandais")){
            Payer.setText("Betalen");
            nico1.setText("Hier is het bedrag te betalen :");
            nico2.setText("Zorg ervoor dat u uw betalingsmethode te kiezen");
        }


        Payer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adao.open();
                for (int i = 0; i<Addition.numCommande.size(); i++){
                    adao.setAdditionPayed(Addition.numCommande.get(i), typePaiement);
                }
                adao.close();
                if(Langue.equals("Anglais")){
                    Toast.makeText(Paiement.this, "You have pay for! Congratulation !", Toast.LENGTH_SHORT).show();

                }
                else if(Langue.equals("Néerlandais")){
                    Toast.makeText(Paiement.this, "U betaalt voor! Felicitatie", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Paiement.this, "Vous avez payez ! Félicitation", Toast.LENGTH_SHORT).show();
                }

                Addition.numCommande = new ArrayList<Integer>();
                finish();
            }
        });
    }

}
