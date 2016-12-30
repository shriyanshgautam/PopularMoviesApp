package com.shriyans.popularmovies.models;

/**
 * Created by shriyans on 7/13/16.
 */
public class Movie {

        private String title,posterPath,overview,releaseDate;
        double voteAverage;
        long id;

        public Movie(long id, String title, String posterPath, String overview,double voteAverage,String releaseDate) {
            this.id = id;
            this.title = title;
            this.posterPath=posterPath;
            this.overview=overview;
            this.voteAverage=voteAverage;
            this.releaseDate=releaseDate;
        }

        public long getId(){ return id;}

        public void setId(long id){this.id = id;}

        public String getTitle() {
            return title;
        }

        public void setTitle(String name) {
            this.title = name;
        }

        public String getPosterPath(){
            return posterPath;
        }
        public void setPosterPath(String posterPath){
            this.posterPath=posterPath;
        }

        public String getOverview(){
            return this.overview;
        }

        public void setOverview(String overview){
            this.overview = overview;
        }

        public double getVoteAverage(){
            return this.voteAverage;
        }

        public void setVoteAverage(double voteAverage){
            this.voteAverage=voteAverage;
        }

        public String getReleaseDate(){
            return this.releaseDate;
        }

        public void setReleaseDate(String release_date){
            this.releaseDate=release_date;
        }
}
