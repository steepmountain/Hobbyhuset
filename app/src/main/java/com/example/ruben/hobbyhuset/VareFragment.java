package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VareFragment extends Fragment {

    ListView mVareListView;
    ArrayList<Vare> mVareListe = new ArrayList<>();
    ArrayList<Integer> mVareNrListe = new ArrayList<>();
    VareAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VareFragment newInstance(String param1, String param2) {
        VareFragment fragment = new VareFragment();
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

        View fragment = inflater.inflate(R.layout.fragment_vare, container, false);
        mVareListView = (ListView) fragment.findViewById(R.id.listView_vare);
        // If the device is online, make a call to the API and request a list of every Kunde
        NetworkHelper helper = new NetworkHelper(getActivity());
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();

            Bundle args = this.getArguments();

            // Branches if bundle contains argument
            if (args != null) {
                int ordreNr = args.getInt("OrdreNr"); // TODO: check for null value

                // Updates the list of VareNr based on the OrdreNr contained in bundle
                api.getOrdrelinje(new GetResponseCallback() { // virker
                    @Override
                    void onDataReceived(String item) {
                        try {
                            mVareNrListe = Ordre.lagVareNrListe(item);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, ordreNr);

                // Uses the list of VareNr to create a list of Vare
                for (int i = 0; i < mVareNrListe.size(); i++) {

                    api.getVare(new GetResponseCallback() {
                        @Override
                        void onDataReceived(String item) {
                            try {
                                oppdaterVareListe(mVareListe = Vare.lagVareListe(item));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, mVareNrListe.get(i));
                }

            }

            // If bundle contains no arguemnts, display all Vare
            else {
                api.getAlleVarer(new GetResponseCallback() {
                    @Override
                    void onDataReceived(String item) {
                        try {
                            oppdaterVareListe(mVareListe = Vare.lagVareListe(item));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });
            }

        } else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }

        // Inflate the layout for this fragment
        return fragment;
    }

    // Updates the ListView with a new ArrayList of Kunde
    public void oppdaterVareListe(ArrayList<Vare> nyVareListe) {

        mAdapter = new VareAdapter(getActivity(), nyVareListe);
        mVareListView.setAdapter(mAdapter);

        mVareListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowVareActivity.class);
                intent.putExtra("Source", MainActivity.VARE_CODE);
                intent.putExtra("Vare", mVareListe.get(i));
                startActivity(intent);
            }
        });

        mAdapter.notifyDataSetChanged();
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
