package com.algorepublic.cityhistory.cityhistory;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

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
    TextView title, disc ,type ,added  , added_by;
    BaseClass base;
    ViewPager pager;
    CustomPagerAdapter mCustomPagerAdapter;
    PlacesDetailService obj;
    Button map;
    static int PlaceId;
    GoogleMap googleMap;
    MapView mapView;
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private static final String ARG_LAYOUT_ID = "layout_id";
    private TwoWayView mRecyclerView;

    int Position=0;


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
//        mapView = (MapView) view.findViewById(R.id.mapView);
//        googleMap = mapView.getMap();
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
        type = (TextView) view.findViewById(R.id.type);
        added = (TextView) view.findViewById(R.id.added);
        added_by = (TextView) view.findViewById(R.id.added_by);
        obj = new PlacesDetailService(getActivity().getApplicationContext());
        obj.CityPlacesDetails(PlaceId, true, new CallBack(this, "CityPlacesDetails"));
        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);

        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                pager.setCurrentItem(position);
                aq.id(R.id.disc).text(Html.fromHtml(PlacesDetailModel.getInstance().album.photos_set.get(Position).description));
            }
        });
        createMapView();
        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

    private void createMapView(){

        try {
            if(googleMap == null){
                if (Build.VERSION.SDK_INT < 21) {
                    googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();
                } else {
                    googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView)).getMap();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    public void CityPlacesDetails(Object caller, Object model) {

        PlacesDetailModel.getInstance().setList((PlacesDetailModel) model);
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(),mRecyclerView));
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.title).text(PlacesDetailModel.getInstance().title);
        aq.id(R.id.type).text(PlacesDetailModel.getInstance().type.type);
        aq.id(R.id.added).text(PlacesDetailModel.getInstance().album.added);
        aq.id(R.id.added_by).text(PlacesDetailModel.getInstance().user.username);
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
                aq.id(R.id.pager).visibility(Constants.GONE);
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
//            ImageLoader.getInstance().displayImage(PlacesDetailModel.getInstance().album.photos_set.get(Position).get_photo, imageView);
            Log.e("positon",position + "");
            aq.id(R.id.disc).text(Html.fromHtml(PlacesDetailModel.getInstance().album.photos_set.get(Position).description));


            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((RelativeLayout)object);
        }
//        public void loadImage()
//        {
//            ImageLoader.getInstance().displayImage(PlacesDetailModel.getInstance().album.photos_set.get(Position).get_photo, imageView);
//
//        }
    }

    private class GalleryPagerAdapter {
        public GalleryPagerAdapter(FragmentActivity activity) {
        }
    }

    public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {


        private final Context mContext;
//        ArrayList<ItemDetails> itemDetailses = new ArrayList<>();
        AQuery aqAdapter;

        public class SimpleViewHolder extends RecyclerView.ViewHolder {
            ImageView  imageView;
            public SimpleViewHolder(View view) {
                super(view);
         imageView = (ImageView) view.findViewById(R.id.photo_image);
            }
        }

        public LayoutAdapter(Context context, TwoWayView recyclerView) {

//            for(int loop=0;loop< PlacesDetailModel.getInstance().album.photos_set.size();loop++) {
//                ItemDetails itemDetails = new ItemDetails();
//                itemDetails.setTitle(PlacesDetailModel.getInstance().album.photos_set.get(loop).get_photo);
//                itemDetailses.add(itemDetails);
//
//            }
            mContext = context;
            mRecyclerView = recyclerView;
        }



        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
            aqAdapter = new AQuery(view);
            return new SimpleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {

//            holder.imageView.setImage(PlacesDetailModel.getInstance().album.photos_set.get(position).get_photo, true, true);
            aqAdapter.id(holder.imageView).image(PlacesDetailModel.getInstance().album.photos_set.get(position).get_photo,true,true);
        }

        @Override
        public int getItemCount() {
            return PlacesDetailModel.getInstance().album.photos_set.size();
        }
    }

}
















