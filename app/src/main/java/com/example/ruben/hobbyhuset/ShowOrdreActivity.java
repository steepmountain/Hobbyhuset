package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ShowOrdreActivity extends AppCompatActivity implements VareFragment.OnFragmentInteractionListener {

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

        String error = "";

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("Source", -1) == MainActivity.ORDRE_CODE) {

            // Tries to make a kunde-object out of the incoming data
            Ordre o = intent.getParcelableExtra("Ordre");
            if (o != null) {
                setOrdreTekst(o);
                getOrdreVarer(o);
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

    private void setOrdreTekst(Ordre o) {
        tvTittel.setText("Ordre");
        tvOrdreNr.setText(o.getOrdreNr() + "");
        tvOrdreDato.setText(o.getOrdreDato());
        tvSendtDato.setText(o.getSendtDato());
        tvBetaltDato.setText(o.getBetaltDato());
        tvKundeNr.setText(o.getKundeNr()+ "");

    }

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
