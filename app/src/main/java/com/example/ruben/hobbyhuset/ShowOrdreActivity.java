package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ShowOrdreActivity extends AppCompatActivity implements VareFragment.OnFragmentInteractionListener {

    private final static String TITLE = "Ordre";
    private int currentOrdre;

    TextView tvTittel;
    TextView tvOrdreNr;
    TextView tvOrdreDato;
    TextView tvSendtDato;
    TextView tvBetaltDato;
    TextView tvKundeNr;
    TextView tvError;

    ListView lvVarerForOrdre;
    ArrayList<Vare> mVareArray;
    VareAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ordre);

        // Prepares XML
        tvTittel = (TextView) findViewById(R.id.textView_tittel);
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

    protected void nyOrdre(View view) {
        Intent intent = new Intent(this, NyOrdreActivity.class);
        startActivity(intent);
    }

    protected void endreOrdre(View view) {
        Intent intent = new Intent(this, EndreOrdreActivity.class);
        intent.putExtra("Source", MainActivity.ORDRE_CODE);
        intent.putExtra("OrdreNr", currentOrdre);
        startActivity(intent);
    }

    protected void slettOrdre(View view) {
        Intent intent = new Intent(this, SlettOrdreActivity.class);
        intent.putExtra("Source", MainActivity.ORDRE_CODE);
        intent.putExtra("OrdreNr", currentOrdre);
        startActivity(intent);
    }

    // Sets text for current Ordre
    private void setOrdreTekst(Ordre o) {

        tvOrdreNr.setText("OrdreNr : " + o.getOrdreNr() + "");
        tvOrdreDato.setText("Ordredato : " + o.getOrdreDato());
        tvSendtDato.setText("Sendtdato : " + o.getSendtDato());
        tvBetaltDato.setText("Betaltdato : " + o.getBetaltDato());
        tvKundeNr.setText("KundeNr : " + o.getKundeNr() + "");
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
    public void onFragmentInteraction(Uri uri) {

    }
}
