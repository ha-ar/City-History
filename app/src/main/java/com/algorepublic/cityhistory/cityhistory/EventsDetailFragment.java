package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
import com.androidquery.util.Constants;
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

import Model.EventsDetailsModel;
import Services.CallBack;
import Services.EventsDetailsService;

/**
 * Created by waqas on 7/5/15.
 */
public class EventsDetailFragment extends BaseFragment {

    AQuery aq;
    TextView title, disc,type ,added  , added_by,home_detail;
    BaseClass base;
    ViewPager pager;
    CustomAdapter mCustomPagerAdapter;
    EventsDetailsService obj;
    ImageView imageView;
    static int PlaceId;
    AdView adView;
    static int p;
    GoogleMap googleMap;
    private TwoWayView mRecyclerView;
    static String coordinates;
    ArrayList<ItemDetails> details;
    int Position=0;

    public static EventsDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInevents ", String.valueOf(id));
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
        adView = (AdView) view.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        pager = (ViewPager) view.findViewById(R.id.event_pager);
        mCustomPagerAdapter = new CustomAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        title = (TextView) view.findViewById(R.id.event_head);
        mRecyclerView = (TwoWayView) view.findViewById(R.id.event_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        disc = (TextView) view.findViewById(R.id.event_disc);
        type = (TextView) view.findViewById(R.id.event_type);
        added = (TextView) view.findViewById(R.id.event_added);
        imageView = (ImageView) view.findViewById(R.id.home_detail);
        home_detail = (TextView) view.findViewById(R.id.title_header);
        added_by = (TextView) view.findViewById(R.id.event_added_by);
        disc = (TextView) view.findViewById(R.id.event_disc);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);
        createMapView();
        obj = new EventsDetailsService(getActivity().getApplicationContext());
        obj.CityEventsDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
        @Override
        public void onItemClick(RecyclerView parent, View child, int position, long id) {
            pager.setCurrentItem(position);
            aq.id(R.id.event_disc).text(Html.fromHtml(EventsDetailsModel.getInstance().album.photos_set.get(position).description));
        }
    });
    }

    @Override
    public void onResume() {

        super.onResume();
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

        EventsDetailsModel.getInstance().setList((EventsDetailsModel) model);
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(),mRecyclerView));
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.event_head).text(EventsDetailsModel.getInstance().title);
        aq.id(R.id.title_header).text(EventsDetailsModel.getInstance().title);
        aq.id(R.id.event_added).text(EventsDetailsModel.getInstance().added);
        if (EventsDetailsModel.getInstance().address.isEmpty()){
            aq.id(R.id.event_layout_map).visibility(View.GONE);
            aq.id(R.id.location).visibility(View.GONE);
        }
        aq.id(R.id.event_added_by).text(capSentences(EventsDetailsModel.getInstance().user.username));
        LatLong();
        if (EventsDetailsModel.getInstance().type.type.equals("EV")){
            aq.id(R.id.event_type).text("Events");
        }

    }
    private void LatLong(){
        if (EventsDetailsModel.getInstance().address.isEmpty()){
            aq.id(R.id.event_mapView).visibility(View.GONE);
        }else{
            coordinates=EventsDetailsModel.getInstance().address;
            String [] splited = coordinates.split("\\s+");
            String Lat = splited[0];
            String Lng =  splited [1];
            addMarker(Double.valueOf(Lat), Double.valueOf(Lng), EventsDetailsModel.getInstance().title);
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
    class CustomAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        ImageView imageView;
        public CustomAdapter(Context context) {
            Position =0;
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            try {
                return EventsDetailsModel.getInstance().album.photos_set.size();
            }catch (NullPointerException e){
                aq.id(R.id.pager).visibility(Constants.GONE);
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
            View itemView = mLayoutInflater.inflate(R.layout.event_pager_item, container, false);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_event);
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            aq.id(imageView).progress(progressBar).image(EventsDetailsModel.getInstance().album.photos_set.get(Position).get_photo, true, true);
            aq.id(R.id.event_disc).text(Html.fromHtml(EventsDetailsModel.getInstance().album.photos_set.get(position).description));
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
            ImageView  imageView;
            public SimpleViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.event_photo_image);
            }
        }

        public LayoutAdapter(Context context, TwoWayView recyclerView) {


            mContext = context;
            mRecyclerView = recyclerView;
        }



        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.event_item_gallery, parent, false);
            aqAdapter = new AQuery(view);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            aqAdapter.id(holder.imageView).image(EventsDetailsModel.getInstance().album.photos_set.get(position).get_photo,true,true);
        }

        @Override
        public int getItemCount() {
            return EventsDetailsModel.getInstance().album.photos_set.size();
        }
    }
}
