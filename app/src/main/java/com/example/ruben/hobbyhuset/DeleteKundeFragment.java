package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeleteKundeFragment extends Fragment {

    public DeleteKundeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Attempts to delete the sent Kunde
        NetworkHelper helper = new NetworkHelper(getContext());
        if (helper.isOnline()) {
            HobbyhusetApi api = new HobbyhusetApi();
            api.deleteKunde(new GetResponseCallback() {
                @Override
                void onDataReceived(String item) {
                    Log.d("PostDelete", item.toString());
                }
            }, getArguments().getInt("KundeNr"));
        }
        return inflater.inflate(R.layout.fragment_delete_kunde, container, false);
    }

}
