package com.algorepublic.cityhistory.cityhistory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import Model.PlacesDetailModel;
import Services.CallBack;
import Services.PlacesDetailService;

/**
 * Created by waqas on 6/26/15.
 */
public class PlacesDetailFragment extends BaseFragment {
    AQuery aq;
    TextView title, disc;
    BaseClass base;
    ViewPager pager;
    CustomPagerAdapter mCustomPagerAdapter;
    PlacesDetailService obj;
    Button map;
    static int PlaceId;

    private static final String ARG_LAYOUT_ID = "layout_id";

    private TwoWayView mRecyclerView;




    public static PlacesDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInplaces ", String.valueOf(id));
        PlaceId = id;
        return new PlacesDetailFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Activity activity = getActivity();
        View view = inflater.inflate(R.layout.places_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        base = ((BaseClass) getActivity().getApplicationContext());
        pager = (ViewPager) view.findViewById(R.id.pager);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        title = (TextView) view.findViewById(R.id.title);
        mRecyclerView = (TwoWayView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        map = (Button) view.findViewById(R.id.button1);
        disc = (TextView) view.findViewById(R.id.disc);
        obj = new PlacesDetailService(getActivity().getApplicationContext());
        obj.CityPlacesDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));

//        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);


        return view;
    }
    @Override
    public void onResume() {

        super.onResume();
    }
    public void CityPlacesDetails(Object caller, Object model) {

        PlacesDetailModel.getInstance().setList((PlacesDetailModel) model);
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(),mRecyclerView));
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.title).text(PlacesDetailModel.getInstance().title);

    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        private boolean doNotifyDataSetChangedOnce = false;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public CustomPagerAdapter() {
            super();
        }

        @Override
        public int getCount() {

            try {
                return PlacesDetailModel.getInstance().album.photos_set.size();
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
            View itemView = mLayoutInflater.inflate(R.layout.places_pager_item, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_people);
//          if (position>0){
//            if (base.saveposition>position){
//                base.saveposition = position;
//                position++;
//            }else{
//                base.saveposition=position;
//                position--;
//            }}


            ImageLoader.getInstance().displayImage(PlacesDetailModel.getInstance().album.photos_set.get(position).get_photo, imageView);
            Log.e("positon",position + "");
            aq.id(R.id.disc).text(Html.fromHtml(PlacesDetailModel.getInstance().album.photos_set.get(position).description));


            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private class GalleryPagerAdapter {
        public GalleryPagerAdapter(FragmentActivity activity) {
        }
    }
}
















