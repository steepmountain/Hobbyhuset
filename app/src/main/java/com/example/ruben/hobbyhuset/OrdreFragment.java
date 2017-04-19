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

import java.text.ParseException;
import java.util.ArrayList;
/*
 * FRagment to show a listview of Ordre given a Ordre Arraylist
 */
public class OrdreFragment extends Fragment {

    ListView mOrdreListView;
    ArrayList<Ordre> mOrdreListe = new ArrayList<>();
    OrdreAdapter mAdapter;

    public OrdreFragment() {
        // Required empty public constructor
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

        mOrdreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ReadOrdreActivity.class);
                intent.putExtra("Source", MainActivity.ORDRE_CODE);
                intent.putExtra("Ordre", mOrdreListe.get(i));
                startActivity(intent);
            }
        });

        mAdapter.notifyDataSetChanged();
    }


    // Checks if the device is online

}
