package com.example.ruben.hobbyhuset;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewKundeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewKundeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewKundeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvTitle;
    TextView tvFornavnLabel;
    TextView etFornavn;
    TextView tvEtternavnLabel;
    EditText etEtternavn;
    TextView tvAdresseLabel;
    EditText etAdresse;
    TextView tvPostNrLabel;
    EditText etPostNr;
    Button btnSubmit;
    private Activity mActivity;

    private static int VARCHAR_MAX = 256;
    private static int POSTNR_LENGTH = 4;

    EditText[] inputFields;

    private OnFragmentInteractionListener mListener;

    public NewKundeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewKundeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewKundeFragment newInstance(String param1, String param2) {
        NewKundeFragment fragment = new NewKundeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_new_kunde, container, false);
        mActivity = getActivity();

        inputFields = new EditText[4];

        // Title for newKunde
        tvTitle = (TextView) mActivity.findViewById(R.id.textView_tittel);

        // Inits label and edittext for fornavn
        tvFornavnLabel = (TextView) fragment.findViewById(R.id.textView_fornavnLabel);
        tvFornavnLabel.setText("Fornavn");
        etFornavn = (EditText) fragment.findViewById(R.id.editText_fornavn);
        etFornavn.setHint("Fornavn");
        inputFields[0] = (EditText) etFornavn;

        tvEtternavnLabel = (TextView) fragment.findViewById(R.id.textView_etternavnLabel);
        tvEtternavnLabel.setText("Etternavn");
        etEtternavn = (EditText) fragment.findViewById(R.id.editText_etternavn);
        etEtternavn.setHint("Etternavn");
        inputFields[1] = (EditText) etEtternavn;

        tvAdresseLabel = (TextView) fragment.findViewById(R.id.textView_adresseLabel);
        tvAdresseLabel.setText("Adresse");
        etAdresse = (EditText) fragment.findViewById(R.id.editText_adresse);
        etAdresse.setHint("Adresse");
        inputFields[2] = (EditText) etAdresse;

        tvPostNrLabel = (TextView) fragment.findViewById(R.id.textView_postNrLabel);
        tvPostNrLabel.setText("PostNr");
        etPostNr = (EditText) fragment.findViewById(R.id.editText_postNr);
        etPostNr.setHint("PostNr");
        inputFields[3] = (EditText) etPostNr;

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
        // resets colors
        for (EditText field : inputFields) {
            field.setBackgroundColor(Color.WHITE);
        }

        String fornavn = etFornavn.getText().toString();
        String etternavn = etEtternavn.getText().toString();
        String adresse = etAdresse.getText().toString();
        String postNr = etPostNr.getText().toString();

        if (!checkInput(fornavn, etternavn, adresse, postNr)) {
            return;
        }

        // If input is correct, create Kunde object, check if online, and send Kunde-object
        Kunde kunde = new Kunde(fornavn, etternavn, adresse, Integer.parseInt(postNr));

        NetworkHelper helper = new NetworkHelper(mActivity);
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();
            api.insertKunde(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    // TODO: WHAT TO DO WITH RESPONSE?
                }
            }, kunde);
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkInput(String fornavn, String etternavn, String adresse, String postNr) {

        // TODO: sjekk VARCAHR lengde i DB
        // TODO: make class for checking inputs
        boolean[] errors = new boolean[]{false, false, false, false};
        boolean correctInput = true;

        // Simple checks for input. Could be made more robust before being sent to DB
        if (fornavn.isEmpty() || fornavn.length() > VARCHAR_MAX) {
            errors[0] = true;
        }

        if (etternavn.isEmpty() || etternavn.length() > VARCHAR_MAX) {
            errors[1] = true;
        }

        if (adresse.isEmpty() || etternavn.length() > VARCHAR_MAX) {
            errors[2] = true;
        }

        if (postNr.isEmpty() || postNr.length() != POSTNR_LENGTH || !isNumeric(postNr)) {
            errors[3] = true;
        }

        // Checks if any input field has errors and highlights
        for (int i = 0; i < inputFields.length; i++) {
            if (errors[i]) {
                inputFields[i].setBackgroundColor(Color.RED);
                correctInput = false;
            }
        }
        return correctInput;
    }

    private static boolean isNumeric(String number) {
        try {
            int integer = Integer.parseInt(number);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
