package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facility extends CCoord {
    @SerializedName("@name")
    public String name;
//    @SerializedName("@coordinate")
//    public String coordinate;
    @SerializedName("@gbbkgcolor")
    public String gbbkgcolor;
    @SerializedName("@gbtxtcolor")
    public String gbtxtcolor;
    @SerializedName("@gcbkgcolor")
    public String gcbkgcolor;
    @SerializedName("@gctxtcolor")
    public String gctxtcolor;
    @SerializedName("@gfbkgcolor")
    public String gfbkgcolor;
    @SerializedName("@gftxtcolor")
    public String gftxtcolor;
    @SerializedName("@id")
    public int id;
    @SerializedName("@projection")
    public String projection;
    @SerializedName("@shotplanner")
    public String shotplanner;
    @SerializedName("@showcart")
    public String showcart;
    @SerializedName("@sound")
    public String sound;
    @SerializedName("@timezone")
    public String timezone;
    @SerializedName("@units")
    public String units;
    @SerializedName("@vendor")
    public String vendor;

    @SerializedName("homebase")
    public List<Homebase> homebases;
    public Vcourse vcourse;
    @SerializedName("course")
    public List<Course> courses;
}
