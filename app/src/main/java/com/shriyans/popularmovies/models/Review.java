package com.shriyans.popularmovies.models;

/**
 * Created by shriyanshgautam on 30/12/16.
 */
public class Review {
    String id;
    String author,content;

    public Review(String id, String author, String content){
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getAuthor(){return this.author;}

    public void setAuthor(String author){this.author = author;}

    public String getContent(){return this.content;}

    public void setContent(String content) {
        this.content = content;
    }
}
