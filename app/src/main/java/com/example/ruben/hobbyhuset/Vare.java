package com.example.ruben.hobbyhuset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ruben on 23.03.2017.
 */

public class Vare {

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
        this.vareNr = jsonVare.optString(KOL_NAVN_VNR);
        this.betegnelse = jsonVare.optString(KOL_NAVN_BETEGNELSE);
        this.pris = jsonVare.optDouble(KOL_NAVN_PRIS);
        this.katNr = jsonVare.optInt(KOL_NAVN_KATNR);
        this.antall = jsonVare.optInt(KOL_NAVN_ANTALL);
        this.hylle = jsonVare.optString(KOL_NAVN_HYLLE);
    }

    // Metode som lager en ArrayList med Vare-objekter basert på en streng med JSONdata
    public static ArrayList<Vare> lagVareListe(String jsonString) throws JSONException, NullPointerException {
        ArrayList<Vare> vareListe = new ArrayList<Vare>();
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
}
