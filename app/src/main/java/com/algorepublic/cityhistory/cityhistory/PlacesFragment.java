package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;

import Model.CityPlacesModel;
import Services.CallBack;
import Services.CityDetailService;

/**
 * Created by waqas on 6/18/15.
 */
public class PlacesFragment extends BaseFragment {
    AQuery aq;
    private int cityId;
    private BaseClass base;
    CityDetailService obj;
    CitysAdapter selectCityAdapter;
    ListView CityView;
    int index;
    static String CityId;
    public static PlacesFragment newInstance(String id) {

        PlacesFragment fragment = new PlacesFragment();
        CityId   = id ;
        Log.e("City id in places ",CityId);
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


        CityView = (ListView) view.findViewById(R.id.city_details);
        base = ((BaseClass) getActivity().getApplicationContext());

        obj = new CityDetailService(getActivity().getApplicationContext());
        obj.CityDetails(CityId,true, new CallBack(this, "CityDetails"));
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
            CityPlacesModel.getInstance().setList((CityPlacesModel) model);
            if (CityPlacesModel.getInstance().previous ==null) {
                aq.id(R.id.city_details).itemClicked(new CityListner());
                selectCityAdapter = new CitysAdapter(getActivity());
                CityView.setAdapter(selectCityAdapter);
            }
        }else{
            CityPlacesModel.getInstance().appendList((CityPlacesModel) model);
            selectCityAdapter.notifyDataSetChanged();
        }
    }
    public void GetSearchMoreResults(int page){
        obj.CityDetailPage(true,page, new CallBack(this, "CityDetails"));

    }



    public class CityListner implements AdapterView.OnItemClickListener {



        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.e("a","aaaaaaa");
            int cityId = CityPlacesModel.getInstance().results.get(position).id;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pagerForList, CityPlacesDetailFragment.newInstance(cityId))
                    .addToBackStack(null).commit();

            Log.e("Places for city ", String.valueOf(cityId));
        }
    }




}


