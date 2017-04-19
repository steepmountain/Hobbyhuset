package com.example.ruben.hobbyhuset;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Ruben on 23.03.2017.
 */

public class Ordre extends Item implements Parcelable {

    int ordreNr;

    public int getOrdreNr() {
        return ordreNr;
    }

    public String getOrdreDato() {
        return ordreDato;
    }

    public String getSendtDato() {
        return sendtDato;
    }

    public String getBetaltDato() {
        return betaltDato;
    }

    public int getKundeNr() {
        return kundeNr;
    }

    String ordreDato;
    String sendtDato;
    String betaltDato;
    int kundeNr;


    // Name of table and columns from database
    static final String TABELL_NAVN_ORDRE = "Ordre";
    static final String KOL_NAVN_ORDRENR = "OrdreNr";
    static final String KOL_NAVN_ORDREDATO = "OrdreDato";
    static final String KOL_NAVN_SENDTDATO = "SendtDato";
    static final String KOL_NAVN_BETALTDATO = "BetaltDato";
    static final String KOL_NAVN_KNR = "KNr";

    static final String TABELL_NAVN_ORDRELINJE = "Ordrelinje";
    static final String KOL_NAVN_VARENR = "VNr";

    public Ordre(int ordreNr, String ordreDato, String sendtDato, String betaltDato, int kundeNr) {
        this.ordreNr = ordreNr;
        this.ordreDato = ordreDato;
        this.sendtDato = sendtDato;
        this.betaltDato = betaltDato;
        this.kundeNr = kundeNr;
    }

    public Ordre() {
    }

    public Ordre(JSONObject jsonOrdre) throws ParseException {
        this.ordreNr = jsonOrdre.optInt(KOL_NAVN_ORDRENR);
        this.ordreDato = jsonOrdre.optString(KOL_NAVN_ORDREDATO);
        this.sendtDato = jsonOrdre.optString(KOL_NAVN_SENDTDATO);
        this.betaltDato = jsonOrdre.optString(KOL_NAVN_BETALTDATO);
        this.kundeNr = jsonOrdre.optInt(KOL_NAVN_KNR);
    }

    public Ordre(String ordreDato, int kundeNr) {
        this.ordreDato = ordreDato;
        this.kundeNr = kundeNr;
    }

    public static ArrayList<Ordre> lagOrdreListe(String jsonString) throws JSONException, NullPointerException, ParseException {
        ArrayList<Ordre> ordreListe = new ArrayList<Ordre>();
        JSONObject jsonData = new JSONObject(jsonString);
        JSONArray jsonOrdreTabell = jsonData.optJSONArray(TABELL_NAVN_ORDRE);
        for (int i = 0; i < jsonOrdreTabell.length(); i++) {
            JSONObject jsonOrdre = (JSONObject) jsonOrdreTabell.get(i);
            Ordre ordre = new Ordre(jsonOrdre);
            ordreListe.add(ordre);
        }
        return ordreListe;
    }


    public static ArrayList<Integer> lagVareNrListe(String jsonString) throws JSONException {
        ArrayList<Integer> nyKundeNrListe = new ArrayList<>();
        JSONObject jsonData = new JSONObject(jsonString);
        JSONArray jsonOrdreTabell = jsonData.optJSONArray(TABELL_NAVN_ORDRELINJE);
        for (int i = 0; i < jsonOrdreTabell.length(); i++) {
            JSONObject jsonVareNr = (JSONObject) jsonOrdreTabell.get(i);
            int vareNr = jsonVareNr.getInt(KOL_NAVN_VARENR);
            nyKundeNrListe.add(vareNr);
        }
        return nyKundeNrListe;
    }

    @Override
    public String toString() {
        return "Ordre{" +
                "ordreNr=" + ordreNr +
                ", ordreDato=" + ordreDato +
                ", sendtDato=" + sendtDato +
                ", betaltDato=" + betaltDato +
                ", kundeNr=" + kundeNr +
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
        parcel.writeInt(getOrdreNr());
        parcel.writeString(getOrdreDato());
        parcel.writeString(getBetaltDato());
        parcel.writeString(getSendtDato());
        parcel.writeInt(getKundeNr());
    }

    // Constructor to create Kunde from parcel based on FIFO from writeToParcel
    private Ordre(Parcel in) {
        this.ordreNr = in.readInt();
        this.ordreDato = in.readString();
        this.betaltDato = in.readString();
        this.sendtDato = in.readString();
        this.kundeNr = in.readInt();
    }

    public static final Parcelable.Creator<Ordre> CREATOR = new Parcelable.Creator<Ordre>() {
        public Ordre createFromParcel(Parcel in) {
            return new Ordre(in);
        }

        public Ordre[] newArray(int size) {
            return new Ordre[size];
        }
    };

    @Override
    JSONObject toJSON() {
        JSONObject jsonItem = new JSONObject();
        try {
            jsonItem.put(KOL_NAVN_ORDRENR, getOrdreNr());
            jsonItem.put(KOL_NAVN_ORDREDATO, getOrdreDato());
            jsonItem.put(KOL_NAVN_SENDTDATO, getSendtDato());
            jsonItem.put(KOL_NAVN_BETALTDATO, getBetaltDato());
            jsonItem.put(KOL_NAVN_KNR, getKundeNr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonItem;
    }
}
