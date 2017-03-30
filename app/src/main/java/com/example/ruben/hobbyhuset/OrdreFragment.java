package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KundeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KundeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdreFragment extends Fragment {

    ListView mOrdreListView;
    ArrayList<Ordre> mOrdreListe = new ArrayList<>();
    OrdreAdapter mAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KundeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdreFragment newInstance(String param1, String param2) {
        OrdreFragment fragment = new OrdreFragment();
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

        View fragment = inflater.inflate(R.layout.fragment_ordre, container, false);
        mOrdreListView = (ListView) fragment.findViewById(R.id.listView_ordre);

        // If the device is online, make a call to the API and request a list of every Kunde
        NetworkHelper helper = new NetworkHelper(getActivity());
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();

            // Checks if the fragment launched with a bundle
            Bundle args = this.getArguments();
            if (args != null) {
                int kundeNr = args.getInt("KundeNr");
                if(kundeNr > 0) {
                    api.getOrdreFraKunde(new GetResponseCallback() {
                        @Override
                        void onDataReceived(String item) {
                            try {
                                oppdaterOrdreListe(mOrdreListe = Ordre.lagOrdreListe(item));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, kundeNr);
                }
                args.clear();
            }


            // If bundle is empty, show all ordre
            else {
                api.getAlleOrdre(new GetResponseCallback() {
                    @Override
                    void onDataReceived(String item) {
                        try {
                            oppdaterOrdreListe(mOrdreListe = Ordre.lagOrdreListe(item));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }

        // Inflate the layout for this fragment
        return fragment;
    }

    // Updates the ListView with a new ArrayList of Kunde
    public void oppdaterOrdreListe(ArrayList<Ordre> nyOrdreListe) {

        mAdapter = new OrdreAdapter(getActivity(), nyOrdreListe);
        mOrdreListView.setAdapter(mAdapter);

        /*
        mOrdreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowOrdreActivity.class);
                intent.putExtra("Source", MainActivity.ORDRE_CODE);
                intent.putExtra("Ordre", mOrdreListe.get(i));
                startActivity(intent);
            }
        });
        */

        mAdapter.notifyDataSetChanged();
    }


    // Checks if the device is online


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
