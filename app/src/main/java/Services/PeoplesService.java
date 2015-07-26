package Services;

import android.content.Context;

import Model.PeoplesModel;

/**
 * Created by waqas on 7/6/15.
 */
public class PeoplesService extends BaseService {


    public PeoplesService(Context ctx) {
        super(ctx);
    }

    public void PeoplesDetails(String CityId,String CategoriedId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "site/" + CityId + "/type/"+CategoriedId+"/articles-list/?format=json";
        this.get(url, obj, PeoplesModel.getInstance(), message);
    }
    public void PeoplesDetailPage(String CityId, String CategoriedId,boolean message,int pageNo,CallBack obj){

        String url = Constants.BASE_URL+"site/"+CityId+"/type/"+CategoriedId+"/articles-list/?"+"page="+pageNo+"&format=json";
       this.get(url, obj, PeoplesModel.getInstance(), message);

    }
}
