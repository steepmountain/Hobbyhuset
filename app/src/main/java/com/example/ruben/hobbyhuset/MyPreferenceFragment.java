package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPreferenceFragment extends PreferenceFragmentCompat {

    private EditTextPreference mNavnPreference;
    private SharedPreferences mSettings;

    public MyPreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        mSettings = getActivity().getSharedPreferences("Navn", 0);
        mNavnPreference = (EditTextPreference) findPreference("navn");
        mNavnPreference.setDefaultValue(mSettings);
        mNavnPreference.setSummary(mSettings.getString("Navn", null));
        mNavnPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor edit = mSettings.edit();
                edit.putString("Navn", (String) newValue);
                edit.commit();
                return false;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
