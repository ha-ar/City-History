package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.net.URI;
import java.util.ArrayList;

import Model.PlacesModel;

/**
 * Created by ahmad on 8/17/15.
 */
public class PhotoAdapter extends BaseAdapter {

    AQuery aqAdapter;
    Context ctx;
    LayoutInflater l_Inflater;
    BaseClass baseClass;
    public static ArrayList<PhotoDetails> itemDetailsList;
    int k=0;


    public PhotoAdapter(Context ctx, ArrayList<PhotoDetails> result) {
//        itemDetailsList.addAll(result);
        itemDetailsList = result;
        Log.e("List Size:", itemDetailsList.size() + "");
        this.ctx = ctx;
        l_Inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.photo_pager_adapter, null);
        aqAdapter = new AQuery(view);
//        if (view == null) {
//        holder = new ViewHolder();
////        holder.city_Name = (TextView) v.findViewById(R.id.title_places);
//            holder.Star = (ImageView) view.findViewById(R.id.logo_main);
//
//            view.setTag(holder);
//        }else {
//            holder = (ViewHolder) view.getTag();
//        }
//        holder.city_Name.setText(PlacesModel.getInstance().results.get(position).title);
//        aqAdapter.id(R.id.logo_main).clicked(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int cityId = PlacesModel.getInstance().results.get(position).id;
//                ((FragmentActivity) ctx) .getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, PlacesDetailFragment.newInstance(cityId))
//                        .addToBackStack(null).commit();
//
//
//
//
//            }
//        });
        try {
//            Log.e("Date:",itemDetailsList.get(position).getDate());
            Log.e("Size:",itemDetailsList.size()+"");
            aqAdapter.id(R.id.logo_main).image(itemDetailsList.get(position).getPhoto());
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
            return view;
    }

    static class ViewHolder {
        TextView city_Name;
        ImageView Star;
    }

    @Override
    public int getCount() {
        return itemDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

