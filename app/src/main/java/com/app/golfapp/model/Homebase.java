package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

public class Homebase extends CCoord {
    @SerializedName("@name")
    public String name;
    @SerializedName("@adset")
    public String adset;
//    @SerializedName("@coordinate")
//    public String coordinate;
    @SerializedName("@radius")
    public int radius;
}
