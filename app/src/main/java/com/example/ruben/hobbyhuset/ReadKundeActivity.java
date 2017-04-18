package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/*
 * Activity to show a single Kunde object and a list of all Ordre for the kunde
 */
public class ReadKundeActivity extends AppCompatActivity {

    private final static String TITLE = "Kunde";
    private int currentKundeNr;
    private Kunde currentKunde;

    Button btnNyKunde;
    Button btnEndreKunde;
    Button btnSlettKunde;
    TextView tvNavn;
    TextView tvKundeNr;
    TextView tvAdresse;
    TextView tvPostNr;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kunde);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Prepares XML
        btnNyKunde = (Button) findViewById(R.id.button_nyKunde);
        btnEndreKunde = (Button) findViewById(R.id.button_endreKunde);
        btnSlettKunde = (Button) findViewById(R.id.button_slettKunde);
        tvNavn = (TextView) findViewById(R.id.textView_navn);
        tvKundeNr = (TextView) findViewById(R.id.textView_kundeNr);
        tvAdresse = (TextView) findViewById(R.id.textView_adresse);
        tvPostNr = (TextView) findViewById(R.id.textView_postNr);

        getSupportActionBar().setTitle(TITLE);

        String error = "";

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("Source", -1) == MainActivity.KUNDE_CODE) {

            // Tries to make a kunde-object out of the incoming data
            currentKunde = intent.getParcelableExtra("Kunde");
            if (currentKunde != null) {
                setKundeText(currentKunde);
                getKundeOrdre(currentKunde);
                currentKundeNr = currentKunde.getKundeNr();
            }
            else {
                error += "Fant ingen kunde.";
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
     * Methods for buttons
     */
    protected void nyKunde(View view) {
        Intent intent = new Intent(this, CreateItemActivity.class);
        intent.putExtra("Source", MainActivity.KUNDE_CODE);
        startActivity(intent);
    }

    protected void endreKunde(View view) {
        Intent intent = new Intent(this, UpdateItemActivity.class);
        intent.putExtra("Source", MainActivity.KUNDE_CODE);
        intent.putExtra("Kunde", currentKunde);
        startActivity(intent);
    }

    protected void slettKunde(View view) {
        Intent intent = new Intent(this, DeleteItemActivity.class);
        intent.putExtra("Source", MainActivity.KUNDE_CODE);
        intent.putExtra("KundeNr", currentKundeNr);
        startActivity(intent);
    }

    // Sets the text for the given kunde
    private void setKundeText(Kunde k) {
        tvNavn.setText(k.getFornavn() + " " + k.getEtternavn());
        tvKundeNr.setText(k.getKundeNr() + "");
        tvAdresse.setText(k.getAdresse() + "");
        tvPostNr.setText(k.getPostNr() + "");
        getSupportActionBar().setTitle(k.getFornavn() + " " + k.getEtternavn());
    }

    // Gets ordre for a given kunde
    private void getKundeOrdre(Kunde k) {
        Bundle args = new Bundle();
        args.putInt("KundeNr", k.getKundeNr());
        Fragment fragment = new OrdreFragment();
        fragment.setArguments(args);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.ordreForKundeFragment, fragment);
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
