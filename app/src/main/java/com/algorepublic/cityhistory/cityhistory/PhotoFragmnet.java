package com.algorepublic.cityhistory.cityhistory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import Model.PlacesDetailModel;
import Services.CallBack;
import Services.PlacesDetailService;

/**
 * Created by ahmad on 8/17/15.
 */
public class PhotoFragmnet extends Fragment {

    View view;
    static String date;
    GridView photoGridView;
    BaseClass baseClass;
    AQuery aq;
    PlacesDetailService obj;
    PhotoAdapter photoAdapter;
    static int placeId;
    int index;
    ArrayList<PhotoDetails> arrayList;
    ProgressDialog progressDialog;
    PhotoDetails itemDetails;
    static int position;
    ScrollView scrollView;

    public static PhotoFragmnet getInstance(String Date, int PlaceId, int Position){
        PhotoFragmnet _obj = new PhotoFragmnet();
        date = Date;
        position = Position;
        placeId = PlaceId;
        return _obj;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.photos_fragment,container,false);
        baseClass = (BaseClass) getActivity().getApplicationContext();
        aq = new AQuery(getActivity());
        arrayList = new ArrayList<>();
        itemDetails = new PhotoDetails();
        scrollView = (ScrollView) view.findViewById(R.id.parents_scrollview);
        photoGridView = (GridView) view.findViewById(R.id.city_details);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        obj =  new PlacesDetailService(getActivity().getApplicationContext());
        obj.CityPlacesDetails(true,placeId,new CallBack(this,"PlacePhotos"));
//        photoGridView.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//
//                index = page;
//                GetSearchMoreResults(index);
//            }
//        });

        photoGridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        return view;
    }
    public void GetSearchMoreResults(int page){
        obj.CityPlacesDetails(true, page, new CallBack(this, "CityDetails"));

    }
    static int outer, inner;

    public void PlacePhotos(Object caller, Object model){
        progressDialog.dismiss();
        ArrayList<PhotoDetails> results = new ArrayList<PhotoDetails>();
        PlacesDetailModel.getInstance().articledetails_set.clear();
        PlacesDetailModel.getInstance().setList((PlacesDetailModel) model);
        for (outer = 0;outer<PlacesDetailModel.getInstance().articledetails_set.size();outer++) {
            for (inner = 0; inner < PlacesDetailModel.getInstance().articledetails_set.get(outer).album.photos_set.size(); inner++) {

                if (PlacesDetailModel.getInstance().articledetails_set.get(outer).original_date == null){
                    itemDetails.setPhoto(PlacesDetailModel.getInstance().articledetails_set.get(position)
                            .album.photos_set.get(inner).get_photo);

                    results.add(itemDetails);
                }else
                if(PlacesDetailModel.getInstance().articledetails_set.get(outer).original_date.equalsIgnoreCase(date)) {

                    Log.e("Date frag:", PlacesDetailModel.getInstance().articledetails_set.get(outer).original_date+" "+date);
                    itemDetails.setPhoto(PlacesDetailModel.getInstance().articledetails_set.get(outer)
                            .album.photos_set.get(inner).get_photo);

                    itemDetails.setDate(PlacesDetailModel.getInstance().articledetails_set.get(outer).original_date);
                    results.add(itemDetails);
                }
            }
        }
        Log.e("result length:", results.size()+"");
        photoAdapter = new PhotoAdapter(getActivity(),results);
        photoGridView.setNumColumns(5);
//        photoGridView.setExpanded(true);
        photoGridView.setAdapter(photoAdapter);
    }

}
