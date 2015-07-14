package com.google.android.googleplayservices;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

public class LocationWebService extends AsyncTask<String, String, Boolean> {

	public LocationWebService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Boolean doInBackground(String... arg0) {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("location", arg0[1]));

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(arg0[0]);
		HttpParams httpParameters = new BasicHttpParams();

		httpclient = new DefaultHttpClient(httpParameters);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response;
			response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

				Log.e("Google", "Server Responded OK");

			} else {

				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
