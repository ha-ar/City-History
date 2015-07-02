package Services;

import android.content.Context;
import android.util.Log;

import Model.SelectCityModel;

/**
 * Created by waqas on 6/22/15.
 */
public class SelectCityService extends BaseService {
    public SelectCityService(Context ctx) {
        super(ctx);
    }

    public void SelectCity(boolean message, CallBack obj){
        String url = Constants.BASE_URL+ Constants.SELECT_CITY_URL;
        Log.e("URL",url);
        this.get(url, obj, SelectCityModel.getInstance(),message);


    }
}
