package com.example.ruben.hobbyhuset;

import java.util.ArrayList;

/**
 * Topp-nivå Controller-del av MVC. Tar input fra bruker og sender kall til RestClient
 */

public class HobbyhusetApi {

    public HobbyhusetApi() {
    }

    // Gets every Kunde in list
    public void getAlleKunder(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde?transform=1";
        doExecuteCall(callback, restUrl);
    }

    // gets kunde by kundeNr
    public void getKunde(GetResponseCallback callback, int kundeNr) {
        final String restUrl = "http://itfag.usn.no/~141175/api.php/Kunde?filter=kundeNr,eq,"+ kundeNr +"&transform=1";
        doExecuteCall(callback, restUrl);
    }

    // gets every Ordre in list
    public void getAlleOrdre(GetResponseCallback callback) {
        String restUrl = "http://itfag.usn.no/~141175/api.php/Ordre?transform=1";
        doExecuteCall(callback, restUrl);
    }

    private void doExecuteCall(final GetResponseCallback callback, String restUrl) {
        new RestClient.GetTask(restUrl, new RestClient.RestTaskCallback() {
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

abstract class PostCallback {
    /**
     * Called when a POST success response is received. <br/>
     * This method is guaranteed to execute on the UI thread.
     */
    public abstract void onPostSuccess();

}