package com.example.ruben.hobbyhuset;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    SharedPreferences mSettings;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_profile, container, false);

        mSettings = getActivity().getPreferences(Context.MODE_PRIVATE);
        String title = "";
        String lastVistiString = "";
        String runCountString = "";

        // viser profilen vis navn og "husk meg" er satt
        if (mSettings.getBoolean("RememberMe", false) && mSettings.getString("Navn", null) != null) {

            String navn = mSettings.getString("Navn", null);
            String lastVisit = mSettings.getString("lastVisit", "aldri");
            int runCount = mSettings.getInt("runCount", 0);

            title = "Velkommen " + navn + "!";
            lastVistiString = "Ditt siste besøk var " + lastVisit + ".";
            runCountString = "Du har kjørt appen " + runCount + " ganger.";
        } else {
            title = "Sett et navn og huk av \"Husk meg\" i instillinger for å bruke profilen.";
        }

        TextView tvTitle = (TextView) fragment.findViewById(R.id.textView_title);
        tvTitle.setText(title);

        TextView tvLastVisit = (TextView) fragment.findViewById(R.id.textView_lastVisit);
        tvLastVisit.setText(lastVistiString);

        TextView tvRunCount = (TextView) fragment.findViewById(R.id.textView_runCount);
        tvRunCount.setText(runCountString);

        return fragment;
    }

}
