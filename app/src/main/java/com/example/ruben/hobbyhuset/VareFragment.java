package com.example.ruben.hobbyhuset;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VareFragment extends Fragment {

    ListView mVareListView;
    ArrayList<Vare> mVareListe = new ArrayList<>();
    ArrayList<Integer> mVareNrListe = new ArrayList<>();
    VareAdapter mAdapter;

    public VareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_vare, container, false);
        mVareListView = (ListView) fragment.findViewById(R.id.listView_vare);
        // If the device is online, make a call to the API and request a list of every Kunde
        NetworkHelper helper = new NetworkHelper(getActivity());
        if (helper.isOnline()) {
            final HobbyhusetApi api = new HobbyhusetApi();

            Bundle args = this.getArguments();

            // Branches if bundle contains argument
            if (args != null) {
                int ordreNr = args.getInt("OrdreNr"); // TODO: check for null value

                // Updates the list of VareNr based on the OrdreNr contained in bundle
                api.getOrdrelinje(new GetResponseCallback() {
                    @Override
                    void onDataReceived(String item) {
                        try {
                            mVareNrListe = Ordre.lagVareNrListe(item);

                            // Fetches each item from ORdrelinje and adds to mVareListe
                            for (int i = 0; i < mVareNrListe.size(); i++) {
                                api.getVare(new GetResponseCallback() {
                                    @Override
                                    void onDataReceived(String item) {
                                        try {
                                            Vare v = new Vare(new JSONObject(item));
                                            mVareListe.add(v);
                                            oppdaterVareListe(mVareListe);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, mVareNrListe.get(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, ordreNr);
                args.clear();
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
                Intent intent = new Intent(getActivity(), ReadVareActivity.class);
                intent.putExtra("Source", MainActivity.VARE_CODE);
                intent.putExtra("Vare", mVareListe.get(i));
                startActivity(intent);
            }
        });

        mAdapter.notifyDataSetChanged();
    }

}
