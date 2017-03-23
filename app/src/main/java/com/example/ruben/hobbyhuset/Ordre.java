package com.example.ruben.hobbyhuset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Ruben on 23.03.2017.
 */

public class Ordre {

    int ordreNr;
    Date ordreDato;
    Date sendtDato;
    Date betaltDato;
    int kundeNr;

    // Name of table and columns from database
    static final String TABELL_NAVN = "Ordre";
    static final String KOL_NAVN_ORDRENR = "OrdreNr";
    static final String KOL_NAVN_ORDREDATO = "OrdreDato";
    static final String KOL_NAVN_SENDTDATO = "SendtDato";
    static final String KOL_NAVN_BETALTDATO = "BetaltDato";
    static final String KOL_NAVN_KNR = "KNr";

    public Ordre(int ordreNr, Date ordreDato, Date sendtDato, Date betaltDato, int kundeNr) {
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

        // TODO: Fix this shit it wont work you dumbo
        String dateStr = jsonOrdre.optString(KOL_NAVN_ORDREDATO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.ordreDato = sdf.parse(dateStr);
        dateStr = jsonOrdre.optString(KOL_NAVN_SENDTDATO);
        this.sendtDato = sdf.parse(dateStr);
        dateStr = jsonOrdre.optString(KOL_NAVN_BETALTDATO);
        this.betaltDato = sdf.parse(dateStr);

        this.kundeNr = jsonOrdre.optInt(KOL_NAVN_KNR);
    }

    public static ArrayList<Ordre> lagOrdreListe(String jsonString) throws JSONException, NullPointerException, ParseException {
        ArrayList<Ordre> OrdreListe = new ArrayList<Ordre>();
        JSONObject jsonData = new JSONObject(jsonString);
        JSONArray jsonOrdreTabell = jsonData.optJSONArray(TABELL_NAVN);
        for(int i = 0; i < jsonOrdreTabell.length(); i++) {
            JSONObject jsonOrdre = (JSONObject) jsonOrdreTabell.get(i);
            Ordre ordre = new Ordre(jsonOrdre);
            OrdreListe.add(ordre);
        }
        return OrdreListe;
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

}
