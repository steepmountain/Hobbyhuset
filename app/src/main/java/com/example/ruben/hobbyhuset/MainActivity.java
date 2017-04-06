package com.example.ruben.hobbyhuset;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ruben.hobbyhuset.kunde.KundeFragment;
import com.example.ruben.hobbyhuset.ordre.OrdreFragment;
import com.example.ruben.hobbyhuset.vare.VareFragment;


public class MainActivity
        extends AppCompatActivity implements KundeFragment.OnFragmentInteractionListener, OrdreFragment.OnFragmentInteractionListener, VareFragment.OnFragmentInteractionListener {

    // Priority 1: Essential functionality
    // TODO: EditKundeActivity, EditOrdreActivity, EditVareActivity | VÆRE GENERELT FRAGMENT?
    // TODO: SlettItemActivity / SlettKundeActivity, LettOrdreActivity, SlettVareActivity | VÆRE GENERELT FRAGMENT?
    // TODO: NyItemActivity / NyKundeActivity, NyOrdreActivity, NyVareActivity

    // Priority 2: Required additions
    // TODO: Activities and fragments need proper titles
    // TODO: All fields needs a name, format= "FieldName: FieldValue";
    // TODO: Preference manager to remember login
    // TODO: Settings menu
    // TODO: Navigation drawer to select fragments

    // Priority 3: Has to wait until functionality is there
    // TODO: Polish GUI
    // TODO: Landscape orientation

    // Priority 4: Wait until everything is done
    // TODO: Clean up imports
    // TODO: Comment everything

    private static final int REQUEST_CODE_INTERNET = 1;
    private static final int REQUEST_CODE_ACCESS_NETWORK_STATE = 2;

    public static final int KUNDE_CODE = 0;
    public static final int ORDRE_CODE = 1;
    public static final int VARE_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checks if permissions are granted, no callback as the whole app fails if it doesn't have these permissions
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)) {
            // asks for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE_ACCESS_NETWORK_STATE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_INTERNET);
        }

        // Starts KundeFragment as default fragment when the app launches
        startFragment(R.id.action_kunde);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startFragment(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    /// private method to launch a fragment based on the fragment ID
    private void startFragment(int id) {
        Fragment fragment = null;
        FragmentManager fm;
        FragmentTransaction transaction;

        switch (id) {
            case R.id.action_kunde:
                fragment = new KundeFragment();
                break;
            case R.id.action_ordre:
                fragment = new OrdreFragment();
                break;
            case R.id.action_vare:
                fragment = new VareFragment();
                break;
        }

        if (fragment != null) {
            fm = getSupportFragmentManager();
            transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();
        }
        else {
            Toast.makeText(this, "Failed to start fragment", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
