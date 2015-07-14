package com.algorepublic.cityhistory.cityhistory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import Model.BlogsDetailsModel;
import Services.BlogsDetailService;
import Services.CallBack;

/**
 * Created by waqas on 7/6/15.
 */
public class BlogsDetailFragment extends BaseFragment {


    AQuery aq;
    TextView title, disc;
    BaseClass base;
    ViewPager pager;
    ImageBlogAdapter mCustomPagerAdapter;
    BlogsDetailService obj;
    static int PlaceId;
    static int p;
    ArrayList<ItemDetails> details;


    public static BlogsDetailFragment newInstance(int id) {
        Log.e("CitrdeatilsInplaces ", String.valueOf(id));
        PlaceId = id;
        return new BlogsDetailFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.blogs_detail_fragment, container, false);
        aq = new AQuery(getActivity(), view);
        base = ((BaseClass) getActivity().getApplicationContext());
        pager = (ViewPager) view.findViewById(R.id.blogs_pager);
        mCustomPagerAdapter = new ImageBlogAdapter(getActivity());
        pager.setAdapter(mCustomPagerAdapter);
        title = (TextView) view.findViewById(R.id.title_blogs);
        disc = (TextView) view.findViewById(R.id.disc_blogs);
        obj = new BlogsDetailService(getActivity().getApplicationContext());
        obj.BlogsDetails(PlaceId, true, new CallBack(this, "BlogsDetails"));
        return view;
    }
    @Override
    public void onResume() {

        super.onResume();
    }
    public void BlogsDetails(Object caller, Object model) {

        BlogsDetailsModel.getInstance().setList((BlogsDetailsModel) model);
        mCustomPagerAdapter.notifyDataSetChanged();
        upDateData();

    }

    private void upDateData(){
        aq.id(R.id.title_blogs).text(BlogsDetailsModel.getInstance().title);
        aq.id(R.id.disc_events).text(Html.fromHtml(BlogsDetailsModel.getInstance().description));

    }


    class ImageBlogAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public ImageBlogAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

            try {
                Log.e("count",BlogsDetailsModel.getInstance().album.photos_set.size() + "");
                return BlogsDetailsModel.getInstance().album.photos_set.size();
            }catch (NullPointerException e){
                aq.id(R.id.pager).visibility(Constants.GONE);
                return 0;
            }
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.blog_image_pager, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView_blog);

            ImageLoader.getInstance().displayImage(BlogsDetailsModel.getInstance().album.photos_set.get(position).get_photo, imageView);
            container.addView(itemView);
            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}
