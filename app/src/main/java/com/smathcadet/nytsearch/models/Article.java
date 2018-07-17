package com.smathcadet.nytsearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lenovo de Marcus on 7/15/2018.
 */

public class Article implements Serializable{
    private String webUrl;
    private String headline;
    private String thumbnail;

    public Article(JSONObject object) throws JSONException {
        this.webUrl = object.getString("web_url");
        this.headline = object.getJSONObject("headline").getString("main");
        JSONArray multimedia = object.getJSONArray("multimedia");
        if (multimedia.length() > 0){
            JSONObject multimediaObject = multimedia.getJSONObject(0);
            this.thumbnail = "http://www.nytimes.com/" + multimediaObject.getString("url");
        }else {
            this.thumbnail = "";
        }
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array){
        ArrayList<Article> articles = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            try {
                articles.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }
}
