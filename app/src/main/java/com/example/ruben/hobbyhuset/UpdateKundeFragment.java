package com.example.ruben.hobbyhuset;

import android.app.Activity;
import android.content.Context;
import android.net.Network;
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
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateKundeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateKundeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateKundeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Kunde currentKunde;

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

    public UpdateKundeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateKundeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateKundeFragment newInstance(String param1, String param2) {
        UpdateKundeFragment fragment = new UpdateKundeFragment();
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

        currentKunde = getArguments().getParcelable("Kunde");

        mActivity = getActivity();
        inputFields = new EditText[4];

        // Inits label and edittext for fornavn
        tvFornavnLabel = (TextView) fragment.findViewById(R.id.textView_fornavnLabel);
        tvFornavnLabel.setText("Fornavn");
        etFornavn = (EditText) fragment.findViewById(R.id.editText_fornavn);
        etFornavn.setText(currentKunde.getFornavn());
        inputFields[0] = (EditText) etFornavn;

        tvEtternavnLabel = (TextView) fragment.findViewById(R.id.textView_etternavnLabel);
        tvEtternavnLabel.setText("Etternavn");
        etEtternavn = (EditText) fragment.findViewById(R.id.editText_etternavn);
        etEtternavn.setText(currentKunde.getEtternavn());
        inputFields[1] = (EditText) etEtternavn;

        tvAdresseLabel = (TextView) fragment.findViewById(R.id.textView_adresseLabel);
        tvAdresseLabel.setText("Adresse");
        etAdresse = (EditText) fragment.findViewById(R.id.editText_adresse);
        etAdresse.setText(currentKunde.getAdresse());
        inputFields[2] = (EditText) etAdresse;

        tvPostNrLabel = (TextView) fragment.findViewById(R.id.textView_postNrLabel);
        tvPostNrLabel.setText("PostNr");
        etPostNr = (EditText) fragment.findViewById(R.id.editText_postNr);
        etPostNr.setText(currentKunde.getPostNr() + "");
        inputFields[3] = (EditText) etPostNr;

        btnSubmit = (Button) fragment.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateKunde();
            }
        });
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void setCurrentKunde(String kundeString) {
        try {
            this.currentKunde = new Kunde(new JSONObject(kundeString));
        } catch (JSONException e) {
            e.printStackTrace();
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
