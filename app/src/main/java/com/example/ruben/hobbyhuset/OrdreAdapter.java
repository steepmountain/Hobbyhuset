package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ruben on 30.03.2017.
 */

public class OrdreAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Ordre> mOrdre;
    LayoutInflater mInflater;

    public OrdreAdapter(Context context, ArrayList<Ordre> ordre) {
        mContext = context;
        mOrdre = ordre;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mOrdre.size();
    }

    @Override
    public Object getItem(int i) {
        return mOrdre.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ordre_item, null);
            vh = new ViewHolder();
            vh.tvOrdreNr = (TextView) convertView.findViewById(R.id.ordreItemTextView_ordreNr);
            vh.tvOrdreDato = (TextView) convertView.findViewById(R.id.ordreItemTextView_ordreDato);
            vh.tvSendtDato = (TextView) convertView.findViewById(R.id.ordreItemTextView_sendtDato);
            vh.tvBetaltDato = (TextView) convertView.findViewById(R.id.ordreItemTextView_betaltDato);
            vh.tvKundeNr = (TextView) convertView.findViewById(R.id.ordreItemTextView_kundeNr);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Ordre item = mOrdre.get(position);
        vh.tvOrdreNr.setText("OrdreNr : " + item.getOrdreNr() + "");
        vh.tvOrdreDato.setText("Ordredato : " + item.getOrdreDato());
        vh.tvSendtDato.setText("Sendtdato : " + item.getSendtDato());
        vh.tvBetaltDato.setText("Betaltdato : " + item.getBetaltDato());
        vh.tvKundeNr.setText("KundeNr : " + item.getKundeNr() + "");
        return convertView;
    }

    protected static class ViewHolder {
        TextView tvOrdreNr;
        TextView tvOrdreDato;
        TextView tvSendtDato;
        TextView tvBetaltDato;
        TextView tvKundeNr;
    }
}
