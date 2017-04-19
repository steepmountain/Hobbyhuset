package com.example.ruben.hobbyhuset;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Ruben on 23.03.2017.
 */

public class Kunde extends Item implements Parcelable {

    int kundeNr;
    String fornavn;
    String etternavn;
    String adresse;
    String postNr;

    public int getKundeNr() {
        return kundeNr;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPostNr() {
        return postNr;
    }

    static final String TABELL_NAVN = "Kunde";
    static final String KOL_NAVN_KNR  = "KNr";
    static final String KOL_NAVN_FORNAVN = "Fornavn";
    static final String KOL_NAVN_ETTERNAVN = "Etternavn";
    static final String KOL_NAVN_ADRESSE = "Adresse";
    static final String KOL_NAVN_POSTNR = "PostNr";


    public Kunde(int kundeNr, String fornavn, String etternavn, String adresse, String postNr) {
        this.kundeNr = kundeNr;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.adresse = adresse;
        this.postNr = postNr;
    }

    public Kunde(String fornavn, String etternavn, String adresse, String postNr) {
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.adresse = adresse;
        this.postNr = postNr;
    }

    public Kunde() {
    }

    public Kunde(JSONObject jsonKunde) {
        this.kundeNr = jsonKunde.optInt(KOL_NAVN_KNR);
        this.fornavn = jsonKunde.optString(KOL_NAVN_FORNAVN);
        this.etternavn = jsonKunde.optString(KOL_NAVN_ETTERNAVN);
        this.adresse = jsonKunde.optString(KOL_NAVN_ADRESSE);
        this.postNr = jsonKunde.optString(KOL_NAVN_POSTNR);
    }

    public static ArrayList<Kunde> lagKundeListe(String jsonString) throws JSONException, NullPointerException {
        ArrayList<Kunde> KundeListe = new ArrayList<Kunde>();
        JSONObject jsonData = new JSONObject(jsonString);
        JSONArray jsonKundeTabell = jsonData.optJSONArray(TABELL_NAVN);
        for(int i = 0; i < jsonKundeTabell.length(); i++) {
            JSONObject jsonKunde = (JSONObject) jsonKundeTabell.get(i);
            Kunde kunde = new Kunde(jsonKunde);
            KundeListe.add(kunde);
        }
        return KundeListe;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "kundeNr=" + kundeNr +
                ", fornavn='" + fornavn + '\'' +
                ", etternavn='" + etternavn + '\'' +
                ", adresse='" + adresse + '\'' +
                ", postNr=" + postNr +
                '}';
    }

    /*
     * Methods to make the object parcelable
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getKundeNr());
        parcel.writeString(getFornavn());
        parcel.writeString(getEtternavn());
        parcel.writeString(getAdresse());
        parcel.writeString(getPostNr());
    }

    // Constructor to create Kunde from parcel based on FIFO from writeToParcel
    private Kunde(Parcel in) {
        this.kundeNr = in.readInt();
        this.fornavn = in.readString();
        this.etternavn = in.readString();
        this.adresse = in.readString();
        this.postNr = in.readString();
    }

    public static final Parcelable.Creator<Kunde> CREATOR = new Parcelable.Creator<Kunde>() {
        public Kunde createFromParcel(Parcel in) {
            return new Kunde(in);
        }

        public Kunde[] newArray(int size) {
            return new Kunde[size];
        }
    };

    @Override
    JSONObject toJSON() {
        JSONObject jsonItem = new JSONObject();
        try {
            jsonItem.put(KOL_NAVN_KNR, getKundeNr());
            jsonItem.put(KOL_NAVN_FORNAVN, getFornavn());
            jsonItem.put(KOL_NAVN_ETTERNAVN, getEtternavn());
            jsonItem.put(KOL_NAVN_ADRESSE, getAdresse());
            jsonItem.put(KOL_NAVN_POSTNR, getPostNr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonItem;
    }


}
