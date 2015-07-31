package com.algorepublic.cityhistory.cityhistory;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import com.androidquery.AQuery;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.GcmBroadcastReceiver;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import Model.SelectCityModel;
import Services.CallBack;
import Services.SelectCityService;


public class BaseActivity extends FragmentActivity {
    private AQuery aq;
    private BaseClass base;
    static int p=1;
    private SelectCityService obj;
    MenuDrawer mDrawerLeft;
    AdView adView;
    public static    ListView CityListView;
    Switch button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawerLeft = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, Position.LEFT, MenuDrawer.MENU_DRAG_CONTENT);
        mDrawerLeft.setContentView(R.layout.activity_main);
        mDrawerLeft.setMenuView(R.layout.layout_dropdownmenu);
        mDrawerLeft.setDrawOverlay(true);
        mDrawerLeft.setSlideDrawable(R.drawable.menu);
        mDrawerLeft.setDrawerIndicatorEnabled(true);
        mDrawerLeft.setAllowIndicatorAnimation(true);
        adView = (AdView) findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().build());
        aq = new AQuery(BaseActivity.this);
        base = ((BaseClass)getApplicationContext());
        obj = new SelectCityService(BaseActivity.this);
        obj.SelectCity(true, new CallBack(this, "UpDateList"));
        CityListView = (ListView) findViewById(R.id.city_list);

        button = (Switch) findViewById(R.id.switch1);
        button.setChecked(true);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("Switch Value", "" + b);
                if(b == true){
                    Log.e("Subscribe", "Yes");
                    PackageManager pm  = BaseActivity.this.getPackageManager();
                    ComponentName componentName = new ComponentName(BaseActivity.this, GcmBroadcastReceiver.class);
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

                }else{
                    Log.e("UnSubscribe", "Yes");
                    PackageManager pm  = BaseActivity.this.getPackageManager();
                    ComponentName componentName = new ComponentName(BaseActivity.this, GcmBroadcastReceiver.class);
                    pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);

                }

            }
        });

    }
    public void UpDateList(Object caller, Object model) {
        SelectCityModel.getInstance().setList((SelectCityModel) model);
        aq.id(R.id.city_list).itemClicked(new SelectCityListner());
        SelectCityAdapter selectCityAdapter=  new SelectCityAdapter(getApplicationContext());
        CityListView.setAdapter(selectCityAdapter);

    }

    public class SelectCityListner implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityId = SelectCityModel.getInstance().results.get(position).id;
            Log.e("CITYID",String.valueOf(cityId));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PagerFragment.newInstance(cityId, false))
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
