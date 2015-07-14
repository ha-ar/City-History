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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import Model.PeoplesDetailModel;
import Services.CallBack;
import Services.PeopleDetailService;

/**
 * Created by waqas on 7/6/15.
 */
public class PeoplesDetailFragment extends BaseFragment {



    AQuery aq;
    TextView title, disc;
    BaseClass base;
    ViewPager pager;
    Adapter mCustomPagerAdapter;
    PeopleDetailService obj;
    static int PlaceId;
    static int p;
    Button map;
    ArrayList<ItemDetails> details;




    public static PeoplesDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInplaces ", String.valueOf(id));
        PlaceId = id;
        return new PeoplesDetailFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.people_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        base = ((BaseClass) getActivity().getApplicationContext());
        pager = (ViewPager) view.findViewById(R.id.people_pager);
        mCustomPagerAdapter = new Adapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        title = (TextView) view.findViewById(R.id.title_people);
//        map =(Button) view.findViewById(R.id.button1);
        disc = (TextView) view.findViewById(R.id.disc_people);
        obj = new PeopleDetailService(getActivity().getApplicationContext());
        obj.CityPeoplesDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, MapsFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
        return view;
    }
    @Override
    public void onResume() {

        super.onResume();
    }


    public void CityPlacesDetails(Object caller, Object model) {

        PeoplesDetailModel.getInstance().setList((PeoplesDetailModel) model);
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.title_people).text(PeoplesDetailModel.getInstance().title);
        aq.id(R.id.disc_people).text(Html.fromHtml(PeoplesDetailModel.getInstance().description));

    }

    class Adapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public Adapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            try {
                Log.e("count",PeoplesDetailModel.getInstance().album.photos_set.size() + "");
                return PeoplesDetailModel.getInstance().album.photos_set.size();
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
            ImageLoader.getInstance().displayImage(PeoplesDetailModel.getInstance().album.photos_set.get(position).get_photo, imageView);
            container.addView(itemView);
            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
