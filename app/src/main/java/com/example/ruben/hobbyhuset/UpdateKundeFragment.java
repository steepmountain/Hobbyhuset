package com.example.ruben.hobbyhuset;

import android.app.Activity;
import android.content.Context;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateKundeFragment extends Fragment {

    private Kunde currentKunde;

    TextInputLayout tvFornavnLabel;
    EditText etFornavn;
    TextInputLayout tvEtternavnLabel;
    EditText etEtternavn;
    TextInputLayout tvAdresseLabel;
    EditText etAdresse;
    TextInputLayout tvPostNrLabel;
    EditText etPostNr;
    Button btnSubmit;
    private Activity mActivity;

    private static int VARCHAR_MAX = 256;
    private static int POSTNR_LENGTH = 4;

    public UpdateKundeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_new_kunde, container, false);

        currentKunde = getArguments().getParcelable("Kunde");

        mActivity = getActivity();
        // Inits label and edittext for fornavn
        tvFornavnLabel = (TextInputLayout) fragment.findViewById(R.id.textView_fornavnLabel);
        etFornavn = (EditText) fragment.findViewById(R.id.editText_fornavn);
        etFornavn.setText(currentKunde.getFornavn());

        tvEtternavnLabel = (TextInputLayout) fragment.findViewById(R.id.textView_etternavnLabel);
        etEtternavn = (EditText) fragment.findViewById(R.id.editText_etternavn);
        etEtternavn.setText(currentKunde.getEtternavn());

        tvAdresseLabel = (TextInputLayout) fragment.findViewById(R.id.textView_adresseLabel);
        etAdresse = (EditText) fragment.findViewById(R.id.editText_adresse);
        etAdresse.setText(currentKunde.getAdresse());

        tvPostNrLabel = (TextInputLayout) fragment.findViewById(R.id.textView_postNrLabel);
        etPostNr = (EditText) fragment.findViewById(R.id.editText_postNr);
        etPostNr.setText(currentKunde.getPostNr() + "");

        btnSubmit = (Button) fragment.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKunde();
            }
        });
        return fragment;
    }

    private void updateKunde() {
        NetworkHelper helper = new NetworkHelper(mActivity);
        if (helper.isOnline()) {
            Kunde updateKunde = new Kunde(
                    currentKunde.getKundeNr(),
                    etFornavn.getText().toString(),
                    etEtternavn.getText().toString(),
                    etAdresse.getText().toString(),
                    etPostNr.getText().toString()
            );
            HobbyhusetApi api = new HobbyhusetApi();
            api.updateKunde(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    // TODO: hva skjer på received?
                }
            }, updateKunde);
        }
        else {
            Toast.makeText(mActivity, "Ingen nettverkstilgang!", Toast.LENGTH_LONG).show();
        }
    }
}
