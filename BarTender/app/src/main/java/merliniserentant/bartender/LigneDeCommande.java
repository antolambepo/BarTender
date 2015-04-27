package com.example.roselien.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class LigneCommande extends ActionBarActivity implements View.OnClickListener {

    private Button boisson;
    private int num;
    private String login;
    private String bsn;
    private int qté;
    private int table;

    public LigneCommande(int num, String login, String bsn, int qté, int table)
    {
        this.bsn = bsn;
        this.qté = qté;
        this.table = table;
        this.num = num;
        this.login = login;
    }

    public String getBoisson() {
        return bsn;
    }

    public int getQuantité() {
        return qté;
    }

    public  int getTable(){
        return table;
    }

    public int getNum(){
        return num;
    }

    public String getLogin(){
        return login;
    }

    public void setBoisson(String bsn){
        this.bsn = bsn;
    }

    public void setQuantité(int qté){
        this.qté = qté;
    }

    public void setTable(int table){
        this.table = table;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne_commande);
        // Locate the button
        boisson = (Button) findViewById(R.id.boisson);
        // Capture the button click
        boisson.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ligne_commande, menu);
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boisson:
                Intent intent = new Intent(LigneCommande.this, ListeBoisson.class);
                startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
