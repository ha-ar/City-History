package Services;

import android.content.Context;
import android.util.Log;

import Model.PlacesModel;

/**
 * Created by waqas on 6/24/15.
 */
public class PlacesService extends BaseService {

    public PlacesService(Context ctx) {
        super(ctx);
    }
    public void CityDetails(String CityId,String CategoriedId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "site/" + CityId + "/type/"+CategoriedId+"/articles-list/?format=json";
        Log.e("Placesurl",url);
        this.get(url, obj, PlacesModel.getInstance(), message);
    }

    public void CityDetailPage(boolean message,int pageNo,CallBack obj){

        String url = Constants.CITY_DETAIL_PAGE+"page="+pageNo+"&format=json";
        this.get(url, obj, PlacesModel.getInstance(), message);

    }


}
