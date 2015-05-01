package merliniserentant.bar_tender;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by merliniserentant on 30/04/15.
 */
public class Consult extends Activity{
    Button retour = null;
    BoissonDAO boissondao = null;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult);
        boissondao = new BoissonDAO(this);

        retour = (Button)findViewById(R.id.finconsult);

        boissondao.open();
        Boisson myboisson = boissondao.getBoissonwithNumboisson(17);
        if(myboisson==null){        Toast.makeText(Consult.this, "Pas trouvé la boisson",Toast.LENGTH_SHORT).show();
        }
        else {
                Toast.makeText(Consult.this, myboisson.getNom(), Toast.LENGTH_SHORT).show();
            }
        boissondao.close();
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}