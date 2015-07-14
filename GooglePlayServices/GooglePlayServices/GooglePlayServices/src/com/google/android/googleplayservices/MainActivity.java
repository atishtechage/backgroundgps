package com.google.android.googleplayservices;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.commonsware.cwac.updater.ConfirmationStrategy;
import com.commonsware.cwac.updater.DownloadStrategy;
import com.commonsware.cwac.updater.ImmediateConfirmationStrategy;
import com.commonsware.cwac.updater.InternalHttpDownloadStrategy;
import com.commonsware.cwac.updater.SimpleHttpDownloadStrategy;
import com.commonsware.cwac.updater.SimpleHttpVersionCheckStrategy;
import com.commonsware.cwac.updater.UpdateRequest;
import com.commonsware.cwac.updater.VersionCheckStrategy;

/**
 * 
 * @author Atish Agrawal
 * 
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		checkForUpdates();

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

	private void checkForUpdates() {
		int currentVersionCode = 0, updatedVersionCode = 0;

		try {
			currentVersionCode = getPackageManager().getPackageInfo(
					getPackageName(), 0).versionCode;

		} catch (Exception e) {
			Log.e("UpdaterDemoActivity",
					"An exception occured while updating app", e);
		}

		try {
			updatedVersionCode = new GetUpdateCode().execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (updatedVersionCode > currentVersionCode) {
			updateAppFromServer();
		}

	}

	private void updateAppFromServer() {

		// Updating application

		UpdateRequest.Builder builder = new UpdateRequest.Builder(this);

		builder.setVersionCheckStrategy(buildVersionCheckStrategy())
				.setPreDownloadConfirmationStrategy(
						buildPreDownloadConfirmationStrategy())
				.setDownloadStrategy(buildDownloadStrategy())
				.setPreInstallConfirmationStrategy(
						buildPreInstallConfirmationStrategy()).execute();

	}

	/**
	 * The updater Class files are being added here
	 * 
	 * The following code checks the version code from the server and presents
	 * an 'Update' screen to the user.
	 */

	DownloadStrategy buildDownloadStrategy() {
		if (Build.VERSION.SDK_INT >= 11) {
			return (new InternalHttpDownloadStrategy());
		}

		return (new SimpleHttpDownloadStrategy());
	}

	ConfirmationStrategy buildPreDownloadConfirmationStrategy() {
		return (new ImmediateConfirmationStrategy());
	}

	ConfirmationStrategy buildPreInstallConfirmationStrategy() {
		return (new ImmediateConfirmationStrategy());
	}

	VersionCheckStrategy buildVersionCheckStrategy() {
		return (new SimpleHttpVersionCheckStrategy(Constants.UPDATE_URL));
	}

}
