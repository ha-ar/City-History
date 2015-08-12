package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import Model.PlacesDetailModel;

/**
 * Created by waqas on 8/11/15.
 */
public class TwentyAdapter extends BaseAdapter{
    Context ctx;
    AQuery aqAdapter;
    LayoutInflater l_Inflater;
    public TwentyAdapter(Context ctx) {

        this.ctx = ctx;
        l_Inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        View v ;
       ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.twentyfifteen_pager_item, null);
        aqAdapter.id(R.id.tewntyfifteen_main).image(PlacesDetailModel.getInstance().articledetails_set.get(position).album.photos_set.get(position).get_photo);
        aqAdapter = new AQuery(v);
        return v;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    static class ViewHolder {
        TextView city_Name;
        ImageView Star;
    }
}
