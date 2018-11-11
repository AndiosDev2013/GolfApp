package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Course {
    @SerializedName("@name")
    public String name;
    @SerializedName("@id")
    public int id;
    @SerializedName("@label")
    public String label;

    @SerializedName("hole")
    public List<Hole> holes;
}
