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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

import Model.BlogsDetailsModel;
import Model.PlacesDetailModel;
import Services.BlogsDetailService;
import Services.CallBack;

/**
 * Created by waqas on 7/6/15.
 */
public class BlogsDetailFragment extends BaseFragment {


    AQuery aq;
    TextView title, disc,type ,added , added_by,home_detail;
    BaseClass base;
    ViewPager pager;
    ImageBlogAdapter mCustomPagerAdapter;
    BlogsDetailService obj;
    static int PlaceId;
    static int p;
    ImageView imageView;
    AdView adView;
    GoogleMap googleMap;
    private TwoWayView mRecyclerView;
    static String coordinates;
    ArrayList<ItemDetails> details;
    int Position=0;
    ImageView image;
    ArrayList<String> stringArrayList;
    static String subyear;
    public ArrayList<String> years;
    private int height = 5 ;
    ViewPager blogsPager;
    private PagerSlidingTabStrip tabs;


    public static BlogsDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInBlogs ", String.valueOf(id));
        PlaceId = id;
        return new BlogsDetailFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.blogs_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        base = ((BaseClass) getActivity().getApplicationContext());
        adView = (AdView) view.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        pager = (ViewPager) view.findViewById(R.id.blog_pager);
        blogsPager = (ViewPager) view.findViewById(R.id.blogs_pagerForList);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.blogs_tabs);
        image = (ImageView) view.findViewById(R.id.blog_imgLogo);
        title = (TextView) view.findViewById(R.id.blog_head);
        mRecyclerView = (TwoWayView) view.findViewById(R.id.blog_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setOrientation(TwoWayLayoutManager.Orientation.HORIZONTAL);
        disc = (TextView) view.findViewById(R.id.blog_disc);
        type = (TextView) view.findViewById(R.id.blog_type);
        added = (TextView) view.findViewById(R.id.blog_added);
        imageView = (ImageView) view.findViewById(R.id.home_detail);
        home_detail = (TextView) view.findViewById(R.id.title_header);
        added_by = (TextView) view.findViewById(R.id.blog_added_by);
        aq.id(R.id.blog_mapView).visibility(View.GONE);
        pager = (ViewPager) view.findViewById(R.id.blog_pager);
        mCustomPagerAdapter = new ImageBlogAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        showPager();
        showPagerSecond();
        obj = new BlogsDetailService(getActivity().getApplicationContext());
        obj.BlogsDetails(PlaceId, true, new CallBack(this, "BlogsDetails"));
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
        showPager();
        showPagerSecond();
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                pager.setCurrentItem(position);
                aq.id(R.id.people_disc).text(Html.fromHtml(BlogsDetailsModel.getInstance().description));
            }
        });
    }

    public void showPager(){

        aq.id(R.id.blog_imgLogo).visibility(View.GONE);
        aq.id(R.id.blog_pager).visibility(View.VISIBLE);
        aq.id(R.id.blog_list).visibility(View.VISIBLE);

    }

    public void showImage(){
        aq.id(R.id.blog_imgLogo).visibility(View.VISIBLE);
        aq.id(R.id.blog_pager).visibility(View.GONE);
        aq.id(R.id.blog_list).visibility(View.GONE);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        showPager();
        showPagerSecond();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showPager();
        showPagerSecond();
    }

    @Override
    public void onResume() {
        super.onResume();
        showPager();
        showPagerSecond();
    }
    public void hidePagerSecond(){
        aq.id(R.id.location_blogs_photosset).visibility(View.GONE);
    }
    public void showPagerSecond(){
        aq.id(R.id.location_blogs_photosset).visibility(View.VISIBLE);

    }

    public void BlogsDetails(Object caller, Object model) {

        BlogsDetailsModel.getInstance().setList((BlogsDetailsModel) model);

        if (BlogsDetailsModel.getInstance().album == null || BlogsDetailsModel.getInstance().album.photos_set.size()==0){
            showImage();
            aq.id(R.id.blog_imgLogo).image(BlogsDetailsModel.getInstance().get_photo);
            aq.id(R.id.blog_disc).text(Html.fromHtml(BlogsDetailsModel.getInstance().description));
        }else{
            showPager();
        }
        if (BlogsDetailsModel.getInstance().articledetails_set.size() == 0 || BlogsDetailsModel.getInstance().articledetails_set == null
                || BlogsDetailsModel.getInstance().articledetails_set.get(0).album.photos_set.size()==0){
            Log.e("subyear", "subyear");
            hidePagerSecond();
        }
        else {
            showPagerSecond();
        }

        mCustomPagerAdapter = new ImageBlogAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(), mRecyclerView));
        upDateData();

        stringArrayList = new ArrayList<String>();

        for(int loop=0;loop< BlogsDetailsModel.getInstance().articledetails_set.size();loop++)
        {

            subyear=BlogsDetailsModel.getInstance().articledetails_set.get(loop).original_date;
            years.add(BlogsDetailsModel.getInstance().articledetails_set.get(loop).original_date);
            Log.e("subyear",subyear);
            String [] splited = subyear.split("-");
            String year = splited[0];
            if(!stringArrayList.contains(year))
                stringArrayList.add(year);
        }
        Log.e("1","2");
        PageAdapter pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
        blogsPager.setAdapter(pagerAdapter);

        tabs.setIndicatorColor(getResources().getColor(R.color.Orange));
        tabs.setTextColor(getResources().getColor(R.color.Black));
        tabs.setIndicatorHeight(height);
        tabs.setDividerColor(getResources().getColor(R.color.Black));
        tabs.setViewPager(blogsPager);


    }

    public class PageAdapter extends FragmentStatePagerAdapter {

        private String[] TITLES = {"All", "New", "Used", "Rental", "eBook"};
        private String[] array ;

        public PageAdapter(FragmentManager fm) {
            super(fm);
            Log.e("1", stringArrayList.toArray(new String[stringArrayList.size()]).toString());
            // TITLES = stringArrayList.toArray(new String[stringArrayList.size()]);
            array = stringArrayList.toArray(new String[stringArrayList.size()]);
            for (int loop =0;loop<array.length;loop++){
                Log.e("Array Value",array[loop] +" "+ years.get(loop));
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
        aq.id(R.id.blog_head).text(BlogsDetailsModel.getInstance().title);
        aq.id(R.id.title_header).text(BlogsDetailsModel.getInstance().title);
        aq.id(R.id.blog_disc).text(Html.fromHtml(BlogsDetailsModel.getInstance().description));
        aq.id(R.id.blog_added).text(BlogsDetailsModel.getInstance().added);
        if (BlogsDetailsModel.getInstance().address.isEmpty()){
            aq.id(R.id.blog_layout_map).visibility(View.GONE);
            aq.id(R.id.location).visibility(View.GONE);
        }
        aq.id(R.id.blog_added_by).text(capSentences(BlogsDetailsModel.getInstance().user.username));
        LatLong();
        if (BlogsDetailsModel.getInstance().type.type.equals("BL")){
            aq.id(R.id.blog_type).text("Blog");
        }
    }
    private void LatLong(){
        if (BlogsDetailsModel.getInstance().address.isEmpty()){
            aq.id(R.id.blog_mapView).visibility(View.GONE);
        }else{
            coordinates=BlogsDetailsModel.getInstance().address;
            String [] splited = coordinates.split("\\s+");
            String Lat = splited[0];
            String Lng =  splited [1];
            addMarker(Double.valueOf(Lat), Double.valueOf(Lng), BlogsDetailsModel
                    .getInstance().title);
        }}
    private void addMarker(double Lat,double Lon,String Title) {

        if(googleMap !=null) {

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
    private void createMapView(){

        try {
            if(googleMap == null){

                if (Build.VERSION.SDK_INT < 16) {
                    googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.blog_mapView)).getMap();

                } else {
                    googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.blog_mapView)).getMap();

                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }

    }


    class ImageBlogAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public ImageBlogAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            try {
                Log.e("count",BlogsDetailsModel.getInstance().album.photos_set.size() + "");
                return BlogsDetailsModel.getInstance().album.photos_set.size();
            }catch (NullPointerException e){
                aq.id(R.id.pager).visibility(Constants.GONE);
                return 0;
            }
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.blog_image_pager, container, false);
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_blog);
            aq.id(imageView).progress(progressBar).image(BlogsDetailsModel.getInstance().album.photos_set.get(Position).get_photo, true, true);
            ImageLoader.getInstance().displayImage(BlogsDetailsModel.getInstance().album.photos_set.get(position).get_photo, imageView);
            container.addView(itemView);
            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
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
            if (BlogsDetailsModel.getInstance().album.photos_set==null){
                aq.id(R.id.people_list).visibility(View.GONE);
            }else{
                aqAdapter.id(holder.imageView).image(BlogsDetailsModel.getInstance().album.photos_set.get(position).get_photo, true, true);
            }}


        @Override
        public int getItemCount() {
            try {
                return BlogsDetailsModel.getInstance().album.photos_set.size();
            } catch (NullPointerException e) {
                aq.id(R.id.people_list).visibility(View.GONE);
                return 0;
            }
        }
    }
}
