package com.example.ruben.hobbyhuset;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateKundeFragment extends Fragment {

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

    private OnFragmentInteractionListener mListener;

    public CreateKundeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_new_kunde, container, false);
        mActivity = getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ny kunde");


        // Inits label and edittext for fornavn
        tvFornavnLabel = (TextInputLayout) fragment.findViewById(R.id.textView_fornavnLabel);
        etFornavn = (EditText) fragment.findViewById(R.id.editText_fornavn);

        tvEtternavnLabel = (TextInputLayout) fragment.findViewById(R.id.textView_etternavnLabel);
        etEtternavn = (EditText) fragment.findViewById(R.id.editText_etternavn);

        tvAdresseLabel = (TextInputLayout) fragment.findViewById(R.id.textView_adresseLabel);
        etAdresse = (EditText) fragment.findViewById(R.id.editText_adresse);

        tvPostNrLabel = (TextInputLayout) fragment.findViewById(R.id.textView_postNrLabel);
        etPostNr = (EditText) fragment.findViewById(R.id.editText_postNr);

        btnSubmit = (Button) fragment.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewKunde();
            }
        });
        return fragment;
    }

    protected void createNewKunde() {
        String fornavn = etFornavn.getText().toString().trim();
        String etternavn = etEtternavn.getText().toString().trim();
        String adresse = etAdresse.getText().toString().trim();
        String postNr = etPostNr.getText().toString().trim();

        if (!checkInput(fornavn, etternavn, adresse, postNr)) {
            return;
        }

        // If input is correct, create Kunde object, check if online, and send Kunde-object
        Kunde kunde = new Kunde(fornavn, etternavn, adresse, postNr);

        NetworkHelper helper = new NetworkHelper(mActivity);
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();
            api.insertKunde(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    getActivity().finish();
                }
            }, kunde);
        } else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkInput(String fornavn, String etternavn, String adresse, String postNr) {

        // Simple checks for input. Could be made more robust before being sent to DB
        if (fornavn.isEmpty() || fornavn.length() > VARCHAR_MAX) {
            etFornavn.setError("Feil.");
            etFornavn.requestFocus();
            return false;

        } else {
            tvFornavnLabel.setErrorEnabled(false);
        }

        if (etternavn.isEmpty() || etternavn.length() > VARCHAR_MAX) {
            etEtternavn.setError("Feil");
            etEtternavn.requestFocus();
            return false;
        } else {
            tvEtternavnLabel.setErrorEnabled(false);
        }

        if (adresse.isEmpty() || etternavn.length() > VARCHAR_MAX) {
            etAdresse.setError("Feil.");
            etAdresse.requestFocus();
            return false;
        } else {
            tvAdresseLabel.setErrorEnabled(false);
        }

        if (postNr.isEmpty() || postNr.length() != POSTNR_LENGTH) {
            etPostNr.setError("Feil.");
            etPostNr.requestFocus();
            return false;
        } else {
            tvPostNrLabel.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
