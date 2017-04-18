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
 * Adapter for Kunde ListView
 */

public class KundeAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Kunde> mKunder;
    LayoutInflater mInflater;

    public KundeAdapter(Context context, ArrayList<Kunde> kunder) {
        mContext = context;
        mKunder = kunder;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mKunder.size();
    }

    @Override
    public Object getItem(int position) {
        return mKunder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.kunde_item, null);
            vh = new ViewHolder();
            vh.tvKundeNr = (TextView) convertView.findViewById(R.id.kundeItemTextView_kundeNr);
            vh.tvFornavn = (TextView) convertView.findViewById(R.id.kundeItemTextView_fornavn);
            vh.tvEtternavn = (TextView) convertView.findViewById(R.id.kundeItemTextView_etternavn);
            vh.tvAdresse = (TextView) convertView.findViewById(R.id.kundeItemTextView_adresse);
            vh.tvPostNr = (TextView) convertView.findViewById(R.id.kundeItemTextView_postNr);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Kunde item = mKunder.get(position);
        vh.tvKundeNr.setText(item.getKundeNr() + ""); // Must be made into string to avoid Android thinking it's a resource ID
        vh.tvFornavn.setText(item.getFornavn());
        vh.tvEtternavn.setText(item.getEtternavn());
        vh.tvAdresse.setText(item.getAdresse());
        vh.tvPostNr.setText(    item.getPostNr() + ""); // Must be made into string to avoid Android thinking it's a resource ID

        return convertView;
    }

    protected static class ViewHolder {
        TextView tvKundeNr;
        TextView tvFornavn;
        TextView tvEtternavn;
        TextView tvAdresse;
        TextView tvPostNr;
    }
}
