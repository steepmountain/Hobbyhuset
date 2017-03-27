package com.example.ruben.hobbyhuset;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Bunn-nivå Model-del av MVC. Gjør kall mot REST Api med kall fra Controller
 */

public class RestClient {

    // Class for performing GETS
    static class GetTask extends AsyncTask<String, String, String> {

        private String mRestUrl;
        private RestTaskCallback mCallback;

        GetTask(String restUrl, RestTaskCallback callback) {
            this.mRestUrl = restUrl;
            this.mCallback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;

            HttpURLConnection connection = null;
            try {
                URL kundeListeURL = new URL(strings[0]);
                connection = (HttpURLConnection) kundeListeURL.openConnection();
                connection.connect();
                int status = connection.getResponseCode();

                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder builder = new StringBuilder();
                    while ((response = reader.readLine()) != null) {
                        builder.append(response);
                    }
                    response = builder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert connection != null;
                connection.disconnect();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            mCallback.onTaskComplete(result);
            super.onPostExecute(result);
        }
    }

    // Class for performing POSTs
    class PostTask extends AsyncTask<String, String, String> {

        private String mRestUrl;
        private RestTaskCallback mCallback;
        private String mRequestBody;

        public PostTask(String restUrl, RestTaskCallback callback, String requestBody) {
            this.mRestUrl = restUrl;
            this.mCallback = callback;
            this.mRequestBody = requestBody;
        }

        @Override
        protected String doInBackground(String... strings) {
            // DO POST TASK HERE

            return null;
        }
    }

    class DeleteTask extends AsyncTask<String, String, String> {

        private String mRestUrl;
        private RestTaskCallback mCallback;
        private String mRequestBody;

        public DeleteTask(String restUrl, RestTaskCallback callback, String requestBody) {
            this.mRestUrl = restUrl;
            this.mCallback = callback;
            this.mRequestBody = requestBody;
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

    abstract static class RestTaskCallback {
        public abstract void onTaskComplete(String result);
    }

}
