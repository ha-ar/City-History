package com.algorepublic.cityhistory.cityhistory;

/**
 * Created by waqas on 6/19/15.
 */
public class CityDetails {


    public String getId() {
        return id ;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return cityname;
    }
    public void setName(String name) {
        this.cityname = name;
    }


    public String getDomain(){
        return domain;
    }

    public void setDomain(String domain){
        this.domain=domain;
    }
    private String id ;
    private String cityname;
    private  String domain;

}

