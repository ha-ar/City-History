package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.androidquery.AQuery;

import Services.CallBack;
import Services.PlacesDetailService;

/**
 * Created by waqas on 8/11/15.
 */
public class Twentyfifteen extends BaseFragment {
    AQuery aq;
    GridView DetailView;
    BaseClass baseClass;
   static int DetailsId ;
    PlacesDetailService obj;

    public static Twentyfifteen newInstance(int id) {

        Twentyfifteen fragment = new Twentyfifteen();
        DetailsId = id;
        Log.e("DetailsId", String.valueOf(id));
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.twentyfifteen, container, false);
        aq = new AQuery(getActivity(), view);
        DetailView = (GridView) view.findViewById(R.id.fifteen_details);



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obj=new PlacesDetailService(getActivity().getApplicationContext());
        obj.CityPlacesDetails( true,DetailsId, new CallBack(this, "CityPlacesDetails"));


    }
}
