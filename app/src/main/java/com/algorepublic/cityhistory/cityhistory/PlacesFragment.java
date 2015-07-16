package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidquery.AQuery;

import Model.PlacesModel;
import Services.CallBack;
import Services.PlacesService;

/**
 * Created by waqas on 6/18/15.
 */
public class PlacesFragment extends BaseFragment {
    AQuery aq;
    private int cityId;
    private BaseClass base;
    PlacesService obj;
    PlacesAdapter selectCityAdapter;
    GridView CityView;
    int index;

    static String CategoriesId;
    static String CityId;
    public static PlacesFragment newInstance(String id,String CategoryId) {

        PlacesFragment fragment = new PlacesFragment();
        CityId   = id ;
        CategoriesId =CategoryId;
        Log.e("City id in places ",CityId + CategoriesId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.places_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        CityView = (GridView) view.findViewById(R.id.city_details);
        CityView.setNumColumns(2);
        base = ((BaseClass) getActivity().getApplicationContext());
        obj = new PlacesService(getActivity().getApplicationContext());
        obj.CityDetails(CityId,CategoriesId,true, new CallBack(this, "CityDetails"));
        CityView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount){
                index = page;
                GetSearchMoreResults(page);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        index = 0;

    }

    public void CityDetails(Object caller, Object model) {
        if(index==0)
        {
            PlacesModel.getInstance().setList((PlacesModel) model);
            if (PlacesModel.getInstance().previous ==null) {
                aq.id(R.id.city_details).itemClicked(new CityListner());
                selectCityAdapter = new PlacesAdapter(getActivity());
                CityView.setAdapter(selectCityAdapter);
            }
        }else{

            PlacesModel.getInstance().appendList((PlacesModel) model);
            selectCityAdapter.notifyDataSetChanged();
        }
    }
    public void GetSearchMoreResults(int page){
        obj.CityDetailPage(true, page, new CallBack(PlacesFragment.this, "CityDetails"));

    }



    public class CityListner implements AdapterView.OnItemClickListener {



        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.e("a","aaaaaaa");
            int cityId = PlacesModel.getInstance().results.get(position).id;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pagerForList, PlacesDetailFragment.newInstance(cityId))
                    .addToBackStack(null).commit();

        }
    }




}


