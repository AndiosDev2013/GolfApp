package com.app.golfapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.app.golfapp.GolfApp;


public class DeviceUtil {
	/*
	 * network connection
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean isConnected = false;
		try{
			ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

		} catch(Exception e) {
			e.printStackTrace();  
			return false;
		}

		return isConnected;
	}

	// check wifi availble
	public static boolean isWifiAvailable() {
		ConnectivityManager connManager = (ConnectivityManager) GolfApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	/*
	 * Location service
	 */
	public static boolean isLocationServiceAvailable(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// getting GPS status
		boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		return isGPSEnabled || isNetworkEnabled;
	}

	public static String getLocationName(double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(GolfApp.getContext(), Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				if (address == null)
					return String.format(Locale.getDefault(), "*Lon:%.3f, Lat:%.3f", longitude, latitude);

				String country = address.getCountryName();
				if (country == null) {
					country = "";
				}
				String adminArea = address.getAdminArea();
				if (adminArea == null) {
					adminArea = "";
				}
				String locality = address.getLocality();
				if (locality == null) {
					locality = "";
				}
				String thoroghfare = address.getThoroughfare();
				if (thoroghfare == null) {
					thoroghfare = "";
				}
				String subthoroghfare = address.getSubThoroughfare();
				if (subthoroghfare == null) {
					subthoroghfare = "";
				}
				String area = address.getSubLocality();
				if (area == null) {
					area = "";
				}

				String locationName = subthoroghfare + " " + thoroghfare + " " + locality + " " + adminArea + " " + country;
				locationName = locationName.replace("  ", " ");
				locationName = locationName.replace("  ", " ");

				return locationName;

			} else {
				return String.format(Locale.getDefault(), "*Lon:%.3f, Lat:%.3f", longitude, latitude);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
}
