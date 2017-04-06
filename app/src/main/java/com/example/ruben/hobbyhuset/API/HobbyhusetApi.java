package com.example.ruben.hobbyhuset.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ruben.hobbyhuset.item.Item;
import com.example.ruben.hobbyhuset.vare.Vare;

import org.json.JSONObject;

/**
 * Topp-nivå Controller-del av MVC. Tar input fra bruker og sender kall til RestClient
 */

public class HobbyhusetApi {

    public HobbyhusetApi() {
    }

    // GETS
    // Gets every Kunde in list
    public void getAlleKunder(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde?transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets kunde by kundeNr
    public void getKunde(GetResponseCallback callback, int kundeNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde?filter=kNr,eq,"+ kundeNr +"&transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    // gets every Ordre in list
    public void getAlleOrdre(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordre?transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    public void getOrdreFraKunde(GetResponseCallback callback, int kundeNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordre?filter=kNr,eq,"+ kundeNr +"&transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    public void getAlleVarer(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare?transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    public void getOrdrelinje(GetResponseCallback callback, int ordreNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordrelinje?filter=OrdreNr,eq," + ordreNr + "&transform=1";
        doExecuteGetCall(callback, restUrl);
    }

    public void getVare(GetResponseCallback callback, int vareNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/" + vareNr;
        doExecuteGetCall(callback, restUrl);
    }


    // UPDATES
    public void updateVare(GetResponseCallback callback, Vare v) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/" + v.getVareNr();
        JSONObject updateJSON = v.toJSON();
        doExecuteUpdateCall(callback, restUrl, updateJSON);
    }


    // INSERTS
    public void insertVare(GetResponseCallback callback, Vare v) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare";
        doExecuteInsertCall(callback, restUrl, v);
    }

    // DELETES
    public void deleteVare(GetResponseCallback callback, int vareNr) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Vare/ " + vareNr;
    }

    private void doExecuteGetCall(final GetResponseCallback callback, String restUrl) {
        new RestClient.GetTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute(restUrl);
    }

    private void doExecuteUpdateCall(final GetResponseCallback callback, String restUrl, JSONObject updateJSON) {
        new RestClient.UpdateTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }, updateJSON).execute(restUrl);
    }

    private void doExecuteInsertCall(final GetResponseCallback callback, String restUrl, Item item) {
        new RestClient.InsertTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }, item).execute(restUrl);
    }

    private void doExecuteDeleteCall(final GetResponseCallback callback, String restUrl) {
        new RestClient.DeleteTask(restUrl, new RestClient.RestTaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute(restUrl);
    }
}

abstract class PostCallback {
    /**
     * Called when a POST success response is received. <br/>
     * This method is guaranteed to execute on the UI thread.
     */
    public abstract void onPostSuccess();

}

