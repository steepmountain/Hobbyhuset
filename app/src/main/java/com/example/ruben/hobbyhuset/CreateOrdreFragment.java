package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrdreFragment extends Fragment {

    private Context mActivity;
    TextView tvOrdreDato;
    DatePicker dpOrdreDato;
    TextInputLayout tvKundeNr;
    EditText etKundeNr;
    Button btnSubmit;

    private static int KUNDENR_LENGTH = 4;

    public CreateOrdreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_create_ordre, container, false);
        mActivity = getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ny ordre");

        tvOrdreDato = (TextView) fragment.findViewById(R.id.textView_ordreDato);
        tvOrdreDato.setText("Ordredato");

        dpOrdreDato = (DatePicker) fragment.findViewById(R.id.datePicker_ordreDato);
        tvKundeNr = (TextInputLayout) fragment.findViewById(R.id.textView_kundeNr);

        etKundeNr = (EditText) fragment.findViewById(R.id.editText_kundeNr);

        btnSubmit = (Button) fragment.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewOrdre();
            }
        });

        return fragment;
    }

    // Sends EditText input to RESTClient
    private void createNewOrdre() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String ordreDato = df.format(new Date());
        String kundeNr = etKundeNr.getText().toString().trim();

        if (!checkInput(kundeNr)) {
            return;
        }

        Ordre ordre = new Ordre(ordreDato, Integer.parseInt(kundeNr));
        NetworkHelper helper = new NetworkHelper(mActivity);
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();
            api.insertOrdre(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    getActivity().finish();
                }
            }, ordre);
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }
    }

    // Simple checks for input
    private boolean checkInput(String kundeNr) {

        // Checks kundeNr, assumes date is correct
        if (kundeNr.isEmpty() || kundeNr.length() != KUNDENR_LENGTH || !isNumeric(kundeNr)) {
            etKundeNr.setError("Feil");
            etKundeNr.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
