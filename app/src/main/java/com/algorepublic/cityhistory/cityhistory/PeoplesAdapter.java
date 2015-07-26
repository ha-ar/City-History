package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import Model.PeoplesModel;

/**
 * Created by waqas on 7/6/15.
 */
public class PeoplesAdapter extends BaseAdapter {
    AQuery aqAdapter;
    Context ctx;
    LayoutInflater l_Inflater;

    public PeoplesAdapter(Context ctx) {

        this.ctx = ctx;
        l_Inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v ;
        ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.people_pager_adapter_item, null);
        aqAdapter = new AQuery(v);
        holder = new ViewHolder();
        holder.people_Name = (TextView) v.findViewById(R.id.people_title);
//        holder.Star = (ImageView) v.findViewById(R.id.star_people);

        v.setTag(holder);
        holder.people_Name.setText(PeoplesModel.getInstance().results.get(position).title);
        aqAdapter.id(R.id.logo_people).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cityId = PeoplesModel.getInstance().results.get(position).id;
                Log.e("CitydeatilsId", String.valueOf(PeoplesModel.getInstance().results.get(position).id));
                ((FragmentActivity) ctx) .getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PeoplesDetailFragment.newInstance(cityId))
                        .addToBackStack(null).commit();
            }
        });
        aqAdapter.id(R.id.logo_people).image(PeoplesModel.getInstance().results.get(position).get_photo,true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
        return v;
    }

    static class ViewHolder {
        TextView people_Name;
        ImageView Star;
    }

    @Override
    public int getCount() {
        return PeoplesModel.getInstance().results.size();
    }

    @Override
    public PeoplesModel.results getItem(int position) {
        return PeoplesModel.getInstance().results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }}
