package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;

/**
 * Created by waqas on 6/23/15.
 */
public class PagerFragment extends Fragment {


    AQuery aq;
    static PagerAdapter pagerAdapter;
    private static final String POSITION = "position";
    static String cityId;
    static String CategoriesId;
    private int currentColor = 0xFFCC33;
    private int height = 5 ;
    private final Handler handler = new Handler();
    private PagerSlidingTabStrip tabs;
    AdView adView;

    public static PagerFragment newInstance(String cityid) {
        PagerFragment fragment = new PagerFragment();
        cityId = cityid;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.pagerForList);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(4);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        tabs.setIndicatorColor(getResources().getColor(R.color.Orange));
        tabs.setTextColor(getResources().getColor(R.color.Black));
        tabs.setIndicatorHeight(height);
        tabs.setDividerColor(getResources().getColor(R.color.Black));
        adView = (AdView) view.findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
//        ActionBar actionBar = getActivity().getActionBar();
//        actionBar.setBackgroundDrawable(R.drawable.city);

         return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Places","Events","People","Blogs"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            BaseClass.tracker().send(new HitBuilders.EventBuilder("Pager View", "Tap")
                    .setAction("Taped")
                    .setLabel("Pager View Action").build());
            switch (position)
            {
                case 0:
                    CategoriesId="1";
                    Log.e("1","first");
                    return PlacesFragment.newInstance(cityId,CategoriesId);
                case 1:
                    CategoriesId="2";
                    Log.e("2","second");
                    return EventsFragment.newInstance(cityId,CategoriesId);
                case 2:
                    CategoriesId="3";
                    Log.e("3","third");
                    return PeopleFragment.newInstance(cityId,CategoriesId);
                case 3:
                    CategoriesId="4";
                    Log.e("4","forth");
                    return BlogsFragment.newInstance(cityId,CategoriesId);
            }
            return null;
        }

    }

}
