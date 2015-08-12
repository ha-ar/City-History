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
        this.get(url, obj, PlacesModel.getInstance(), message);
        Log.e("urlplaces",url);
    }
    public void CityDetailPage(String CityId,String CategoriedId,boolean message,int pageNo,CallBack obj){
        String url = Constants.BASE_URL+"site/"+CityId+"/type/"+CategoriedId+"/articles-list/?"+"page="+pageNo+"&format=json";
        this.get(url, obj, PlacesModel.getInstance(), message);

    }


}
