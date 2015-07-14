package com.google.android.googleplayservices;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;

/**
 * 
 * @author Atish Agrawal
 * 
 */
public class ToggleGPS {

	Context context;

	public ToggleGPS(Context context) {
		this.context = context;
	}

	public boolean canToggleGPS() {
		PackageManager pacman = context.getPackageManager();
		PackageInfo pacInfo = null;

		try {
			pacInfo = pacman.getPackageInfo("com.android.settings",
					PackageManager.GET_RECEIVERS);
		} catch (NameNotFoundException e) {
			return false; // package not found
		}

		if (pacInfo != null) {
			for (ActivityInfo actInfo : pacInfo.receivers) {
				// test if recevier is exported. if so, we can toggle GPS.
				if (actInfo.name
						.equals("com.android.settings.widget.SettingsAppWidgetProvider")
						&& actInfo.exported) {
					return true;
				}
			}
		}

		return false; // default
	}

	public void turnGPSOn() {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		this.context.sendBroadcast(intent);

		String provider = Settings.Secure.getString(
				context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) {
			// if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.context.sendBroadcast(poke);
		}
	}

	public void turnGPSOff() {
		String provider = Settings.Secure.getString(
				context.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			this.context.sendBroadcast(poke);
		}
	}

}
