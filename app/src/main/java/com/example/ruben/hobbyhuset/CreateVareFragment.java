package com.example.ruben.hobbyhuset;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateVareFragment extends Fragment {

    TextInputLayout tvVareNr;
    EditText etVareNr;
    TextInputLayout tvBetegnelse;
    EditText etBetegnelse;
    TextInputLayout tvPris;
    EditText etPris;
    TextInputLayout tvAntall;
    EditText etAntall;
    Button btnSubmit;

    private final int MAX_VNR_LENGTH = 5;
    private final int MAX_BETEGNELSE_LENGTH = 100;

    private Activity mActivity;

    public CreateVareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_create_vare, container, false);
        mActivity = getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ny vare");
        tvVareNr = (TextInputLayout) fragment.findViewById(R.id.textView_vareNr);
        etVareNr = (EditText) fragment.findViewById(R.id.editText_vareNr);

        tvBetegnelse = (TextInputLayout) fragment.findViewById(R.id.textView_betegnelse);
        etBetegnelse = (EditText) fragment.findViewById(R.id.editText_betegnelse);

        tvPris = (TextInputLayout) fragment.findViewById(R.id.textView_pris);
        etPris = (EditText) fragment.findViewById(R.id.editText_pris);

        tvAntall = (TextInputLayout) fragment.findViewById(R.id.textView_antall);
        etAntall = (EditText) fragment.findViewById(R.id.editText_antall);

        btnSubmit = (Button) fragment.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewVare();
            }
        });

        return fragment;
    }

    // Sends EditText input to RESTClient
    protected void createNewVare() {

        String vareNr = etVareNr.getText().toString().trim();
        String betegnelse = etBetegnelse.getText().toString().trim();
        String pris = etPris.getText().toString().trim();
        String antall = etAntall.getText().toString().trim();

        if (!checkInput(vareNr, betegnelse, pris, antall)) {
            return;
        }

        // If input is correct, create Kunde object, check if online, and send Kunde-object
        Vare vare = new Vare(vareNr, betegnelse, Double.parseDouble(pris), Integer.parseInt(antall));

        NetworkHelper helper = new NetworkHelper(mActivity);
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();
            api.insertVare(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    getActivity().finish();
                }
            }, vare);
        } else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }

    }

    // Simple checks for input
    private boolean checkInput(String vareNr, String betegnelse, String pris, String antall) {

        // Simple checks for input. Could be made more robust before being sent to DB
        if (vareNr.isEmpty() || vareNr.length() != MAX_VNR_LENGTH || !isNumeric(vareNr)) {
            etVareNr.setError("Må være 5 tegn.");
            etVareNr.requestFocus();
            return false;

        } else {
            tvVareNr.setErrorEnabled(false);
        }

        if (betegnelse.isEmpty() || betegnelse.length() > MAX_BETEGNELSE_LENGTH) {
            Log.d("Length", betegnelse.length() + "");
            etBetegnelse.setError("Feil.");
            etBetegnelse.requestFocus();
            return false;

        } else {
            tvBetegnelse.setErrorEnabled(false);
        }

        if (pris.isEmpty() || !isNumeric(pris)) {
            etPris.setError("Feil.");
            etPris.requestFocus();
            return false;

        } else {
            tvPris.setErrorEnabled(false);
        }

        if (antall.isEmpty() || !isNumeric(antall)) {
            etAntall.setError("Feil.");
            etAntall.requestFocus();
            return false;

        } else {
            tvAntall.setErrorEnabled(false);
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
