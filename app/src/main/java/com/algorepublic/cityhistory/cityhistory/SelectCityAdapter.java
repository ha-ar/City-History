package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;

import Model.SelectCityModel;

/**
 * Created by waqas on 6/19/15.
 */
public class SelectCityAdapter extends BaseAdapter {

    AQuery aqAdapter;
    Context ctx;
    BaseClass base;
    LayoutInflater l_Inflater;
    BaseClass baseClass;
    public SelectCityAdapter(Context ctx) {
        super();
        this.ctx = ctx;
        baseClass = ((BaseClass)ctx.getApplicationContext());
        l_Inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {

        return SelectCityModel.getInstance().results.size();
    }

    @Override
    public SelectCityModel.results getItem(int position) {
        return SelectCityModel.getInstance().results.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public View getView(final int position,  View convertView, ViewGroup viewGroup) {


        View v ;
        ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.select_city_item, null);

        aqAdapter = new AQuery(v);
        holder = new ViewHolder();

        holder.city_Name = (TextView) v.findViewById(R.id.city_name);

        v.setTag(holder);
        holder.city_Name.setText(getItem(position).name);
        return v;
    }

    static class ViewHolder {
        TextView city_Name;

    }
}
