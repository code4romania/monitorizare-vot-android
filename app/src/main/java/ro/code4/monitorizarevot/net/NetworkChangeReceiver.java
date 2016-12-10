package ro.code4.monitorizarevot.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ro.code4.monitorizarevot.adapter.SyncAdapter;
import ro.code4.monitorizarevot.db.Preferences;

import static ro.code4.monitorizarevot.util.NetworkUtils.isOnline;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isOnline(context) && Preferences.hasCredentials()) {
            SyncAdapter.requestSync(context);
        } else {
            //do stuff when connection lost
        }
    }
}
