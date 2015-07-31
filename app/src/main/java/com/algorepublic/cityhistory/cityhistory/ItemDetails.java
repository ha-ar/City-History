package com.algorepublic.cityhistory.cityhistory;

/**
 * Created by waqas on 7/3/15.
 */
public class ItemDetails {


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }


    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url= url;
    }


    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getGet_photo(){
        return get_photo;
    }

    public void setGet_photo(String get_photo) {
        this.get_photo = get_photo;
    }

    public String getGet_photo_micro_thumbnail() {
        return get_photo_micro_thumbnail;
    }

    public void setGet_photo_micro_thumbnail(String get_photo_micro_thumbnail) {
        this.get_photo_micro_thumbnail = get_photo_micro_thumbnail;
    }

    public String getGet_photo_thumbnail() {
        return get_photo_thumbnail;
    }

    public void setGet_photo_thumbnail(String get_photo_thumbnail) {
        this.get_photo_thumbnail = get_photo_thumbnail;
    }


    public String getAdded() {
        return added;
    }

    public void setAdded(String added)
    {
        this.added=added;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int id;
    private String url;
    private String title;
    private String description;
    private String get_photo;
    public String get_photo_thumbnail;
    public String get_photo_micro_thumbnail;
    public String added;
    public String address;
}