package com.example.ruben.hobbyhuset;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Bunn-nivå Model-del av MVC. Gjør kall mot REST Api med kall fra Controller
 */

public class RestClient {

    static String DELETE = "DELETE";
    static String PUT = "PUT";
    static String POST = "POST";

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
                URL kundeListeURL = new URL(mRestUrl);
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

    // Class for performing DELETE
    static class DeleteTask extends AsyncTask<String, String, String> {

        private String mRestUrl;
        private RestTaskCallback mCallback;

        public DeleteTask(String restUrl, RestTaskCallback callback) {
            this.mRestUrl = restUrl;
            this.mCallback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;

            HttpURLConnection connection = null;
            try {
                URL deleteUrl = new URL(mRestUrl);
                connection = (HttpURLConnection) deleteUrl.openConnection();
                connection.setDoOutput(false);
                connection.setDoInput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod(DELETE);
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

    // Class for performing UPDATE
    static class UpdateTask extends AsyncTask<String, String, String> {

        private String mRestUrl;
        private RestTaskCallback mCallback;
        private JSONObject mUpdateJSON;

        public UpdateTask(String restUrl, RestTaskCallback callback, JSONObject updateJSON) {
            this.mRestUrl = restUrl;
            this.mCallback = callback;
            this.mUpdateJSON = updateJSON;
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;
            HttpURLConnection connection = null;
            try {
                URL updateUrl = new URL(mRestUrl);
                connection = (HttpURLConnection) updateUrl.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod(PUT);
                connection.setRequestProperty("Content-Type","application/json");
                connection.connect();

                JSONObject jsonItem = mUpdateJSON;
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonItem.toString());
                out.close();

                int status = connection.getResponseCode();
                Log.d("JSON", updateUrl + "" + jsonItem);
                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder builder = new StringBuilder();
                    while ((response = reader.readLine()) != null) {
                        builder.append(response);
                    }
                    response = builder.toString();
                    Log.d("Builder", builder.toString());
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

    // Class for performing INSERT
    static class InsertTask extends AsyncTask<String, String, String> {

        private String mRestUrl;
        private RestTaskCallback mCallback;
        private Item mItem;

        public InsertTask(String restUrl, RestTaskCallback callback, Item item) {
            this.mRestUrl = restUrl;
            this.mCallback = callback;
            this.mItem = item;
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = null;

            HttpURLConnection connection = null;
            try {
                URL deleteUrl = new URL(mRestUrl);
                connection = (HttpURLConnection) deleteUrl.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setChunkedStreamingMode(0);
                connection.setRequestMethod(POST);
                connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                connection.connect();

                JSONObject jsonItem = mItem.toJSON();
                Log.d("RestClient", jsonItem.toString());
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonItem.toString());
                out.close();
                Log.d("JSONObject", jsonItem.toString());

                int status = connection.getResponseCode();

                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder builder = new StringBuilder();
                    while ((response = reader.readLine()) != null) {
                        builder.append(response);
                    }
                    response = builder.toString();
                    Log.d("Response", builder.toString());
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

    abstract static class RestTaskCallback {
        public abstract void onTaskComplete(String result);
    }

}
