package com.example.nicolas.addition;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Payement extends ActionBarActivity {

    Button Payer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);
        Payer = (Button) findViewById(R.id.calcul);
        Payer.setOnClickListener(PayerListener);
    }
    private OnClickListener PayerListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(Payement.this, "Vous avez payez ! Félicitation", Toast.LENGTH_SHORT).show();
        }
    };

}
