package com.example.ruben.hobbyhuset;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity
        extends AppCompatActivity
        implements KundeFragment.OnFragmentInteractionListener,
        OrdreFragment.OnFragmentInteractionListener,
        VareFragment.OnFragmentInteractionListener,
        MyPreferenceFragment.OnFragmentInteractionListener {

    // TODO: ny kunde burde være knapp i main liste

    // Priority 2: Required additions
    // TODO: Activities and fragments need proper titles
    // TODO: All fields needs a name, format= "FieldName: FieldValue";
    // TODO: Preference manager to remember login
    // TODO: Navigation drawer to select fragments

    // Priority 3: Has to wait until functionality is there
    // TODO: Polish GUI
    // TODO: Landscape orientation

    // Priority 4: Wait until everything is done
    // TODO: Clean up imports
    // TODO: Comment everything

    private static String[] fragmentTitles = new String[]{"Kunde", "Ordre", "Vare", "Instillinger"};

    private static final int REQUEST_CODE_INTERNET = 1;
    private static final int REQUEST_CODE_ACCESS_NETWORK_STATE = 2;

    public static final int KUNDE_CODE = 0;
    public static final int ORDRE_CODE = 1;
    public static final int VARE_CODE = 2;
    public static final int INSTILLINGER_CODE = 3;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checks if permissions are granted, no callback as the whole app fails if it doesn't have these permissions
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)) {
            // asks for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE_ACCESS_NETWORK_STATE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, REQUEST_CODE_INTERNET);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, fragmentTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // Starts KundeFragment as default fragment when the app launches
        startFragment(KUNDE_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /// private method to launch a fragment based on the fragment ID
    public void startFragment(int id) {
        Fragment fragment = null;
        FragmentManager fm;
        FragmentTransaction transaction;

        switch (id) {
            case KUNDE_CODE:
                fragment = new KundeFragment();
                break;
            case ORDRE_CODE:
                fragment = new OrdreFragment();
                break;
            case VARE_CODE:
                fragment = new VareFragment();
                break;
            case INSTILLINGER_CODE:
                fragment = new MyPreferenceFragment();
                break;
        }

        if (fragment != null) {
            fm = getSupportFragmentManager();
            transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, fragment);
            transaction.commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(id, true);
            setTitle(fragmentTitles[id]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Toast.makeText(this, "Failed to start fragment", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            startFragment(i);
        }
    }
}
