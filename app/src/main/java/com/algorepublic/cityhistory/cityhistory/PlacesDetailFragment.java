package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.lucasr.twowayview.ItemClickSupport;
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
    TextView title, disc ,type ,added  , added_by,home_detail;
    BaseClass base;
    ViewPager pager;
    CustomPagerAdapter mCustomPagerAdapter;
    PlacesDetailService obj;
    static int PlaceId;
    GoogleMap googleMap;
    MapView mapView;
    static String coordinates;
    private TwoWayView mRecyclerView;
    ImageView imageView;
    int Position=0;
    AdView adView;


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


        View view = inflater.inflate(R.layout.places_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        base = ((BaseClass) getActivity().getApplicationContext());
        adView = (AdView) view.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        pager = (ViewPager) view.findViewById(R.id.pager);

        title = (TextView) view.findViewById(R.id.head_places);
        mRecyclerView = (TwoWayView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        disc = (TextView) view.findViewById(R.id.disc_places);
        type = (TextView) view.findViewById(R.id.type_places);
        added = (TextView) view.findViewById(R.id.added_places);
        imageView = (ImageView) view.findViewById(R.id.home_detail);
        home_detail = (TextView) view.findViewById(R.id.title_header);
        added_by = (TextView) view.findViewById(R.id.added_by_places);
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
        obj = new PlacesDetailService(getActivity().getApplicationContext());
        obj.CityPlacesDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                pager.setCurrentItem(position);
                aq.id(R.id.disc_places).text(Html.fromHtml(PlacesDetailModel.getInstance().album.photos_set.get(Position).description));
            }
        });

    }

    private void createMapView(){

        try {
            if(googleMap == null){

                if (Build.VERSION.SDK_INT < 16) {
                    googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.places_mapView)).getMap();

                } else {
                    googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.places_mapView)).getMap();

                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }

    }

    public void CityPlacesDetails(Object caller, Object model) {
        PlacesDetailModel.getInstance().album.photos_set.clear();
        PlacesDetailModel.getInstance().setList((PlacesDetailModel) model);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(),mRecyclerView));
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.head_places).text(PlacesDetailModel.getInstance().title);
        aq.id(R.id.title_header).text(PlacesDetailModel.getInstance().title);
        aq.id(R.id.added_places).text(PlacesDetailModel.getInstance().added);
        aq.id(R.id.added_by_places).text(capSentences(PlacesDetailModel.getInstance().user.username));
        LatLong();
        if (PlacesDetailModel.getInstance().type.type.equals("PL")){
            aq.id(R.id.type_places).text("Places");
        }
    }

    private void LatLong(){
        if (PlacesDetailModel.getInstance().address.isEmpty()){
          aq.id(R.id.places_mapView).visibility(View.GONE);
        }else{
        coordinates=PlacesDetailModel.getInstance().address;
        String [] splited = coordinates.split("\\s+");
        String Lat = splited[0];
        String Lng =  splited [1];
        addMarker(Double.valueOf(Lat), Double.valueOf(Lng), PlacesDetailModel.getInstance().title);
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
    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        ImageView imageView;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            Position =0;
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

                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Position = position;
            View itemView = mLayoutInflater.inflate(R.layout.places_pager_item, container, false);
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_people);
            aq.id(imageView).progress(progressBar).image(PlacesDetailModel.getInstance().album.photos_set.get(Position).get_photo, true, true);
            aq.id(R.id.disc_places).text(Html.fromHtml(PlacesDetailModel.getInstance().album.photos_set.get(Position).description));
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }

    private class GalleryPagerAdapter {
        public GalleryPagerAdapter(FragmentActivity activity) {
        }
    }
    private String capSentences( String text ) {

        return text.substring( 0, 1 ).toUpperCase() + text.substring( 1 ).toLowerCase();
    }
    public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {


        private final Context mContext;
        AQuery aqAdapter;

        public class SimpleViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public SimpleViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.photo_image);
            }
        }

        public LayoutAdapter(Context context, TwoWayView recyclerView) {


            mContext = context;
            mRecyclerView = recyclerView;
        }


        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.places_item_gallery, parent, false);
            aqAdapter = new AQuery(view);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            aqAdapter.id(holder.imageView).image(PlacesDetailModel.getInstance().album.photos_set.get(position).get_photo, true, true);
        }

        @Override
        public int getItemCount() {

            Log.e("Size", PlacesDetailModel.getInstance().album.photos_set.size() + "");
            try {
                return PlacesDetailModel.getInstance().album.photos_set.size();
            } catch (NullPointerException e) {
                aq.id(R.id.list).visibility(View.GONE);
                return 0;
            }
        }
    }
}
















