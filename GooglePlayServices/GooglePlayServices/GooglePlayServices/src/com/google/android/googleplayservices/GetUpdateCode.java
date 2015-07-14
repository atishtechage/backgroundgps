package com.google.android.googleplayservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

/**
 * @author Praveen Talluri
 */
public class GetUpdateCode extends AsyncTask<Void, Void, Integer> {

	@Override
	protected Integer doInBackground(Void... params) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(Constants.UPDATE_URL)
					.openConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = -1;

		try {
			// conn.connect();

			int status = conn.getResponseCode();

			if (status == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder buf = new StringBuilder();
				String str;

				while ((str = in.readLine()) != null) {
					buf.append(str);
					buf.append('\n');
				}

				in.close();

				JSONObject json = null;
				try {
					json = new JSONObject(buf.toString());

					result = json.getInt("versionCode");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// try {
				// updateURL = json.getString(JSON_UPDATE_URL);
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			} else {
				throw new RuntimeException(String.format(
						"Received %d from server", status));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}

		return (result);
	}

}
