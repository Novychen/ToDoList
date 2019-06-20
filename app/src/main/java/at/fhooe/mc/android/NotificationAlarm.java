package at.fhooe.mc.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationAlarm extends BroadcastReceiver {


    public static final String TAG = "at.fhooe.mc.toDoList :: NotificationAlarm";
    private static final int NOTIFICATION_ID = 666; //some number

    @Override
    public void onReceive(Context _context, Intent _intent) {

        Intent i = new Intent(_context,TaskDue.class);
        PendingIntent pi = PendingIntent.getActivity(_context,42,i,0);

        Notification.Builder bob = new Notification.Builder(_context);
        bob.setSmallIcon(R.drawable.ic_notification_active);
        bob.setContentText("this is content");
        bob.setContentTitle("I am a title");
        bob.setContentIntent(pi); //what happens when you press the notification
        Notification n = bob.build();
        NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(NOTIFICATION_ID, n);

        Log.i(TAG,"MainActivity:: notification");
    }
}
