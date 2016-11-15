package edu.utdallas.hciproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sindhura on 11/15/2016.
 */

class DatasetAdapter extends ArrayAdapter {

    private List<String[]> dataset;

    private static class ViewHolder {
        TextView[] textViews;
    }

    DatasetAdapter(Context context, int resource, List<String[]> data) {
        super(context, resource);
        this.dataset = data;
    }

    @Override
    public int getCount() {
        return dataset.size()-1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            holder = new ViewHolder();
            holder.textViews = new TextView[7];
            holder.textViews[0] = (TextView) convertView.findViewById(R.id.tvDate);
            holder.textViews[1] = (TextView) convertView.findViewById(R.id.tvOpen);
            holder.textViews[2] = (TextView) convertView.findViewById(R.id.tvHigh);
            holder.textViews[3] = (TextView) convertView.findViewById(R.id.tvLow);
            holder.textViews[4] = (TextView) convertView.findViewById(R.id.tvClose);
            holder.textViews[5] = (TextView) convertView.findViewById(R.id.tvVolume);
            holder.textViews[6] = (TextView) convertView.findViewById(R.id.tvAdjClose);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        String[] data = dataset.get(position+1);
        if (data.length < 7) return convertView;
        for (int i = 0; i < 7; i++) {
            holder.textViews[i].setText(data[i]);
        }
        return convertView;
    }

    void setDataset(List<String[]> dataset) {
        this.dataset = dataset;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
