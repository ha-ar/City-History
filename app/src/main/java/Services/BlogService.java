package Services;
import android.content.Context;

import Model.BlogsModel;

/**
 * Created by waqas on 7/6/15.
 */
public class BlogService  extends BaseService {


    public BlogService(Context ctx) {
        super(ctx);
    }

    public void BlogsDetails(String CityId,String CategoriedId,boolean message, CallBack obj){
        String url = Constants.BASE_URL + "site/" + CityId + "/type/"+CategoriedId+"/articles-list/?format=json";
        this.get(url, obj, BlogsModel.getInstance(), message);
    }
    public void BlogsDetailPage(String CityId, String CategoriedId,boolean message,int pageNo,CallBack obj){

        String url = Constants.BASE_URL+"site/"+CityId+"/type/"+CategoriedId+"/articles-list/?"+"page="+pageNo+"&format=json";
        this.get(url, obj, BlogsModel.getInstance(), message);

    }


}
