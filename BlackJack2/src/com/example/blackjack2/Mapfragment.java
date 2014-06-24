package com.example.blackjack2;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Mapfragment extends Fragment implements LocationListener , OnMapClickListener , OnInfoWindowClickListener {

	LocationManager lm;
	String best;
	MapView m;
	GoogleMap map;
	HttpClient client; 
	
	public Mapfragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mapfrag = inflater.inflate(R.layout.fragment_map, container,
				false);

		lm = (LocationManager) getActivity().getSystemService (Context.LOCATION_SERVICE);
		getActivity().getSystemService(Context.LOCATION_SERVICE);
		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		best = lm.getBestProvider(crit, true);
		lm.requestLocationUpdates(best, 100, 0, this);
		
		m = (MapView) mapfrag.findViewById(R.id.mapview);
		m.onCreate(savedInstanceState);
		this.setUpMapIfNeeded();
		
		Location lo= lm.getLastKnownLocation(best);
		this.setUpMap(lo);
		
		map.setOnMapClickListener(this);
		map.setOnInfoWindowClickListener(this);
		
		client = (HttpClient) new DefaultHttpClient();
		
		return mapfrag;
	}

	private void setUpMap(Location location) {
        if (map != null) {
        	LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions().position(loc).title("Marker"));
            CameraUpdate center= CameraUpdateFactory.newLatLngZoom(loc, 15);
            if (center != null)  {
            	map.moveCamera(center);
            }
        }
	}

	
	public void configureMap() {
	    if (map == null)
	        return; // Google Maps not available
	    try {
	        MapsInitializer.initialize(getActivity());
	    }
	    catch (Exception e) {
	        return;
	     }
	    map.setMyLocationEnabled(true);   //顯示目前使用者的方位
	    //this.setUpMap(null);
	}

	public void setUpMapIfNeeded() {
        if (map == null) {
            map =  m.getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                 configureMap();
            }
        }
	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		marker.remove();
	}

	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        markerOptions.snippet("Tap here to remove this marker");
        markerOptions.title("Marker Demo");

        map.addMarker(markerOptions);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String locationContent = "緯度：" + location.getLatitude() + 
                "\n經度：" + location.getLongitude() +
                "\n精度：" + location.getAccuracy() +
                "\n標高：" + location.getAltitude() +
                "\n時間：" + location.getTime() +
                "\n速度：" + location.getSpeed() + 
                "\n方位：" + location.getBearing();
		Toast.makeText(getActivity(), locationContent, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
	        super.onResume();
	        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, this);   //做定位時用
	        m.onResume(); 
	        this.setUpMapIfNeeded();
	 }
	@Override
	public void onPause() {
	        super.onPause();
	        m.onPause();
	        lm.removeUpdates(this);
	}
	@Override
	public void onDestroy() {
	        super.onDestroy();
	        m.onDestroy();
	}
	@Override
	public void onLowMemory() {
	        super.onLowMemory();
	        m.onLowMemory();
	}
	
	public void showMessage(final String msg)   {
        getActivity().runOnUiThread(new Runnable() {
                 public void run()  {
                               Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                 }
         });
	}
	
}
