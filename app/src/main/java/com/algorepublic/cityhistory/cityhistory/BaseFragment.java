package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;

/**
 * Created by waqas on 6/18/15.
 */
public class BaseFragment extends Fragment {

    private AQuery aq;
    private ViewPager mPager;
    BaseClass baseClass;
//    static SelectCityAdapter selectCityAdapter;

    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
