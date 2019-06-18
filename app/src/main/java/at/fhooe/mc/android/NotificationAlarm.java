package at.fhooe.mc.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v4.media.app.NotificationCompat;
import android.util.Log;

public class NotificationAlarm extends BroadcastReceiver {


    public static final String TAG = "Notafication-Test";
    private static final String CHANNEL_ID = "at.fhooe.mc.android.someChannelID";    //text der einzigartig im Framework ist
    private static final int NOTIFICATION_ID = 666; //some number
    static String mContent;

    @Override
    public void onReceive(Context _context, Intent _intent) {

        Intent i = new Intent(_context,TaskDue.class);
        PendingIntent pi = PendingIntent.getActivity(_context,42,i,0);

        Notification.Builder bob = new Notification.Builder(_context);
        bob.setSmallIcon(R.drawable.ic_notification_active);
        bob.setContentText("this is content");
        bob.setContentTitle("I am a title");
        bob.setContentIntent(pi); //what happens when you press the notification
        // bob.setAutoCancel(true);
        Notification n = bob.build();
        NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(NOTIFICATION_ID, n);

        Log.i(TAG,"MainActivity:: notification");
    }

        public static void setContent(String _c){
        mContent = _c;
        }
}
