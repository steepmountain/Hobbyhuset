package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowVareActivity extends AppCompatActivity {

    private final static String TITLE = "Vare";
    private String currentVare;

    TextView tvTittel;
    TextView tvVNr;
    TextView tvBetegnelse;
    TextView tvPris;
    TextView tvKatNr;
    TextView tvAntall;
    TextView tvHylle;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vare);

        // Prepares XML
        tvTittel = (TextView) findViewById(R.id.textView_tittel);
        tvVNr = (TextView) findViewById(R.id.textView_VNr);
        tvBetegnelse = (TextView) findViewById(R.id.textView_betegnelse);
        tvPris = (TextView) findViewById(R.id.textView_pris);
        tvKatNr = (TextView) findViewById(R.id.textView_katNr);
        tvAntall= (TextView) findViewById(R.id.textView_antall);
        tvHylle = (TextView) findViewById(R.id.textView_hylle);

        getSupportActionBar().setTitle(TITLE);

        String error = "";

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.getIntExtra("Source", -1) == MainActivity.VARE_CODE) {

            // Tries to make a kunde-object out of the incoming data
            Vare v = intent.getParcelableExtra("Vare");
            if (v != null) {
                setVareTekst(v);
                currentVare = v.getVareNr();
            }
            else {
                error += "Fant ingen vare.";
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

    protected void nyVare(View view) {
        Intent intent = new Intent(this, NewItemActivity.class);
        intent.putExtra("Source", MainActivity.VARE_CODE);
        startActivity(intent);
    }

    protected void endreVare(View view) {
        Intent intent = new Intent(this, EndreVareActivity.class);
        intent.putExtra("Source", MainActivity.VARE_CODE);
        intent.putExtra("VareNr", currentVare);
        startActivity(intent);
    }

    protected void slettVare(View view) {
        Intent intent = new Intent(this, SlettVareActivity.class);
        intent.putExtra("Source", MainActivity.VARE_CODE);
        intent.putExtra("VareNr", currentVare);
        startActivity(intent);
    }

    private void setVareTekst(Vare v) {
        tvTittel.setText("Vare");
        tvVNr.setText(v.getVareNr() + "");
        tvBetegnelse.setText(v.getBetegnelse());
        tvPris.setText(v.getPris() + "");
        tvKatNr.setText(v.getKatNr() + "");
        tvAntall.setText(v.getAntall() + "");
        tvHylle.setText(v.getHylle());

    }

}
