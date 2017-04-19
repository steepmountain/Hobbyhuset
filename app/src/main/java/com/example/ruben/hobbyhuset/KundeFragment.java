package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

/*
 * Fragment to show a ListView of aN ArrayList of Kunder
 */
public class KundeFragment extends Fragment {

    ListView mKundeListView;
    ArrayList<Kunde> mKundeListe = new ArrayList<>();
    KundeAdapter mAdapter;

    public KundeFragment() {
        // Required empty public constructor
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
                Intent intent = new Intent(getActivity(), ReadKundeActivity.class);
                intent.putExtra("Source", MainActivity.KUNDE_CODE);
                intent.putExtra("Kunde", mKundeListe.get(i));
                startActivity(intent);
            }
        });

        mAdapter.notifyDataSetChanged();
    }
}
