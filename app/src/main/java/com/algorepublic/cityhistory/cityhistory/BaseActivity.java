package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;

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
    private Spinner spinner1;
    public ArrayList<String> name;
    public ArrayAdapter<String> dataAdapter;

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
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        name = new ArrayList<>();
        obj = new SelectCityService(BaseActivity.this);
        obj.SelectCity(true, new CallBack(this, "UpDateList"));
        CityListView = (ListView) findViewById(R.id.city_list);

        button = (Switch) findViewById(R.id.switch1);
        button.setChecked(true);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ParsePush.subscribeInBackground("", new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                            } else {
                                Log.e("com.parse.push", "failed to subscribe for push", e);
                            }
                        }
                    });
                } else {
//                    button.setChecked(false);
                    ParsePush.unsubscribeInBackground("", new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d("com.parse.push", "successfully UnSubscribed to the broadcast channel.");
                            } else {
                                Log.e("com.parse.push", "failed to UnSubscribe for push", e);
                            }
                        }
                    });

                }


            }
        });

    }
    public void UpDateList(Object caller, Object model) {
        SelectCityModel.getInstance().setList((SelectCityModel) model);
        aq.id(R.id.city_list).itemClicked(new SelectCityListner());
        SelectCityAdapter selectCityAdapter=  new SelectCityAdapter(getApplicationContext());
        CityListView.setAdapter(selectCityAdapter);
//        name.add("Cities");
        for (int k =0 ; k < SelectCityModel.getInstance().results.size(); k++) {
            name.add(capSentences(SelectCityModel.getInstance().results.get(k).name));
        }
        name.add("Cities");
        dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, name){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        spinner1.setSelection(dataAdapter.getCount());
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }


    private String capSentences( String text ) {

        return text.substring( 0, 1 ).toUpperCase() + text.substring( 1 ).toLowerCase();
    }

    public class SelectCityListner implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityId = SelectCityModel.getInstance().results.get(position).id;
            Log.e("CITYID",String.valueOf(cityId));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PagerFragment.newInstance(cityId))
                    .addToBackStack(null).commit();
        }
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {

//            Toast.makeText(parent.getContext(),
//                    "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
//                    Toast.LENGTH_LONG).show();
            if (parent.getItemAtPosition(pos).toString().equals("Cities")){

            }else{
                String cityId = SelectCityModel.getInstance().results.get(pos).id;
                Log.e("CITYID",String.valueOf(cityId));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PagerFragment.newInstance(cityId))
                        .addToBackStack(null).commit();
                mDrawerLeft.closeMenu();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

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
