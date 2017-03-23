package com.example.ruben.hobbyhuset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Ruben on 23.03.2017.
 */

public class Kunde {

    int kundeNr;
    String fornavn;
    String etternavn;
    String adresse; // TODO: Make Class "adresse"
    int postNr; // TODO: make Class "Poststed"

    static final String TABELL_NAVN = "Kunde";
    static final String KOL_NAVN_KNR  = "KNr";
    static final String KOL_NAVN_FORNAVN = "Fornavn";
    static final String KOL_NAVN_ETTERNAVN = "Etternavn";
    static final String KOL_NAVN_ADRESSE = "Adresse";
    static final String KOL_NAVN_POSTNR = "PostNr";

    public Kunde(int kundeNr, String fornavn, String etternavn, String adresse, int postNr) {
        this.kundeNr = kundeNr;
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
        this.postNr = jsonKunde.optInt(KOL_NAVN_POSTNR);
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
}