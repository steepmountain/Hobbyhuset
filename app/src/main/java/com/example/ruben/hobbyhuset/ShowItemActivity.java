package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ShowItemActivity extends AppCompatActivity {

    TextView tvTempTittel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        String output = "";
        tvTempTittel = (TextView) findViewById(R.id.textView_tempTittel);

        Intent intent = getIntent();

        // checks if the intent is empty upon entering
        if (intent != null) {

            // Sjekker hvilken datatype som kommer inn
            int intentCode = getIntent().getIntExtra("Source", -1);

            // hvis inndata er kunde
            if (intentCode == MainActivity.KUNDE_CODE) {
                Kunde k = (Kunde) getIntent().getParcelableExtra("Kunde");
                if (k != null) {
                    output += "Kunde + \n" +
                            k.getFornavn() + " " +k.getEtternavn();

                }
                else {
                    output += "Kundedata var tom.";
                }
            }

            // hvis inndata er ordre
            else if (intentCode == MainActivity.ORDRE_CODE) {
                Ordre o = (Ordre) getIntent().getParcelableExtra("Ordre");
                if (o != null) {
                    output += o.getOrdreNr();
                }
                else {
                    output += "Ordredata var tom.";
                }
            }

            // hvis inndata er vare
            else if (intentCode == MainActivity.VARE_CODE) {
                Vare v = (Vare) getIntent().getParcelableExtra("Vare");
                if (v != null) {
                    output += v.getBetegnelse();
                }
                else {
                    output += "Varedata var tom.";
                }
            }

            // hvis ikke matcher noen datatype, skal kun forekomme ved feil data eller default value
            else {
                output += "Ukjent datatype";
            }
        }
        else {
            output += "The intent was empty.";
        }


        tvTempTittel.setText(output);
    }
}
