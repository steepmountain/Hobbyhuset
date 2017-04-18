package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateOrdreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateOrdreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateOrdreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Context mActivity;
    EditText[] inputFields;
    TextView tvOrdreDato;
    DatePicker dpOrdreDato;
    TextView tvKundeNr;
    EditText etKundeNr;
    Button btnSubmit;

    private static int VARCHAR_MAX = 256;
    private static int KUNDENR_LENGTH = 4;

    public CreateOrdreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateOrdreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateOrdreFragment newInstance(String param1, String param2) {
        CreateOrdreFragment fragment = new CreateOrdreFragment();
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
        View fragment = inflater.inflate(R.layout.fragment_create_ordre, container, false);
        mActivity = getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ny ordre");

        inputFields = new EditText[1];

        tvOrdreDato = (TextView) fragment.findViewById(R.id.textView_ordreDato);
        tvOrdreDato.setText("Ordredato");

        dpOrdreDato = (DatePicker) fragment.findViewById(R.id.datePicker_ordreDato);
        tvKundeNr = (TextView) fragment.findViewById(R.id.textView_kundeNr);
        tvKundeNr.setText("Kundenummer");

        etKundeNr = (EditText) fragment.findViewById(R.id.editText_kundeNr);
        etKundeNr.setHint("Eksisterende kundenummer");
        inputFields[0] = etKundeNr;

        btnSubmit = (Button) fragment.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewOrdre();
            }
        });


        return fragment;
    }

    private void createNewOrdre() {
        // resets colors
        for (EditText field : inputFields) {
            field.setBackgroundResource(R.drawable.edit_text_box);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String ordreDato = df.format(new Date());
        String kundeNr = etKundeNr.getText().toString();

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

    private boolean checkInput(String kundeNr) {

        boolean[] errors = new boolean[]{false, false};
        boolean correctInput = true;

        if (kundeNr.isEmpty() || kundeNr.length() != KUNDENR_LENGTH || !isNumeric(kundeNr)) {
            errors[0] = true;
        }

        // Checks if any input field has errors and highlights
        for (int i = 0; i < inputFields.length; i++) {
            if (errors[i]) {
                inputFields[i].setBackgroundResource(R.drawable.edit_text_box_error);
                correctInput = false;
            }
        }
        return correctInput;
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
