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
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult);
        retour = (Button)findViewById(R.id.finconsult);

        Toast.makeText(Consult.this, "Holla",Toast.LENGTH_LONG).show();

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}