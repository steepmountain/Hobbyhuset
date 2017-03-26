package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowItemActivity extends AppCompatActivity {

    TextView tvTempTittel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        String output = "";
        tvTempTittel = (TextView) findViewById(R.id.textView_tempTittel);

        // Sjekk type extra som kommer inn
        Intent intent = getIntent();
            if (intent != null) {
            Kunde k = (Kunde) getIntent().getSerializableExtra("Film");
            if (k != null) {
                output = k.getFornavn() + k.getEtternavn();
            }
            else {
                output = "Nothing to show";
            }

        }
        else {
            output = "Nothing to show";
        }


        tvTempTittel.setText(output);
    }
}
