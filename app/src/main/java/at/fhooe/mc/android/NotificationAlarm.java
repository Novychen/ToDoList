package at.fhooe.mc.android;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;
public class NotificationAlarm extends BroadcastReceiver  {

/*    List<String> title = null;
    List<Integer> day = null;
    List<Integer> month = null;
    List<Integer> year = null;
    List<Integer> hour = null;
    List<Integer> min = null ;
    List<Integer> task = null;
    List<String> des = null;
    List<String> ref = null;
    List<List<String>> label = null;*/

    public static final String TAG = "at.fhooe.mc.toDoList :: NotificationAlarm";
    private static final  String GROUP_KEY = "at.fhooe.mc.toDoList.GROUP_KEY";
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
    static private int mGroupCount;

    public void onReceive(Context _context, Intent _intent) {

        final Intent i = new Intent(_context,TaskDue.class);
        int task = _intent.getIntExtra("Task",0);
        i.putExtra("Task",task);
        if(task == 0) {
            String title = _intent.getStringExtra("DeadLineTitle");
            i.putExtra("DeadLineTitle", title);

            String time = _intent.getStringExtra("Time");
            i.putExtra("Time", time);

            String date = _intent.getStringExtra("Date");
            i.putExtra("Date", date);

            String description = _intent.getStringExtra("Description");
            i.putExtra("Description", description);

            String label1 = _intent.getStringExtra("DeadlineLabel1");
            i.putExtra("DeadlineLabel1",label1);

            String label2 = _intent.getStringExtra("DeadlineLabel2");
            i.putExtra("DeadlineLabel2",label1);

            String label3 = _intent.getStringExtra("DeadlineLabel3");
            i.putExtra("DeadlineLabel3",label1);
        }

        Random r = new Random();

        inboxStyle.setBigContentTitle("ToDoList");
        final PendingIntent pi = PendingIntent.getActivity(_context,  r.nextInt(10000) ,i,PendingIntent.FLAG_UPDATE_CURRENT);

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