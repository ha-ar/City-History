package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import Model.CityPlacesDetailModel;
import Services.CallBack;
import Services.CityPlacesDetailService;

/**
 * Created by waqas on 6/26/15.
 */
public class CityPlacesDetailFragment extends BaseFragment {
    AQuery aq;
    TextView title, disc;
    BaseClass base;
    ViewPager pager;
    CustomPagerAdapter mCustomPagerAdapter;
    CityPlacesDetailService obj;
    static int PlaceId;


    public static CityPlacesDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInplaces ", String.valueOf(id));
        PlaceId = id;
        return new CityPlacesDetailFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.city_places_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);


        base = ((BaseClass) getActivity().getApplicationContext());
        pager = (ViewPager) view.findViewById(R.id.pager);
        title = (TextView) view.findViewById(R.id.title);

        disc = (TextView) view.findViewById(R.id.disc);
        obj = new CityPlacesDetailService(getActivity().getApplicationContext());
        obj.CityPlacesDetails(PlaceId,true, new CallBack(this, "CityPlacesDetails"));

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public void CityPlacesDetails(Object caller, Object model) {

        CityPlacesDetailModel.getInstance().setList((CityPlacesDetailModel) model);
            mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
            pager.setAdapter(mCustomPagerAdapter);

    }
    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            Log.e("size", CityPlacesDetailModel.getInstance().album.photos_set.size()+"");
            return CityPlacesDetailModel.getInstance().album.photos_set.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
            Log.e("image",String.valueOf(position));
            aq.id(R.id.imageView).image(CityPlacesDetailModel.getInstance().album.photos_set.get(position).get_photo ,true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
            Log.e("Imagecheck", CityPlacesDetailModel.getInstance().album.photos_set.get(position).get_photo);
            container.addView(itemView);


            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
















