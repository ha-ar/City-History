package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import Model.EventsDetailsModel;
import Services.CallBack;
import Services.EventsDetailsService;

/**
 * Created by waqas on 7/5/15.
 */
public class EventsDetailFragment extends BaseFragment {

    AQuery aq;
    TextView title, disc;
    BaseClass base;
    ViewPager pager;
    CustomAdapter mCustomPagerAdapter;
    EventsDetailsService obj;
    static int PlaceId;
    static int p;
    ArrayList<ItemDetails> details;


    public static EventsDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInplaces ", String.valueOf(id));
        PlaceId = id;
        return new EventsDetailFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.events_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        base = ((BaseClass) getActivity().getApplicationContext());
        pager = (ViewPager) view.findViewById(R.id.events_pager);
        mCustomPagerAdapter = new CustomAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        title = (TextView) view.findViewById(R.id.title_events);
        disc = (TextView) view.findViewById(R.id.disc_events);
        obj = new EventsDetailsService(getActivity().getApplicationContext());
        obj.CityEventsDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));
        return view;
    }
    @Override
    public void onResume() {

        super.onResume();
    }
    public void CityPlacesDetails(Object caller, Object model) {

        EventsDetailsModel.getInstance().setList((EventsDetailsModel) model);
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.title_events).text(EventsDetailsModel.getInstance().title);


    }


    class CustomAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            try {
                Log.e("count",EventsDetailsModel.getInstance().album.photos_set.size() + "");
                return EventsDetailsModel.getInstance().album.photos_set.size();
            }catch (NullPointerException e){
                aq.id(R.id.pager).visibility(Constants.GONE);
                return 0;
            }
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.event_pager_item, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_event);
            aq.id(R.id.disc_events).text(Html.fromHtml(EventsDetailsModel.getInstance().album.photos_set.get(position).description));
            ImageLoader.getInstance().displayImage(EventsDetailsModel.getInstance().album.photos_set.get(position).get_photo, imageView);
            container.addView(itemView);
            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
