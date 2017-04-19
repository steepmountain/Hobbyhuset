package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/*
 * Activity to show a single Ordre object and a list of all varer for the Ordre
 */
public class ReadOrdreActivity extends AppCompatActivity {

    private final static String TITLE = "Ordre";
    private int currentOrdre;

    TextView tvOrdreNr;
    TextView tvOrdreDato;
    TextView tvSendtDato;
    TextView tvBetaltDato;
    TextView tvKundeNr;
    TextView tvError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ordre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Prepares XML
        tvOrdreNr = (TextView) findViewById(R.id.textView_ordreNr);
        tvOrdreDato = (TextView) findViewById(R.id.textView_ordreDato);
        tvSendtDato = (TextView) findViewById(R.id.textView_sendtDato);
        tvBetaltDato = (TextView) findViewById(R.id.textView_betaltDato);
        tvKundeNr = (TextView) findViewById(R.id.textView_kundeNr);

        getSupportActionBar().setTitle(TITLE);

        String error = "";

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("Source", -1) == MainActivity.ORDRE_CODE) {
            // Tries to make a kunde-object out of the incoming data
            Ordre o = intent.getParcelableExtra("Ordre");
            if (o != null) {
                setOrdreTekst(o);
                getOrdreVarer(o);
                currentOrdre = o.getOrdreNr();
            }
            else {
                error += "Fant ingen ordre.";
            }
        }
        else {
            error += "Noe gikk galt med sendingen av data.";
        }


        // Checks if the error string is empty
        if (!error.isEmpty()) {
            tvError = (TextView) findViewById(R.id.textView_error);
            tvError.setText(error);
        }
    }


    /*
     * Button methods
     */
    protected void nyOrdre(View view) {
        Intent intent = new Intent(this, CreateItemActivity.class);
        intent.putExtra("Source", MainActivity.ORDRE_CODE);
        startActivity(intent);
    }

    protected void endreOrdre(View view) {
        Intent intent = new Intent(this, UpdateItemActivity.class);
        intent.putExtra("Source", MainActivity.ORDRE_CODE);
        intent.putExtra("OrdreNr", currentOrdre);
        startActivity(intent);
    }

    protected void slettOrdre(View view) {
        Intent intent = new Intent(this, DeleteItemActivity.class);
        intent.putExtra("Source", MainActivity.ORDRE_CODE);
        intent.putExtra("OrdreNr", currentOrdre);
        startActivity(intent);
    }

    // Sets text for current Ordre
    private void setOrdreTekst(Ordre o) {
        tvOrdreNr.setText(o.getOrdreNr() + "");
        tvOrdreDato.setText("Ordredato : " + o.getOrdreDato());
        tvSendtDato.setText("Sendtdato : " + o.getSendtDato());
        tvBetaltDato.setText("Betaltdato : " + o.getBetaltDato());
        tvKundeNr.setText("KundeNr : " + o.getKundeNr() + "");
        getSupportActionBar().setTitle(o.getOrdreNr() + "");
    }

    // Gets every Vare in current Ordre
    private void getOrdreVarer(Ordre o) {
        Bundle args = new Bundle();
        args.putInt("OrdreNr", o.getOrdreNr());
        Fragment fragment = new VareFragment();
        fragment.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.varerForOrdreFragment, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
