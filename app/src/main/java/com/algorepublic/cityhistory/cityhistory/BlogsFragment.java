package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;

import Model.BlogsModel;
import Services.BlogService;
import Services.CallBack;

/**
 * Created by waqas on 6/18/15.
 */
public class BlogsFragment extends BaseFragment {

    static String CategoriesId;
    static String CityId;
    private BaseClass base;
    AQuery aq;
    ListView BlogView;
    int index;
    BlogsAdapter selectEventsAdapter;
    BlogService obj;

    public static BlogsFragment newInstance(String id,String CategoryId) {
        BlogsFragment fragment = new BlogsFragment();
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



        View view = inflater.inflate(R.layout.blogs_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        BlogView = (ListView) view.findViewById(R.id.blogs);
        base = ((BaseClass) getActivity().getApplicationContext());
        obj = new BlogService(getActivity().getApplicationContext());
        obj.BlogsDetails(CityId,CategoriesId,true, new CallBack(this, "BlogsDetails"));
        BlogView.setOnScrollListener(new EndlessScrollListener() {
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
    public void BlogsDetails(Object caller, Object model) {
        if(index==0)
        {
            BlogsModel.getInstance().setList((BlogsModel) model);
            if (BlogsModel.getInstance().previous ==null) {
                aq.id(R.id.blogs).itemClicked(new EventsListner());
                selectEventsAdapter = new BlogsAdapter(getActivity());
                BlogView.setAdapter(selectEventsAdapter);
            }
        }else{
            BlogsModel.getInstance().appendList((BlogsModel) model);
            selectEventsAdapter.notifyDataSetChanged();
        }
    }
    public void GetResults(int page){
        obj.BlogsDetailPage(true, page, new CallBack(this, "EventsDetails"));

    }



    public class EventsListner implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int cityId = BlogsModel.getInstance().results.get(position).id;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pagerForList, BlogsDetailFragment.newInstance(cityId))
                    .addToBackStack(null).commit();

        }
    }
}