package at.fhooe.mc.android;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.Random;
public class NotificationAlarm extends BroadcastReceiver  {


    public static final String TAG = "at.fhooe.mc.toDoList :: NotificationAlarm";
    private static final  String GROUP_KEY = "at.fhooe.mc.toDoList.GROUP_KEY";
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
    static boolean mGroupEnabled;
    static int mGroupCount;

    public void onReceive(Context _context, Intent _intent) {

        Random r = new Random();
        int value = r.nextInt(10000);
        int groupValue = r.nextInt(10000);

        final Intent i = new Intent(_context,TaskDue.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("NotificationID",value);
        i.putExtra("GroupID",groupValue);
        i.putExtra("fromNotification",true);


        int task = _intent.getIntExtra("task",0);
        i.putExtra("task",task);

        String title = _intent.getStringExtra("title");
        i.putExtra("title", title);

        boolean brutal = _intent.getBooleanExtra("brutal",false);
        i.putExtra("brutal",brutal);

        boolean snarky = _intent.getBooleanExtra("snarky",false);
        i.putExtra("snarky",snarky);

        boolean funny = _intent.getBooleanExtra("funny",false);
        i.putExtra("funny",funny);

        boolean cute = _intent.getBooleanExtra("cute",false);
        i.putExtra("cute",cute);

        boolean normal = _intent.getBooleanExtra("normal",false);
        i.putExtra("normal",normal);

        boolean noNoti = _intent.getBooleanExtra("noNoti",true);
        i.putExtra("noNoti",noNoti);

        String time = _intent.getStringExtra("time");
        i.putExtra("time", time);

        String date = _intent.getStringExtra("date");
        i.putExtra("date", date);

        String description = _intent.getStringExtra("des");
        i.putExtra("des", description);

        String label1 = _intent.getStringExtra("label1");
        i.putExtra("label1",label1);

        String label2 = _intent.getStringExtra("label2");
        i.putExtra("label2",label2);

        String label3 = _intent.getStringExtra("label3");
        i.putExtra("label3",label3);

        inboxStyle.setBigContentTitle("ToDoList");
        final PendingIntent pi = PendingIntent.getActivity(_context, value ,i,PendingIntent.FLAG_UPDATE_CURRENT);

        if(mGroupEnabled) {
            Notification.Builder groupBuilder = new Notification.Builder(_context)
                    .setContentTitle("ToDoList at your service")
                    .setSmallIcon(R.drawable.ic_notification_active)
                    .setGroup(NotificationAlarm.GROUP_KEY)
                    .setContentText("I hope you had a great day :)")
                    .setGroupSummary(true)
                    .setContentIntent(pi);
            Notification group = groupBuilder.build();
            NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.notify(groupValue, group);
        }

        Notification.Builder bob = new Notification.Builder(_context)
                .setSmallIcon(R.drawable.ic_notification_active)
                .setContentText(_intent.getStringExtra("text"))
                .setContentTitle(_intent.getStringExtra("NotificationTitle"))
                .setGroup(GROUP_KEY)
                .setContentIntent(pi); //what happens when you press the notification


        Notification n = bob.build();
        NotificationManager nMgr = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.notify(value, n);

        NotificationAlarm.mGroupEnabled = false;
        mGroupCount++;
        Log.i(TAG,"notification " + value);
    }


}