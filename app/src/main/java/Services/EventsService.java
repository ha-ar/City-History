package Services;

import android.content.Context;
import android.util.Log;

import Model.EventsModel;

/**
 * Created by waqas on 7/5/15.
 */
public class EventsService extends BaseService {


    public EventsService(Context ctx) {
        super(ctx);
    }

    public void EventsDetails(String CityId,String CategoriedId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "site/" + CityId + "/type/"+CategoriedId+"/articles-list/?format=json";
        Log.e("Placesurl",url);
        this.get(url, obj, EventsModel.getInstance(), message);
    }
    public void EventDetailPage(boolean message,int pageNo,CallBack obj){

        String url = Constants.CITY_DETAIL_PAGE+"page="+pageNo+"&format=json";

        this.get(url, obj, EventsModel.getInstance(), message);

    }


}
