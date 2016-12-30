package com.shriyans.popularmovies.models;

/**
 * Created by shriyanshgautam on 30/12/16.
 */
public class Trailer {

    String id,key,name;

    public Trailer(String id, String key, String name){
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }
}
