package com.app.golfapp.utils;

public class MathUtil {
    /*
     * Distance between two location
     */
    public static double EARTH_RADIUS = 6366198;

	public static double distanceBetween(double lat_a, double lon_a, double lat_b, double lon_b) {
	    double pk = 180/3.14169;

	    double a1 = lat_a / pk;
	    double a2 = lon_a / pk;
	    double b1 = lat_b / pk;
	    double b2 = lon_b / pk;

	    double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
	    double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
	    double t3 = Math.sin(a1)*Math.sin(b1);
	    double tt = Math.acos(t1 + t2 + t3);

	    return EARTH_RADIUS * tt;
	}
}
