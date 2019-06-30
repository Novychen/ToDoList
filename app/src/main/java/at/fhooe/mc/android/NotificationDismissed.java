package at.fhooe.mc.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationDismissed extends BroadcastReceiver {

    @Override
    public void onReceive(Context _context, Intent _intent) {
        String action = _intent.getAction();
        if(action.equals("notificationCancelled")) {

            String key = _intent.getStringExtra("ref");
            int count = _intent.getIntExtra("count", 0);
            Repository.mEnable = false;
            Repository.getInstance().saveData(count++, key);
        }
    }
}
