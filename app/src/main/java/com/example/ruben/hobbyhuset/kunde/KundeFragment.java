package com.example.ruben.hobbyhuset.kunde;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruben.hobbyhuset.API.HobbyhusetApi;
import com.example.ruben.hobbyhuset.MainActivity;
import com.example.ruben.hobbyhuset.API.*;
import com.example.ruben.hobbyhuset.R;

import org.json.JSONException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KundeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KundeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KundeFragment extends Fragment {

    ListView mKundeListView;
    ArrayList<Kunde> mKundeListe = new ArrayList<>();
    KundeAdapter mAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public KundeFragment() {
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
    public static KundeFragment newInstance(String param1, String param2) {
        KundeFragment fragment = new KundeFragment();
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

        View fragment = inflater.inflate(R.layout.fragment_kunde, container, false);
        mKundeListView = (ListView) fragment.findViewById(R.id.listView_kunde);

        // If the device is online, make a call to the API and request a list of every Kunde
        NetworkHelper helper = new NetworkHelper(getActivity());
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();
            api.getAlleKunder(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    try {
                        oppdaterKundeListe(mKundeListe = Kunde.lagKundeListe(item));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            });
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }

        // Inflate the layout for this fragment
        return fragment;
    }

    // Updates the ListView with a new ArrayList of Kunde
    public void oppdaterKundeListe(ArrayList<Kunde> nyKundeListe) {

        mAdapter = new KundeAdapter(getActivity(), nyKundeListe);
        mKundeListView.setAdapter(mAdapter);

        mKundeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowKundeActivity.class);
                intent.putExtra("Source", MainActivity.KUNDE_CODE);
                intent.putExtra("Kunde", mKundeListe.get(i));
                startActivity(intent);
            }
        });

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