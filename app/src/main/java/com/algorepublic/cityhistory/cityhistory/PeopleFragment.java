package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;

import Model.EventsModel;
import Model.PeoplesModel;
import Services.CallBack;
import Services.PeoplesService;

/**
 * Created by waqas on 6/18/15.
 */
public class PeopleFragment extends BaseFragment {

    static String CategoriesId;
    static String CityId;
    private BaseClass base;
    AQuery aq;
    ListView PeopleView;
    int index;
    PeoplesAdapter selectPeopleAdapter;
    PeoplesService obj;


    public static PeopleFragment newInstance(String id,String Categoryid) {
        PeopleFragment fragment = new PeopleFragment();
        CityId=id;
        CategoriesId = Categoryid;

        Log.e("PeopleFrag",Categoryid + "" + CityId);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.people_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        PeopleView =(ListView)view.findViewById(R.id.peoples);
        obj = new PeoplesService(getActivity().getApplicationContext());
        obj.PeoplesDetails(CityId,CategoriesId,true, new CallBack(this, "PeoplesDetails"));

        PeopleView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                index = page;
                GetResults(page);

            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        index = 0;

    }
    public void PeoplesDetails(Object caller, Object model) {
        if(index==0)
        {
            PeoplesModel.getInstance().setList((PeoplesModel) model);
            if (PeoplesModel.getInstance().previous ==null) {
                aq.id(R.id.peoples).itemClicked(new EventsListner());
                selectPeopleAdapter = new PeoplesAdapter(getActivity());
                PeopleView.setAdapter(selectPeopleAdapter);
            }
        }else{
            PeoplesModel.getInstance().appendList((PeoplesModel) model);
            selectPeopleAdapter.notifyDataSetChanged();
        }
    }
    public void GetResults(int page){
        obj.PeoplesDetailPage(true, page, new CallBack(this, "EventsDetails"));

    }

    public class EventsListner implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int cityId = EventsModel.getInstance().results.get(position).id;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pagerForList, PeoplesDetailFragment.newInstance(cityId))
                    .addToBackStack(null).commit();

        }
    }
}
