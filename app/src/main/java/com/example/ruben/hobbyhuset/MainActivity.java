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


public class MainActivity
        extends AppCompatActivity implements KundeFragment.OnFragmentInteractionListener { //, OrdreFragment.OnFragmentInteractionListener, VareFragment.OnFragmentInteractionListener {

    // TODO: Preference manager to remember login
    // TODO: Navigation drawer to select fragment

    // TODO: split ShowItemActivity into fragments for each datatype?

    // TODO: Make into JSON objects much earlier than currently?
    // TODO: Api is called and returns info in a long String ->
    // TODO: OnDataReceived sends String to lagKundeListe ->
    // TODO: lagKundeListe turns the String into a JSON Array and turns that into an ArrayList<>

    private static final int REQUEST_CODE_PERMISSION = 2;

    public static final int KUNDE_CODE = 0;
    public static final int ORDRE_CODE = 1;
    public static final int VARE_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checks if permissions are granted
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED)) {
            // asks for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET}, REQUEST_CODE_PERMISSION);
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

        if (id == R.id.action_kunde) {
            fragment = new KundeFragment();
        }
        /*
        else if (id == R.id.action_ordre) {
            fragment = new OrdreFragment();
        }
        else if (id == R.id.action_vare) {
            fragment = new VareFragment();
        }
        */

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
