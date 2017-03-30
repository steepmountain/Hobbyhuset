package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;

public class ShowKundeActivity extends AppCompatActivity implements OrdreFragment.OnFragmentInteractionListener {

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
        tvTittel = (TextView) findViewById(R.id.textView_tittel);
        tvNavn = (TextView) findViewById(R.id.textView_navn);
        tvKundeNr = (TextView) findViewById(R.id.textView_kundeNr);
        tvAdresse = (TextView) findViewById(R.id.textView_adresse);
        tvPostNr = (TextView) findViewById(R.id.textView_postNr);
        tvPostSted = (TextView) findViewById(R.id.textView_postSted);

        String error = "";

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("Source", -1) == MainActivity.KUNDE_CODE) {

            // Tries to make a kunde-object out of the incoming data
            Kunde k = intent.getParcelableExtra("Kunde");
            if (k != null) {
                setKundeText(k);
                getKundeOrdre(k);
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

    private void oppdaterOrdreListe(ArrayList<Ordre> nyOrdreListe) {

        mAdapter = new OrdreAdapter(this, nyOrdreListe);
        lvOrdreForKunde.setAdapter(mAdapter);

        /* TODO: onClick skal føre til ShowOrdreActivity();
        lvOrdreForKunde.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(this, ShowKundeActivity.class);
                intent.putExtra("Source", MainActivity.KUNDE_CODE);
                intent.putExtra("Ordre", mOrdreArray.get(i));
                startActivity(intent);
            }
        });
        */

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
