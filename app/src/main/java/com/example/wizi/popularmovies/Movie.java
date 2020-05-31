package com.example.wizi.popularmovies;

public class Movie {

    private String mTitle;
    private String mPoster;
    private String mOverview;
    private String mAverageVote;
    private String mReleaseDate;

    public Movie() {
    }

    public Movie(String title, String poster, String overview, String averageVote, String releaseDate) {
        mTitle = title;
        mPoster = poster;
        mOverview = overview;
        mAverageVote = averageVote;
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getAverageVote() {
        return mAverageVote;
    }

    public void setAverageVote(String averageVote) {
        mAverageVote = averageVote;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }


}
