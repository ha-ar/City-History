package Services;

import android.content.Context;
import android.util.Log;

import Model.BlogsDetailsModel;

/**
 * Created by waqas on 7/6/15.
 */
public class BlogsDetailService  extends BaseService {

    public BlogsDetailService(Context ctx) {
        super(ctx);
    }

    public void BlogsDetails(int PlaceId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "articles/" + PlaceId + Constants.CITY_PlACES_DETAIL_URL;
        Log.e("CityPlacesDetails Url", url);
        this.get(url, obj, BlogsDetailsModel.getInstance(), message);
    }
}
