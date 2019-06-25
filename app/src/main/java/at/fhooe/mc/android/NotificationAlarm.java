package at.fhooe.mc.android;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationAlarm extends BroadcastReceiver {


    public static final String TAG = "at.fhooe.mc.toDoList :: NotificationAlarm";
    private static final  String GROUP_KEY = "at.fhooe.mc.toDoList.GROUP_KEY";
    //private final static int NotificationID = 666;
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();

    protected String mNotificationText;
    protected String mNotificationTitle;

    public void setNotificationTitle(String _t){
        mNotificationTitle = _t;
    }

    public String getNotificationTitle (){
        return mNotificationTitle;
    }

    public String getNotificationText(){
        return mNotificationText;
    }

    public void setNotificationText(String _text) {
        mNotificationText = _text;
    }


    @Override
    public void onReceive(Context _context, Intent _intent) {
        if(mNotificationText != null) {

        Intent i = new Intent(_context,TaskDue.class);
        Random r = new Random();
        PendingIntent pi = PendingIntent.getActivity(_context,  r.nextInt(10000) ,i,0);

        inboxStyle.setBigContentTitle("ToDoList");

        Notification.Builder groupBuilder = new Notification.Builder(_context)
                .setContentTitle("toDoList")
                .setSmallIcon(R.drawable.ic_notification_active)
                .setGroup(NotificationAlarm.GROUP_KEY)
                .setContentText("This is a activity_deadline_task_test")
                .setGroupSummary(true)
                .setContentIntent(pi);
        Notification group = groupBuilder.build();
        NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(r.nextInt(10000), group);


        Notification.Builder bob = new Notification.Builder(_context)
                .setSmallIcon(R.drawable.ic_notification_active)
                .setContentText(getNotificationText())
                //.setContentText("Your Task is due!")
                //.setContentTitle("ToDoList")
                .setContentTitle(getNotificationTitle())
                .setGroup(GROUP_KEY)
                .setContentIntent(pi); //what happens when you press the notification


        Notification n = bob.build();
            nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.notify(r.nextInt(10000), n);


        Log.i(TAG,"notification");
        }
    }
}
