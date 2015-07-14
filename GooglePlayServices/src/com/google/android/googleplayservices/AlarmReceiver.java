package com.google.android.googleplayservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	public AlarmReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent arg1) {

		// Toast.makeText(context, "Alarm Reciever", Toast.LENGTH_SHORT).show();
		context.startService(new Intent(context, AndroidLocationServices.class));

	}

}
