package com.algorepublic.cityhistory.cityhistory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidquery.AQuery;

import Model.EventsModel;
import Services.CallBack;
import Services.EventsService;

/**
 * Created by waqas on 6/18/15.
 */
public class EventsFragment extends BaseFragment {

    static String CategoriesId;
    static String CityId;
    private BaseClass base;
    AQuery aq;
    GridView EventView;
    int index;
    EventsAdapter selectEventsAdapter;
    EventsService obj;

    public static EventsFragment newInstance(String id,String CategoryId) {
        EventsFragment fragment = new EventsFragment();
        CityId=id;
        CategoriesId=CategoryId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        EventView = (GridView) view.findViewById(R.id.events);
        EventView.setNumColumns(2);

        obj = new EventsService(getActivity().getApplicationContext());
        obj.EventsDetails(CityId,CategoriesId,true, new CallBack(this, "EventsDetails"));
        EventView.setOnScrollListener(new EndlessScrollListener() {
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
    public void EventsDetails(Object caller, Object model) {
        if(index==0)
        {
            EventsModel.getInstance().setList((EventsModel) model);
            if (EventsModel.getInstance().previous ==null) {
                aq.id(R.id.events).itemClicked(new EventsListner());
                selectEventsAdapter = new EventsAdapter(getActivity());
                EventView.setAdapter(selectEventsAdapter);
            }
        }else{
            EventsModel.getInstance().appendList((EventsModel) model);
            selectEventsAdapter.notifyDataSetChanged();
        }
    }
    public void GetResults(int page){
        obj.EventDetailPage(CityId,CategoriesId,true, page, new CallBack(this, "EventsDetails"));

    }

    public class EventsListner implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int cityId = EventsModel.getInstance().results.get(position).id;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pagerForList, EventsDetailFragment.newInstance(cityId))
                    .addToBackStack(null).commit();

        }
    }

}


