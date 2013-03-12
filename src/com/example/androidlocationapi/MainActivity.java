package com.example.androidlocationapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	// Acquire a reference to the system Location Manager

	private static final String PROX_ALERT_INTENT = "com.example.androidlocationapi.SendInfo";
	
	LocationManager locationManager;
	
	private String TAG = "test";
	
	private GoogleMap mMap;
	
	private boolean firststart = true;
	
	Intent sendinfo = new Intent(this,SendInfo.class);
	
	private String name = "";
	private String mac = "";
	private String outin = "outside";
	
	String simmiip = "192.168.53.246";
	String danielip = "10.25.231.246";
	
	private String ip = simmiip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		mac = mBluetoothAdapter.getAddress();
		name = mBluetoothAdapter.getName();
		
		Log.v(TAG,"Oncreate 0 mac: "+ mac+ " NAme: "+name);
		
		Log.v(TAG,"Oncreate 1");
		super.onCreate(savedInstanceState);
		Log.v(TAG,"Oncreate 2");
		setContentView(R.layout.main);
		Log.v(TAG,"Oncreate 3");
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Log.v(TAG,"Oncreate 4");
		
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
		Log.v(TAG,"Oncreate 5");
		
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		Log.v(TAG,"Oncreate 6");
		
		

		Intent intent = new Intent(PROX_ALERT_INTENT);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		locationManager.addProximityAlert(55.6597983, 12.5912919, 10, -1, proximityIntent);
		

		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);

		registerReceiver(new ProximityIntentReceiver(), filter);

		/*PendingIntent pi = PendingIntent.getActivity(this, 0, sendinfo, 0);
		
		locationManager.addProximityAlert(55.6597983, 12.5912919, 10, -1, pi);*/
		Toast.makeText(this, "Hello onCreate main activity", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		Log.v(TAG,"Oncreate options 1");
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		Log.v(TAG,"Oncreate options 2");
		return true;
	}

	@Override
	public void onDestroy()
	{
		Log.v(TAG,"Ondestroy 1");
		locationManager.removeUpdates(locationListener);
		Log.v(TAG,"Ondestroy 2");
		
	}
	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {   
		
		public void onLocationChanged(Location location) {     
			Log.v(TAG,"On location change 1");
			// Called when a new location is found by the network location provider.      
			makeUseOfNewLocation(location);
			Log.v(TAG,"On location change 2");
			}    
		
		public void onStatusChanged(String provider, int status, Bundle extras) { Log.v(TAG,"status changed"); }    
		public void onProviderEnabled(String provider) { Log.v(TAG,"provider enabled"); }    
		public void onProviderDisabled(String provider) { Log.v(TAG,"provider disabled"); }  
	};
	
	
	private void makeUseOfNewLocation(Location location) {
		// TODO Auto-generated method stub
		LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
		
		mMap.clear();
		mMap.addMarker(new MarkerOptions().position(position).title("Latitude: "+location.getLatitude()+" - Longitude: "+location.getLongitude()));
			Log.v(TAG,"Location updated");
			Log.v(TAG,"Location. Latitude: "+location.getLatitude()+"  Longitude: "+location.getLongitude()+new Date());
			if(firststart)
			{
				mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition( position,15, 0, 0)));
				firststart = false;
			}
			
			

			if((position.latitude < 55.659928 && position.latitude > 55.659547) && (position.longitude > 12.59048 && position.longitude < 12.59167))
			{
				Log.v(TAG,"INSIDE BOX");
				outin = "inside";
				Toast.makeText(this, "Inside box", Toast.LENGTH_SHORT).show();
					
				SendMessage myRunnable = new SendMessage(mac+"_"+name+"_inside",ip);
			    Thread t = new Thread(myRunnable);
			    t.start();

			}
			else
			{
				Log.v(TAG,"OUTSIDE BOX");
				outin = "outside";
				
				Toast.makeText(this, "Outside box", Toast.LENGTH_SHORT).show();
				SendMessage myRunnable = new SendMessage(mac+"_"+name+"_outside",ip);
		        Thread t = new Thread(myRunnable);
		        t.start();
			}
	}
	
		
}
