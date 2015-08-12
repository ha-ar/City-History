package Services;

import android.content.Context;

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

        this.get(url, obj, SelectCityModel.getInstance(),message);


    }
}
