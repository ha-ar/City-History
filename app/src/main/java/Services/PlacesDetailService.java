package Services;

import android.content.Context;
import android.util.Log;

import Model.PlacesDetailModel;
import Model.PlacesDetailModel2;

/**
 * Created by waqas on 7/1/15.
 */
public class PlacesDetailService extends BaseService {


    public PlacesDetailService(Context ctx) {
        super(ctx);
    }

    public void CityPlacesDetails(boolean message,int PlaceId, CallBack obj){
        String url = Constants.BASE_URL + "articles/" + PlaceId + Constants.CITY_PlACES_DETAIL_URL;
        Log.e("CityPlacesDetails Url",url);
        this.get(url, obj, PlacesDetailModel.getInstance(), message);
    }
    public void CityPlacesDetails2(boolean message,int PlaceId, CallBack obj){
        String url = Constants.BASE_URL + "articles/" + PlaceId + Constants.CITY_PlACES_DETAIL_URL;
        Log.e("CityPlacesDetails Url",url);
        this.get(url, obj, PlacesDetailModel2.getInstance(), message);
    }
}
