package com.example.ruben.hobbyhuset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for Kunde ListView
 */

public class VareAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Vare> mVare;
    LayoutInflater mInflater;

    public VareAdapter(Context context, ArrayList<Vare> vare) {
        mContext = context;
        mVare = vare;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mVare.size();
    }

    @Override
    public Object getItem(int i) {
        return mVare.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.vare_item, null);
            vh = new ViewHolder();
            vh.tvVareNr = (TextView) convertView.findViewById(R.id.vareTextView_vareNr);
            vh.tvBetegnelse = (TextView) convertView.findViewById(R.id.vareTextView_betegnelse);
            vh.tvPris = (TextView) convertView.findViewById(R.id.vareTextView_pris);
            vh.tvKatNr = (TextView) convertView.findViewById(R.id.vareTextView_katNr);
            vh.tvAntall = (TextView) convertView.findViewById(R.id.vareTextView_antall);
            vh.tvHylle = (TextView) convertView.findViewById(R.id.vareTextView_hylle);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Vare item = mVare.get(position);
        vh.tvVareNr.setText(item.getVareNr() + "");
        vh.tvBetegnelse.setText(item.getBetegnelse());
        vh.tvPris.setText("Pris : " + item.getPris() + "kr");
        vh.tvKatNr.setText("KategoriNr : " + item.getKatNr() + ""); //
        vh.tvAntall.setText("Antall : " +item.getAntall() + "");
        vh.tvHylle.setText("Hylle : " + item.getHylle());

        return convertView;
    }

    protected static class ViewHolder {
        TextView tvVareNr;
        TextView tvBetegnelse;
        TextView tvPris;
        TextView tvKatNr;
        TextView tvAntall;
        TextView tvHylle;
    }
}
