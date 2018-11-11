package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

public class Trigger extends CCoord {
    @SerializedName("@name")
    public String name;
//    @SerializedName("@coordinate")
//    public String coordinate;
    @SerializedName("@radius")
    public int radius;
}
