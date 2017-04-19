package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MyPreferenceFragment extends PreferenceFragmentCompat {

    private EditTextPreference mNavnPreference;
    private SharedPreferences mSettings;
    private SwitchPreference mRememberMe;

    public MyPreferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        mSettings = getActivity().getPreferences(Context.MODE_PRIVATE);

        // Name setting
        mNavnPreference = (EditTextPreference) findPreference("navn");
        mNavnPreference.setSummary(mSettings.getString("Navn", null));
        mNavnPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor edit = mSettings.edit();
                edit.putString("Navn", (String) newValue);
                edit.commit();
                mNavnPreference.setSummary((String) newValue);
                return false;
            }
        });

        // remember me setting
        boolean rememeberMe = mSettings.getBoolean("RememberMe", false);
        mRememberMe = (SwitchPreference) findPreference("switch_pref");
        mRememberMe.setDefaultValue(rememeberMe);
        mRememberMe.setSummary(rememeberMe == false ? "Disabled" : "Enabled");

        mRememberMe.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference,
                                              Object newValue) {
                boolean switched = ((SwitchPreference) preference)
                        .isChecked();

                SharedPreferences.Editor edit = mSettings.edit();
                edit.putBoolean("RememberMe", (boolean) newValue);
                edit.commit();
                mRememberMe.setSummary((boolean) newValue == false ? "Disabled" : "Enabled");

                return true;
            }

        });

    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

}
