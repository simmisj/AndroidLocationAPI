package com.example.androidlocationapi;

import com.example.androidlocationapi.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class SendInfo extends Activity {
	String TAG = "test";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendinfo);
		Log.v(TAG,"SendInfo oncreate 1");
		Toast.makeText(this, "Hello sendinfo", Toast.LENGTH_LONG).show();
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.sendinfo, menu);
		return true;
	}*/
}
