package Services;

import android.content.Context;
import android.util.Log;

import Model.EventsDetailsModel;

/**
 * Created by waqas on 7/5/15.
 */
public class EventsDetailsService extends BaseService {

    public EventsDetailsService(Context ctx) {
        super(ctx);
    }

    public void CityEventsDetails(int PlaceId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "articles/" + PlaceId + Constants.CITY_PlACES_DETAIL_URL;
        Log.e("url", url);
        this.get(url, obj, EventsDetailsModel.getInstance(), message);
    }
}
