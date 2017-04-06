package com.example.ruben.hobbyhuset.kunde;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ruben.hobbyhuset.MainActivity;
import com.example.ruben.hobbyhuset.NewItemActivity;
import com.example.ruben.hobbyhuset.ordre.Ordre;
import com.example.ruben.hobbyhuset.ordre.OrdreAdapter;
import com.example.ruben.hobbyhuset.ordre.OrdreFragment;
import com.example.ruben.hobbyhuset.R;

import java.util.ArrayList;

public class ShowKundeActivity extends AppCompatActivity implements OrdreFragment.OnFragmentInteractionListener {

    private final static String TITLE = "Kunde";
    private int currentKunde;

    Button btnNyKunde;
    Button btnEndreKunde;
    Button btnSlettKunde;
    TextView tvTittel;
    TextView tvNavn;
    TextView tvKundeNr;
    TextView tvAdresse;
    TextView tvPostNr;
    TextView tvPostSted;
    TextView tvError;

    ListView lvOrdreForKunde;
    ArrayList<Ordre> mOrdreArray;
    OrdreAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_kunde);

        // Prepares XML
        btnNyKunde = (Button) findViewById(R.id.button_nyKunde);
        btnEndreKunde = (Button) findViewById(R.id.button_endreKunde);
        btnSlettKunde = (Button) findViewById(R.id.button_slettKunde);

        tvTittel = (TextView) findViewById(R.id.textView_tittel);
        tvNavn = (TextView) findViewById(R.id.textView_navn);
        tvKundeNr = (TextView) findViewById(R.id.textView_kundeNr);
        tvAdresse = (TextView) findViewById(R.id.textView_adresse);
        tvPostNr = (TextView) findViewById(R.id.textView_postNr);
        tvPostSted = (TextView) findViewById(R.id.textView_postSted);

        getSupportActionBar().setTitle(TITLE);

        String error = "";

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("Source", -1) == MainActivity.KUNDE_CODE) {

            // Tries to make a kunde-object out of the incoming data
            Kunde k = intent.getParcelableExtra("Kunde");
            if (k != null) {
                setKundeText(k);
                getKundeOrdre(k);
                currentKunde = k.getKundeNr();
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

    protected void nyKunde(View view) {
        Intent intent = new Intent(this, NewItemActivity.class);
        intent.putExtra("Source", MainActivity.KUNDE_CODE);
        startActivity(intent);
    }
/*
    protected void endreKunde(View view) {
        Intent intent = new Intent(this, EndreKundeActivity.class);
        intent.putExtra("Source", MainActivity.KUNDE_CODE);
        intent.putExtra("KundeNr", currentKunde);
        startActivity(intent);
    }

    protected void slettKunde(View view) {
        Intent intent = new Intent(this, SlettKundeActivity.class);
        intent.putExtra("Source", MainActivity.KUNDE_CODE);
        intent.putExtra("KundeNr", currentKunde);
        startActivity(intent);
    }
*/
    private void setKundeText(Kunde k) {
        tvTittel.setText("Kunde");
        tvNavn.setText(k.getFornavn() + " " + k.getEtternavn());
        tvKundeNr.setText(k.getKundeNr() + "");
        tvAdresse.setText(k.getAdresse() + "");
        tvPostNr.setText(k.getPostNr() + "");
        tvPostSted.setText("TODO");

    }

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
    public void onFragmentInteraction(Uri uri) {

    }

}
