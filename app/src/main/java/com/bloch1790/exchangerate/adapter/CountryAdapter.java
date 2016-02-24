package com.bloch1790.exchangerate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bloch1790.exchangerate.R;
import com.bloch1790.exchangerate.model.Country;

import java.util.List;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class CountryAdapter extends BaseAdapter {

    private List<Country> list;
    private LayoutInflater mInflater;

    public CountryAdapter(Context context, List<Country> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.i("TAG",list.size()+"");
        return list.size();
    }

    @Override
    public Country getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.country_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.country_name);
            holder.code = (TextView) convertView.findViewById(R.id.country_code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("TAG", getItem(position).getName());
        holder.name.setText(getItem(position).getName());
        holder.code.setText(getItem(position).getCode());
        return convertView;
    }

    public final class ViewHolder{
        public TextView name;
        public TextView code;
    }
}
