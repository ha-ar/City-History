package com.algorepublic.cityhistory.cityhistory;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by waqas on 6/23/15.
 */
public class PagerFragment extends Fragment {


    AQuery aq;
    static PagerAdapter pagerAdapter;
    private static final String POSITION = "position";
    static String cityId;
    static String CategoriesId;
    private int currentColor = 0xFF666666;
    private final Handler handler = new Handler();
    private PagerSlidingTabStrip tabs;

    private Drawable oldBackground = null;
    public static PagerFragment newInstance(String cityid, boolean comingFromSaved) {
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
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(pager);
//        changeColor(currentColor);

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
            switch (position)
            {
                case 0:
                    CategoriesId="1";
                    return PlacesFragment.newInstance(cityId,CategoriesId);
                case 1:
                    CategoriesId="2";
                    return EventsFragment.newInstance(cityId,CategoriesId);
                case 2:
                    CategoriesId="3";
                    return PeopleFragment.newInstance(cityId,CategoriesId);
                case 3:
                    CategoriesId="4";
                    return BlogsFragment.newInstance(cityId,CategoriesId);
            }
            return null;
        }

    }

//    private void changeColor(int newColor) {
//
//        tabs.setIndicatorColor(newColor);
//
//        // change ActionBar color just if an ActionBar is available
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//            Drawable colorDrawable = new ColorDrawable(newColor);
//            Drawable bottomDrawable = getResources().getDrawable(R.drawable.bottom);
//            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });
//
//            if (oldBackground == null) {
//
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    ld.setCallback(drawableCallback);
//                } else {
//                  getActivity().  getActionBar().setBackgroundDrawable(ld);
//                }
//
//            } else {
//
//                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });
//
//                // workaround for broken ActionBarContainer drawable handling on
//                // pre-API 17 builds
//                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    td.setCallback(drawableCallback);
//                } else {
//                   getActivity(). getActionBar().setBackgroundDrawable(td);
//                }
//
//                td.startTransition(200);
//
//            }
//
//            oldBackground = ld;
//
//            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
//          getActivity().  getActionBar().setDisplayShowTitleEnabled(false);
//           getActivity(). getActionBar().setDisplayShowTitleEnabled(true);
//
//        }
//
//        currentColor = newColor;
//
//    }
//
//    public void onColorClicked(View v) {
//
//        int color = Color.parseColor(v.getTag().toString());
//        changeColor(color);
//
//    }
//    private Drawable.Callback drawableCallback = new Drawable.Callback() {
//        @Override
//        public void invalidateDrawable(Drawable who) {
//          getActivity().  getActionBar().setBackgroundDrawable(who);
//        }
//
//        @Override
//        public void scheduleDrawable(Drawable who, Runnable what, long when) {
//            handler.postAtTime(what, when);
//        }
//
//        @Override
//        public void unscheduleDrawable(Drawable who, Runnable what) {
//            handler.removeCallbacks(what);
//        }
//    };
}
