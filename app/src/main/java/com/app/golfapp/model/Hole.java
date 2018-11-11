package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hole {
    @SerializedName("@name")
    public String name;
    @SerializedName("@c1")
    public String c1;
    @SerializedName("@c2")
    public String c2;
    @SerializedName("@c3")
    public String c3;
    @SerializedName("@c4")
    public String c4;
    @SerializedName("@label")
    public String label;
    @SerializedName("@number")
    public int number;
    @SerializedName("@pace")
    public int pace;
    @SerializedName("@par")
    public int par;

    @SerializedName("trigger")
    public List<Trigger> triggers;
    public CCoord greenBack;
    public CCoord greenCenter;
    public CCoord greenFront;
    @SerializedName("hazard")
    public List<Hazard> hazards;
}
