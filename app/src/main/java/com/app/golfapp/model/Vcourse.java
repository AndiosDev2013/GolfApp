package com.app.golfapp.model;

import com.google.gson.annotations.SerializedName;

public class Vcourse {
    @SerializedName("@name")
    public String name;
    @SerializedName("@backId")
    public int backId;
    @SerializedName("@frontId")
    public int frontId;
    @SerializedName("@id")
    public String id;
}
