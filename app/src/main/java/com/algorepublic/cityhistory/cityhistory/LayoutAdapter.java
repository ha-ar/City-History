/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

import Model.PeoplesDetailModel;
import Model.PlacesDetailModel;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {


    private final Context mContext;
    private final TwoWayView mRecyclerView;
    private final int mLayoutId= R.layout.places_detail_fragment;
    private int mCurrentItemId = 0;
    ArrayList<ItemDetails> itemDetailses = new ArrayList<>();
    AQuery aqAdapter;
    //    int  position;
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {


        public SimpleViewHolder(View view) {
            super(view);
        }
    }

    public LayoutAdapter(Context context, TwoWayView recyclerView) {
        for(int loop=0;loop< PeoplesDetailModel.getInstance().album.photos_set.size();loop++) {
            ItemDetails itemDetails = new ItemDetails();
            itemDetails.setTitle(PeoplesDetailModel.getInstance().album.photos_set.get(loop).get_photo);
            itemDetailses.add(itemDetails);
        }
        mContext = context;
        mRecyclerView = recyclerView;
    }



    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        aqAdapter = new AQuery(view);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        Log.e("image1",itemDetailses.get(position).getTitle());

        final View itemView = holder.itemView;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.photo_image);
        ImageLoader.getInstance().displayImage(PlacesDetailModel.getInstance().album.photos_set.get(position).get_photo, imageView);
//        ImageLoader.getInstance().displayImage(itemDetailses.get(position).getTitle(), imageView);

//                 aqAdapter.id(R.id.photo_image).image(itemDetailses.get(position).getTitle(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);

    }

    @Override
    public int getItemCount() {

        Log.e("ItemDetails",itemDetailses.size()+"");
        return itemDetailses.size();
    }
}
