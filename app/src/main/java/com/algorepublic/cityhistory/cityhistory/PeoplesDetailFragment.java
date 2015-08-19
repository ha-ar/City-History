package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

import Model.PeoplesDetailModel;
import Model.PlacesDetailModel;
import Services.CallBack;
import Services.PeopleDetailService;

/**
 * Created by waqas on 7/6/15.
 */
public class PeoplesDetailFragment extends BaseFragment {



    AQuery aq;

    TextView title,type ,disc,added , added_by,home_detail;
    BaseClass base;
    ViewPager pager;
    Adapter mCustomPagerAdapter;
    PeopleDetailService obj;
    ImageView imageView;
    AdView adView;
    GoogleMap googleMap;
    private TwoWayView mRecyclerView;
    static String coordinates;
    static int PlaceId;
    ArrayList<ItemDetails> details;
    int Position=0;
    ImageView image;
    ArrayList<String> stringArrayList;
    public ArrayList<String> years, years2;
    static String subyear;
    ViewPager peoplePager;
    private PagerSlidingTabStrip tabs;
    private int height = 5 ;
    public int counter =0;

    public static PeoplesDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInpeople ", String.valueOf(id));
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
        adView = (AdView) view.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        pager = (ViewPager) view.findViewById(R.id.people_pager);
        peoplePager  = (ViewPager)view.findViewById(R.id.people_pagerForList);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.people_tabs);
        image = (ImageView) view.findViewById(R.id.imgLogo);
        title = (TextView) view.findViewById(R.id.people_head);
        years2 = new ArrayList<>();
        mRecyclerView = (TwoWayView) view.findViewById(R.id.people_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        disc = (TextView) view.findViewById(R.id.people_disc);
        type = (TextView) view.findViewById(R.id.people_type);
        added = (TextView) view.findViewById(R.id.people_added);
        imageView = (ImageView) view.findViewById(R.id.home_detail);
        home_detail = (TextView) view.findViewById(R.id.title_header);
        added_by = (TextView) view.findViewById(R.id.people_added_by);
        aq.id(R.id.people_mapView).visibility(View.GONE);
        obj = new PeopleDetailService(getActivity().getApplicationContext());
        obj.CityPeoplesDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));
        showPager();
        showPagerSecond();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        BaseActivity.toolbar.setVisibility(View.GONE);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);
        createMapView();
        showPager();
        showPagerSecond();
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                pager.setCurrentItem(position);
                aq.id(R.id.people_disc).text(Html.fromHtml(PeoplesDetailModel.getInstance().description));
            }
        });

    }

    public void showPager(){

        aq.id(R.id.imgLogo).visibility(View.GONE);
        aq.id(R.id.people_pager).visibility(View.VISIBLE);
        aq.id(R.id.people_list).visibility(View.VISIBLE);

    }

    public void showImage(){
        aq.id(R.id.imgLogo).visibility(View.VISIBLE);
        aq.id(R.id.people_pager).visibility(View.GONE);
        aq.id(R.id.people_list).visibility(View.GONE);

    }

    public void hidePagerSecond(){
        aq.id(R.id.location_people_photosset).visibility(View.GONE);
    }
    public void showPagerSecond(){
        aq.id(R.id.location_people_photosset).visibility(View.VISIBLE);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        showPager();
        showPagerSecond();
        BaseActivity.toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showPager();
        showPagerSecond();
        BaseActivity.toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        showPagerSecond();
        showPager();
    }
    private void createMapView(){

        try {
            if(googleMap == null){

                if (Build.VERSION.SDK_INT < 16) {
                    googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.event_mapView)).getMap();

                } else {
                    googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.event_mapView)).getMap();

                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }

    }


    public void CityPlacesDetails(Object caller, Object model) {
//     Log.e("count","count");
        PeoplesDetailModel.getInstance().album.photos_set.clear();
        PeoplesDetailModel.getInstance().setList((PeoplesDetailModel) model);
        if (PeoplesDetailModel.getInstance().album == null || PeoplesDetailModel.getInstance().album.photos_set.size()==0){
            showImage();
            aq.id(R.id.imgLogo).image(PeoplesDetailModel.getInstance().get_photo);
            aq.id(R.id.people_disc).text(Html.fromHtml(PeoplesDetailModel.getInstance().description));
        }else{
            showPager();
        }
        if (PeoplesDetailModel.getInstance().articledetails_set.size() == 0 || PeoplesDetailModel.getInstance().articledetails_set == null){
            Log.e("subyear", "subyear");
            hidePagerSecond();
        }
        else {
            showPagerSecond();
        }
        mCustomPagerAdapter = new Adapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(), mRecyclerView));
        upDateData();
        stringArrayList = new ArrayList<String>();

        // check the null value of origenal date comes from api.

        for(int loop=0;loop< PeoplesDetailModel.getInstance().articledetails_set.size();loop++)
        {

            if (PeoplesDetailModel.getInstance().articledetails_set.get(loop).original_date == null){
                years.add("Unknown"+counter);
                counter++;
            }else {
                subyear = PeoplesDetailModel.getInstance().articledetails_set.get(loop).original_date;
                years.add(PeoplesDetailModel.getInstance().articledetails_set.get(loop).original_date.toString());
                Log.e("subyear", subyear);
                String[] splited = subyear.split("-");
                String year = splited[0];
                if (!stringArrayList.contains(year))
                    stringArrayList.add(year);
            }
        }
        Log.e("1","2");
        PageAdapter pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
        peoplePager.setAdapter(pagerAdapter);

        tabs.setIndicatorColor(getResources().getColor(R.color.Orange));
        tabs.setTextColor(getResources().getColor(R.color.Black));
        tabs.setIndicatorHeight(height);
        tabs.setDividerColor(getResources().getColor(R.color.Black));
        tabs.setViewPager(peoplePager);

    }
    public class PageAdapter extends FragmentStatePagerAdapter {

        private String[] TITLES = {"All", "New", "Used", "Rental", "eBook"};
        private String[] array ;

        public PageAdapter(FragmentManager fm) {
            super(fm);
            Log.e("1", stringArrayList.toArray(new String[stringArrayList.size()]).toString());
            // TITLES = stringArrayList.toArray(new String[stringArrayList.size()]);
            if (years.get(0).contains("Unknown"))
            {
                array = years.toArray(new String[years.size()]);
            }else {
                array = stringArrayList.toArray(new String[stringArrayList.size()]);
                for (int loop = 0; loop < array.length; loop++) {
                    Log.e("Array Value", array[loop] + " " + years.get(loop));
                }
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return array[position];
        }

        @Override
        public int getCount() {
            return array.length;
        }

        @Override
        public Fragment getItem(int position) {

            Log.e("Year Date", years.get(position));
            if(position == 0)
                return PhotoFragmnet.getInstance(years.get(position), PlaceId,position);
            else {
                Log.e("Else", "Ok");
                return PhotoFragmnet2.getInstance(years.get(position), PlaceId);
            }
        }

    }



    private void upDateData(){


        aq.id(R.id.people_head).text(PeoplesDetailModel.getInstance().title);
        aq.id(R.id.title_header).text(PeoplesDetailModel.getInstance().title);
        aq.id(R.id.people_added).text(PeoplesDetailModel.getInstance().added);
        if (PeoplesDetailModel.getInstance().address.isEmpty()){
            aq.id(R.id.people_layout_map).visibility(View.GONE);
            aq.id(R.id.location).visibility(View.GONE);
        }
        aq.id(R.id.people_added_by).text(capSentences(PeoplesDetailModel.getInstance().user.username));
        LatLong();
        if (PeoplesDetailModel.getInstance().type.type.equals("PE")){
            aq.id(R.id.people_type).text("People");
        }


    }
    private void LatLong(){
        if (PeoplesDetailModel.getInstance().address.isEmpty()){
            aq.id(R.id.people_mapView).visibility(View.GONE);
        }else{
            coordinates=PeoplesDetailModel.getInstance().address;
            String [] splited = coordinates.split("\\s+");
            String Lat = splited[0];
            String Lng =  splited [1];
            addMarker(Double.valueOf(Lat), Double.valueOf(Lng), PeoplesDetailModel
                    .getInstance().title);
        }}
    private void addMarker(double Lat,double Lon,String Title) {

        if(googleMap !=null) {
            Log.e("awww", "cac");
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Lat, Lon))
                            .title(Title)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .draggable(true)
            );
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(Double.valueOf(Lat), Double.valueOf(Lon))).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    private String capSentences( String text ) {

        return text.substring( 0, 1 ).toUpperCase() + text.substring( 1 ).toLowerCase();
    }
    class Adapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        ImageView imageView;
        public Adapter(Context context) {
            Position =0;
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            try {

                return PeoplesDetailModel.getInstance().album.photos_set.size();
            }catch (NullPointerException e){
//                aq.id(R.id.people_pager).visibility(Constants.GONE);
                return 0;
            }
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==  object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Position = position;
            View itemView = mLayoutInflater.inflate(R.layout.places_pager_item, container, false);
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_people);
            Log.e("log","log");
//             if (PeoplesDetailModel.getInstance().album==null){
//
//                   aq.id(imageView).progress(progressBar).image(PeoplesDetailModel.getInstance().get_photo, true, true);
//               }else {
            aq.id(imageView).progress(progressBar).image(PeoplesDetailModel.getInstance().album.photos_set.get(Position).get_photo, true, true);
            //  }
            aq.id(R.id.people_disc).text(Html.fromHtml(PeoplesDetailModel.getInstance().description));
            container.addView(itemView);

            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }


    public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {


        private final Context mContext;
        AQuery aqAdapter;

        public class SimpleViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public SimpleViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.people_photo_image);
            }
        }

        public LayoutAdapter(Context context, TwoWayView recyclerView) {

            mContext = context;
            mRecyclerView = recyclerView;
        }


        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.people_item_gallery, parent, false);
            aqAdapter = new AQuery(view);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            if (PeoplesDetailModel.getInstance().album.photos_set==null){
                aq.id(R.id.people_list).visibility(View.GONE);
            }else{
                aqAdapter.id(holder.imageView).image(PeoplesDetailModel.getInstance().album.photos_set.get(position).get_photo, true, true);
            }}


        @Override
        public int getItemCount() {
            try {
                return PeoplesDetailModel.getInstance().album.photos_set.size();
            } catch (NullPointerException e) {
                aq.id(R.id.people_list).visibility(View.GONE);
                return 0;
            }
        }
    }

}
