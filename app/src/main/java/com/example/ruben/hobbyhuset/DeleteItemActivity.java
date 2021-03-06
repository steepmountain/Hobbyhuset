package com.example.ruben.hobbyhuset;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/*
 * Activity hold DeleteItem fragments
 */
public class DeleteItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Checks incoming intent
        Intent intent = getIntent();
        if (intent != null) {

            // checks which class sent the deleteItem request
            int origin = intent.getIntExtra("Source", -1);
            switch (origin) {

                case MainActivity.KUNDE_CODE: {
                    Bundle bundle = new Bundle();
                    bundle.putInt("KundeNr", intent.getIntExtra("KundeNr", -1));
                    Fragment fragment = new DeleteKundeFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, fragment);
                    transaction.commit();
                    break;
                }
/*
                case MainActivity.ORDRE_CODE: {
                    Fragment fragment = new NewOrdreFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, fragment);
                    transaction.commit();
                    break;
                }

                case MainActivity.VARE_CODE: {
                    Fragment fragment = new NewVareFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, fragment);
                    transaction.commit();
                    break;
                }*/

                default: {
                    Toast.makeText(this, "Invalid origin.", Toast.LENGTH_LONG);
                }
            }
        } else {
            Toast.makeText(this, "Noe gikk galt med sendingen av data", Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
