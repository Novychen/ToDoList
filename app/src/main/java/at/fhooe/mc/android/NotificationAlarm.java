package at.fhooe.mc.android;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;
public class NotificationAlarm extends BroadcastReceiver implements IFirebaseCallback {

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

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref2, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getData(ref, this);
        Log.i(TAG,"refenecen ------------->"+ref);
        Log.i(TAG,"refenecen user ------------->"+ref2);

        Intent i = new Intent(_context,TaskDue.class);
        i.putExtra("title", "versuch");
        i.putExtra("day",11);
        i.putExtra("month", 8);
        i.putExtra("year",2019);
        i.putExtra("hour", 20);
        i.putExtra("min",45);
        i.putExtra("des","hello");
        i.putExtra("ref","ref");
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

    @Override
    public void setNotificationDeadlineData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<String> t) {

    }

    @Override
    public void setNotificationRepeatData(List<Integer> r, List<String> c, List<String> t) {

    }

    @Override
    public void setTitle(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y) {

    }

    @Override
    public void setAll(List<String> _s, List<Integer> _d, List<Integer> _mo, List<Integer> _y, List<Integer> _h, List<Integer> _mi, List<Integer> _t, List<String> _des, List<String> _ref, List<List<String>> _label) {
/*        title = _s;
        day = _d;
        month = _mo;
        year = _y;
        hour = _h;
        min =_mi;
        task = _t;
        des = _des;
        ref = _ref;
        label = _label;*/

    }
}