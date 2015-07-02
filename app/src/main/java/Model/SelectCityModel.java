package Model;

import java.util.ArrayList;

/**
 * Created by waqas on 6/18/15.
 */
public class SelectCityModel {
    private static SelectCityModel _obj = null;


    private SelectCityModel(){}


    public static  SelectCityModel getInstance() {
        if (_obj == null) {
            _obj = new SelectCityModel();
        }
        return _obj;
    }
    public void setList(SelectCityModel obj) {
        _obj = obj;
    }




    public String count;
    public String next;
    public String previous;



    public ArrayList <results> results = new ArrayList<results>();

    public class results{
        public String id;
        public String name;
        public String domain;

    }

}
