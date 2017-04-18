package com.example.ruben.hobbyhuset;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruben on 23.03.2017.
 */

public class Vare extends Item implements Parcelable{

    // Variables for Vare
    String vareNr;
    String betegnelse;
    double pris;
    int katNr;
    int antall;

    public String getVareNr() {
        return vareNr;
    }

    public String getBetegnelse() {
        return betegnelse;
    }

    public double getPris() {
        return pris;
    }

    public int getKatNr() {
        return katNr;
    }

    public int getAntall() {
        return antall;
    }

    public String getHylle() {
        return hylle;
    }

    String hylle;

    // Name of table and columns from database
    static final String TABELL_NAVN = "Vare";
    static final String KOL_NAVN_VNR = "VNr";
    static final String KOL_NAVN_BETEGNELSE = "Betegnelse";
    static final String KOL_NAVN_PRIS = "Pris";
    static final String KOL_NAVN_KATNR = "KatNr";
    static final String KOL_NAVN_ANTALL = "Antall";
    static final String KOL_NAVN_HYLLE = "Hylle";

    static final int DEFAULT_CATEGORY = 1;

    public Vare() {
        // Empty constructor
    }

    public Vare(String vareNr, String betegnelse, double pris, int katNr, int antall, String hylle) {
        this.vareNr = vareNr;
        this.betegnelse = betegnelse;
        this.pris = pris;
        this.katNr = katNr;
        this.antall = antall;
        this.hylle = hylle;
    }

    public Vare(JSONObject jsonVare) {
        Log.d("Vare", "JSON=" + jsonVare.toString());
        this.vareNr = jsonVare.optString(KOL_NAVN_VNR);
        this.betegnelse = jsonVare.optString(KOL_NAVN_BETEGNELSE);
        this.pris = jsonVare.optDouble(KOL_NAVN_PRIS);
        this.katNr = jsonVare.optInt(KOL_NAVN_KATNR);
        this.antall = jsonVare.optInt(KOL_NAVN_ANTALL);
        this.hylle = jsonVare.optString(KOL_NAVN_HYLLE);
    }

    public Vare(String vareNr, String betegnelse, double pris, int antall) {
        this.vareNr = vareNr;
        this.betegnelse = betegnelse;
        this.pris = pris;
        this.antall = antall;

        this.katNr = DEFAULT_CATEGORY;
    }

    // Metode som lager en ArrayList med Vare-objekter basert på en streng med JSONdata
    public static ArrayList<Vare> lagVareListe(String jsonString) throws JSONException, NullPointerException {
        ArrayList<Vare> vareListe = new ArrayList<>();
        JSONObject jsonData = new JSONObject(jsonString);
        JSONArray jsonKundeTabell = jsonData.optJSONArray(TABELL_NAVN);
        for(int i = 0; i < jsonKundeTabell.length(); i++) {
            JSONObject jsonKunde = (JSONObject) jsonKundeTabell.get(i);
            Vare vare = new Vare(jsonKunde);
            vareListe.add(vare);
        }
        return vareListe;
    }

    @Override
    public String toString() {
        return "Vare{" +
                "vareNr=" + vareNr +
                ", betegnelse='" + betegnelse + '\'' +
                ", pris=" + pris +
                ", katNr=" + katNr +
                ", antall=" + antall +
                ", hylle='" + hylle + '\'' +
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
        parcel.writeString(getVareNr());
        parcel.writeString(getBetegnelse());
        parcel.writeDouble(getPris());
        parcel.writeInt(getKatNr());
        parcel.writeInt(getAntall());
        parcel.writeString(getHylle());
    }

    // Constructor to create Vare from parcel based on FIFO from writeToParcel
    private Vare(Parcel in) {
        this.vareNr = in.readString();
        this.betegnelse = in.readString();
        this.pris = in.readDouble();
        this.katNr= in.readInt();
        this.antall = in.readInt();
        this.hylle = in.readString();
    }

    public static final Parcelable.Creator<Vare> CREATOR = new Parcelable.Creator<Vare>() {
        public Vare createFromParcel(Parcel in) {
            return new Vare(in);
        }

        public Vare[] newArray(int size) {
            return new Vare[size];
        }
    };

    @Override
    JSONObject toJSON() {
        JSONObject jsonItem = new JSONObject();
        try {
            jsonItem.put(KOL_NAVN_VNR, getVareNr());
            jsonItem.put(KOL_NAVN_BETEGNELSE, getBetegnelse());
            jsonItem.put(KOL_NAVN_PRIS, getPris());
            jsonItem.put(KOL_NAVN_KATNR, getKatNr());
            jsonItem.put(KOL_NAVN_ANTALL, getAntall());
            jsonItem.put(KOL_NAVN_HYLLE, getHylle());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonItem;
    }


}
