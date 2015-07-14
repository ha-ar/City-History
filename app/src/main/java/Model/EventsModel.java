package Model;

import java.util.ArrayList;

/**
 * Created by waqas on 7/5/15.
 */
public class EventsModel  {

    private static EventsModel _obj = null;


    public EventsModel() {
    }
    public static EventsModel getInstance() {
        if (_obj == null) {
            _obj = new EventsModel();
        }
        return _obj;
    }
    public void setList(EventsModel obj) {
        _obj = obj;


    }

    public void appendList(EventsModel obj){

        for(results data : obj.results) {
            if ( !_obj.results.contains(data) ){
                _obj.results.add(data);
            }
        }
        _obj.next=obj.next;
    }
    public String count;
    public String next;
    public String previous;


    public ArrayList<results> results = new ArrayList<results>();


    public class results{


        public int id;
        public String url;
        public String title;
        public String description;
        public String get_photo;
        public String get_Photo_Thumbnail;
        public String get_photoMicro_thumbnail;
        public Object mainPhoto;
        public String added;
    }
}
