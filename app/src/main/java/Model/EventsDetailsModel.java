package Model;

import java.util.ArrayList;

/**
 * Created by waqas on 7/5/15.
 */
public class EventsDetailsModel {


    private static EventsDetailsModel _obj = null;

    public EventsDetailsModel() {

    }

    public static EventsDetailsModel getInstance() {
        if (_obj == null) {
            _obj = new EventsDetailsModel();
        }
        return _obj;
    }
    public void setList(EventsDetailsModel obj) {
        _obj = obj;
    }
    public int id;
    public String url;
    public String title;
    public String description;
    public String get_photo;
    public String get_photo_thumbnail;
    public String get_photo_micro_thumbnail;
    public String main_photo;
    public String added;
    public String address;
    public Album album = new Album();
    public type type = new type();
    public user user = new user();
    public site site = new site();
    public class Album {
        public int id;
        public String title;
        public String description;
        public String added;
        public String slug;
        public String address;
        public ArrayList<PhotosSet> photos_set = new ArrayList<PhotosSet>();
    }
    public class PhotosSet{
        public int id;
        public String title;
        public String description;
        public String get_photo;
        public String get_photo_thumbnail;
        public String get_photo_micro_thumbnail;

    }

    public class type{
        public int id;
        public String type;

    }

    public class user{

        public int id;
        public String username;
        public String first_name;
        public String last_name;
    }

    public class site{

        public int id;
        public String name;
        public String domain;

    }


    public ArrayList<articledetails_set> articledetails_set = new ArrayList<articledetails_set>();

    public class articledetails_set{

    }

    public ArrayList<articlereferences_set> articlereferences_set = new ArrayList<articlereferences_set>();
    public class articlereferences_set{

    }


}

