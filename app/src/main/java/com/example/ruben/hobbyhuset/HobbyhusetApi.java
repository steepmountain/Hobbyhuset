package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Topp-nivå Controller-del av MVC. Tar input fra bruker og sender kall til RestClient
 */

public class HobbyhusetApi {

    public HobbyhusetApi() {
    }

    /*
      * GET-methods
     */

    // Gets every Kunde in list
    public void getAlleKunder(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde?transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets kunde by kundeNr
    public void getKunde(GetResponseCallback callback, int kundeNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde?filter=kNr,eq," + kundeNr + "&transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets every Ordre in list
    public void getAlleOrdre(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordre?transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets all Ordre from a kundeNr
    public void getOrdreFraKunde(GetResponseCallback callback, int kundeNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordre?filter=kNr,eq," + kundeNr + "&transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets all Varer
    public void getAlleVarer(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare?transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // Get all Ordrelinje for a ordreNr
    public void getOrdrelinje(GetResponseCallback callback, int ordreNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordrelinje?filter=OrdreNr,eq," + ordreNr + "&transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets vare from a varenr
    public void getVare(GetResponseCallback callback, int vareNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/" + vareNr;
        doExecuteGetCall(callback, restUrl);
    }


    /*
     * UPDATE-methods
     */

    // Updates a vare object with new data
    public void updateVare(GetResponseCallback callback, Vare v) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/" + v.getVareNr();
        JSONObject updateJSON = v.toJSON();
        doExecuteUpdateCall(callback, restUrl, updateJSON);
    }

    // updates a kunde object with new data
    public void updateKunde(GetResponseCallback callback, Kunde k) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde/" + k.getKundeNr();
        JSONObject updateJSON = k.toJSON();
        doExecuteUpdateCall(callback, restUrl, updateJSON);
    }


    /*
     * INSERT-methods
     */
    // Inserts new vare
    public void insertVare(GetResponseCallback callback, Vare v) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/";
        doExecuteInsertCall(callback, restUrl, v);
    }

    // Inserts new kunde
    public void insertKunde(GetResponseCallback callback, Kunde k) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde/";
        doExecuteInsertCall(callback, restUrl, k);
    }

    // Inserts new ordre
    public void insertOrdre(GetResponseCallback callback, Ordre o) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordre/";
        doExecuteInsertCall(callback, restUrl, o);
    }

    /*
     * DELETE-methods
     */

    // deletes vare given its varenr
    public void deleteVare(GetResponseCallback callback, int vareNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/ " + vareNr;
        doExecuteDeleteCall(callback, restUrl);
    }

    // deletes kunde given its kundenr
    public void deleteKunde(GetResponseCallback callback, int kundeNr) {
        String restUrl = "http//itfag.usn.no/~141175/api.php/Kunde/ " + kundeNr;
        doExecuteDeleteCall(callback, restUrl);
    }

    /*
     * EXECUTE-methods for each CRUD-method
     */

    // does a get-call given an URL
    private void doExecuteGetCall(final GetResponseCallback callback, String restUrl) {
        new RestClient.GetTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute(restUrl);
    }

    // does an update-call given a URL and a new JSON item
    private void doExecuteUpdateCall(final GetResponseCallback callback, String restUrl, JSONObject updateJSON) {
        new RestClient.UpdateTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }, updateJSON).execute(restUrl);
    }

    // does an insert-call given a url and an item to be updated
    private void doExecuteInsertCall(final GetResponseCallback callback, String restUrl, Item item) {
        new RestClient.InsertTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }, item).execute(restUrl);
    }

    // does an executec call given a URL
    private void doExecuteDeleteCall(final GetResponseCallback callback, String restUrl) {
        new RestClient.DeleteTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute(restUrl);
    }
}

abstract class GetResponseCallback {

    abstract void onDataReceived(String item);

}

// Inner class to help API determine network status
class NetworkHelper {

    Context mContext;

    public NetworkHelper(Context context) {
        mContext = context;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}