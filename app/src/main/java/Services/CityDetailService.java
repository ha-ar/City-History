package Services;

import android.content.Context;

import Model.CityPlacesModel;

/**
 * Created by waqas on 6/24/15.
 */
public class CityDetailService extends BaseService {

    public CityDetailService(Context ctx) {
        super(ctx);
    }
    public void CityDetails(String CityId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "site/" + CityId + "/type/1/articles-list/?format=json";


        this.get(url, obj, CityPlacesModel.getInstance(), message);


    }

    public void CityDetailPage(boolean message,int pageNo,CallBack obj){

        String url = Constants.CITY_DETAIL_PAGE+"page="+pageNo+"&format=json";
        this.get(url, obj, CityPlacesModel.getInstance(), message);

    }


}
