package com.example.ruben.hobbyhuset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Gets current fragment and disables the button for that fragment
    int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Starts KundeFragment as default fragment
        currentFragment = R.id.action_kunde;
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

    // private method to launch a fragment based on the fragment ID|
    private void startFragment(int id) {
        Fragment fragment = null;
        FragmentManager fm;
        FragmentTransaction transaction;

        if (id == R.id.action_kunde) {
            fragment = new KundeFragment();
        }
        else if (id == R.id.action_ordre) {
            fragment = new OrdreFragment();
        }
        else if (id == R.id.action_vare) {
            fragment = new VareFragment();
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
}
