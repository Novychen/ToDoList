package at.fhooe.mc.android;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;
public class NotificationAlarm extends BroadcastReceiver {


    public static final String TAG = "at.fhooe.mc.toDoList :: NotificationAlarm";
    private static final  String GROUP_KEY = "at.fhooe.mc.toDoList.GROUP_KEY";
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
    static private int mGroupCount;

    public void onReceive(Context _context, Intent _intent) {

        Intent i = new Intent(_context,TaskDue.class);
        Random r = new Random();
        PendingIntent pi = PendingIntent.getActivity(_context,  r.nextInt(10000) ,i,0);

        inboxStyle.setBigContentTitle("ToDoList");

        if(mGroupCount == 0) {
            Notification.Builder groupBuilder = new Notification.Builder(_context)
                    .setContentTitle("Test")
                    .setSmallIcon(R.drawable.ic_notification_active)
                    .setGroup(NotificationAlarm.GROUP_KEY)
                    .setContentText("This is a test")
                    .setGroupSummary(true)
                    .setContentIntent(pi);
            Notification group = groupBuilder.build();
            NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.notify(r.nextInt(10000), group);
            mGroupCount++;
        }

        Notification.Builder bob = new Notification.Builder(_context)
                .setSmallIcon(R.drawable.ic_notification_active)
                .setContentText(_intent.getStringExtra("text"))
                .setContentTitle(_intent.getStringExtra("title"))
                .setGroup(GROUP_KEY)
                .setContentIntent(pi); //what happens when you press the notification


        Notification n = bob.build();
        NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(r.nextInt(10000), n);



        Log.i(TAG,"notification");
        }

}