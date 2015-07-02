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

import Model.CityPlacesModel;

/**
 * Created by waqas on 6/24/15.
 */
public class CitysAdapter extends BaseAdapter {

    AQuery aqAdapter;
    Context ctx;
    LayoutInflater l_Inflater;
    BaseClass baseClass;

    public CitysAdapter(Context ctx) {

        this.ctx = ctx;
        l_Inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        View v ;
        ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.city_detail_item, null);

        aqAdapter = new AQuery(v);
        holder = new ViewHolder();

        holder.city_Name = (TextView) v.findViewById(R.id.title);
        holder.Star = (ImageView) v.findViewById(R.id.star);

        v.setTag(holder);
        holder.city_Name.setText(CityPlacesModel.getInstance().results.get(position).title);
        aqAdapter.id(R.id.star).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cityId = CityPlacesModel.getInstance().results.get(position).id;
                Log.e("CitydeatilsId", String.valueOf(CityPlacesModel.getInstance().results.get(position).id));

                ((FragmentActivity) ctx) .getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CityPlacesDetailFragment.newInstance(cityId))
                .addToBackStack(null).commit();




            }
        });
        aqAdapter.id(R.id.logo).image(CityPlacesModel.getInstance().results.get(position).get_photo,true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
        return v;
    }

    static class ViewHolder {
        TextView city_Name;
        ImageView Star;
    }

    @Override
    public int getCount() {
        return CityPlacesModel.getInstance().results.size();
    }

    @Override
    public CityPlacesModel.results getItem(int position) {
        return CityPlacesModel.getInstance().results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

