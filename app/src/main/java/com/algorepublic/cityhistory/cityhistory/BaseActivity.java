package com.algorepublic.cityhistory.cityhistory;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.ArrayList;

import Model.SelectCityModel;
import Services.CallBack;
import Services.SelectCityService;


public class BaseActivity extends FragmentActivity {
    private AQuery aq;
    private BaseClass base;
    static int p;
    ArrayList<String> results;
    private SelectCityService obj;
    private ArrayList<SelectCityModel> arrayList = new ArrayList<>();
    ListView CityListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CityListView = (ListView) findViewById(R.id.city_list);
        aq = new AQuery(BaseActivity.this);
        base = ((BaseClass)getApplicationContext());


        obj = new SelectCityService(BaseActivity.this);
        obj.SelectCity(true, new CallBack(this, "UpDateList"));

    }


    public void UpDateList(Object caller, Object model) {


        SelectCityModel.getInstance().setList((SelectCityModel) model);

        if (SelectCityModel.getInstance().count!= "0") {
            aq.id(R.id.city_list).itemClicked(new SelectCityListner());
            SelectCityAdapter selectCityAdapter=  new SelectCityAdapter(getApplicationContext());

          CityListView.setAdapter(selectCityAdapter);
        }else{
            Toast.makeText(getApplicationContext(), "No City Found",
                    Toast.LENGTH_LONG).show();

        }

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
