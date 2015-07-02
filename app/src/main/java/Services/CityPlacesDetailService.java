package Services;

import android.content.Context;
import android.util.Log;

import Model.CityPlacesDetailModel;

/**
 * Created by waqas on 7/1/15.
 */
public class CityPlacesDetailService extends BaseService {


    public CityPlacesDetailService(Context ctx) {
        super(ctx);
    }

    public void CityPlacesDetails(int PlaceId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "articles/" + PlaceId + Constants.CITY_PlACES_DETAIL_URL;
        Log.e("CityPlacesDetails Url",url);
        this.get(url, obj, CityPlacesDetailModel.getInstance(), message);


    }
}
