package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

public class Hazard extends CCoord {
    @SerializedName("@name")
    public String name;
//    @SerializedName("@coordinate")
//    public String coordinate;
    @SerializedName("@landscape")
    public String landscape;
}
