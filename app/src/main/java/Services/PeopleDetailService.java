package Services;

import android.content.Context;
import android.util.Log;

import Model.PeoplesDetailModel;

/**
 * Created by waqas on 7/6/15.
 */
public class PeopleDetailService extends BaseService {

    public PeopleDetailService(Context ctx) {
        super(ctx);
    }

    public void CityPeoplesDetails(int PlaceId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "articles/" + PlaceId + Constants.CITY_PlACES_DETAIL_URL;
        Log.e("CityPlacesD etails Url", url);
        this.get(url, obj, PeoplesDetailModel.getInstance(), message);
    }
}
