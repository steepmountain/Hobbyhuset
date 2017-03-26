package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KundeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KundeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KundeFragment extends Fragment {

    String DEBUG = "KundeFragment";
    ListView mKundeListView;
    ArrayList<Kunde> mKundeListe = new ArrayList<>();
    KundeAdapter mAdapter;
    String URLString = "http://itfag.usn.no/~141175/api.php/Kunde?transform=1";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public KundeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KundeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KundeFragment newInstance(String param1, String param2) {
        KundeFragment fragment = new KundeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_kunde, container, false);
        mKundeListView = (ListView) fragment.findViewById(R.id.listView_kunde);

        if (isOnline()) {
            KundeListeGetter getter = new KundeListeGetter();
            getter.execute();
        }
        else {
            Toast.makeText(getActivity(), "Ingen nettverkstilgang!", Toast.LENGTH_SHORT).show();
        }
        oppdaterKundeListe(mKundeListe);


        // Inflate the layout for this fragment
        return fragment;
    }

    public void oppdaterKundeListe(ArrayList<Kunde> nyKundeListe) {

        mAdapter = new KundeAdapter(getActivity(), nyKundeListe);
        mKundeListView.setAdapter(mAdapter);

        mKundeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowItemActivity.class);
                Log.d("KundeFragment", mKundeListe.get(i).toString());
                intent.putExtra("Kunde", mKundeListe.get(i));
                startActivity(intent);
            }
        });

        mAdapter.notifyDataSetChanged();
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private class KundeListeGetter extends AsyncTask<String , String, Long> {

        @Override
        protected Long doInBackground(String... params) {

            HttpURLConnection connection = null;
            try {
                URL kundeListeURL = new URL(URLString);
                connection = (HttpURLConnection) kundeListeURL.openConnection();
                connection.connect();
                int status = connection.getResponseCode();

                if (status == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String response;
                    StringBuilder builder = new StringBuilder();
                    while ((response = reader.readLine()) != null) {
                        builder.append(response);
                    }
                    mKundeListe = Kunde.lagKundeListe(builder.toString());
                    return (0l);
                }
                else {
                    return (1l);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return (1l);
            } catch (IOException e) {
                e.printStackTrace();
                return (1l);
            } catch (JSONException e) {
                e.printStackTrace();
                return (1l);
            }
            finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result == 0) {
                oppdaterKundeListe(mKundeListe);
            }
            else {
                Toast.makeText(getActivity(), "Noe gikk galt!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
