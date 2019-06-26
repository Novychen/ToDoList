package at.fhooe.mc.android;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * this class displays the toDoList and implements action that can manipulate the list
 *
 *
 * Repeat task algorithmus überlegen für Notifications
 * remove
 * offline Daten
 * Wechseln zwischen tasks
 * Settings wo Notificationart eingestellt werden kann
 *
 */
public class ActivityList extends ListActivity implements IFirebaseCallback{

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityList";
    static DataAdapter adapter;
    List <String> mSnarkyMotivation = new LinkedList<>();
    List <String> mFunnyMotivation = new LinkedList<>();
    List <String> mCuteMotivation = new LinkedList<>();
    List <String> mMotivation = new LinkedList<>();
    List <String> mBrutalMotivation = new LinkedList<>();
    int mGotIntoData;



    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref2, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getData(ref, this);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/

        mFunnyMotivation.add("People often say that motivation doesn’t last. Well, neither does bathing — that’s why we recommend it daily");
        mSnarkyMotivation.add("Anything's possible if you've got enough nerve.");
        mSnarkyMotivation.add("If you're waiting for a SIGN ... THIS IS IT");
        mSnarkyMotivation.add("It's never too late to get your shit together");

        mBrutalMotivation.add("Get your shit together!");
        mBrutalMotivation.add("Just do it!");
        mBrutalMotivation.add("You have time to think up excuses? Get to work!");
        mBrutalMotivation.add("You don't want to? so what?");
        mBrutalMotivation.add("Get your ass up");
        mBrutalMotivation.add("Don't find excuses and keep moving forward!");

        mMotivation.add("The expert in anything was once a beginner");
        mMotivation.add("Tough times don't last; Tough people do.");
        mMotivation.add("Worrying will never change the outcome");
        mMotivation.add("You can and you will");
        mMotivation.add("If not you, who? If not now. when?");
        mMotivation.add("you have to go through the worst, to get to the best");
        mMotivation.add("the struggle you're in today is developing the strength you need for tomorrow");
        mMotivation.add("To be the best, you must be able to handle the worst.");
        mMotivation.add("The best way out is always through.");

        mCuteMotivation.add("Believe you can and you’re halfway there.");
        mCuteMotivation.add("You can totally do it!");
        mCuteMotivation.add("Life is not a problem to be solved but a gift to be enjoyed. Make the best of this day!");
        mCuteMotivation.add("There's hope for tomorrow, cheer up :D !");
        mCuteMotivation.add("Just a little bit longer - you can do it!");
        mCuteMotivation.add("Don't worry - be happy");
        mCuteMotivation.add("you are so much stronger than you think");
        mCuteMotivation.add("After the rain comes the rainbow");
        mCuteMotivation.add("You are doing great! Keep pushing!");


        adapter = new DataAdapter(this);
        //    addData(adapter);
        setListAdapter(adapter);

        final ActionBar ab = getActionBar();
        ab.setHomeButtonEnabled(true);

        CheckBox check = (CheckBox) findViewById(R.id.activity_list_checkbox);

    }

    private void addData(DataAdapter _adapter) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().
                child(Repository.getInstance().getUserId());
        long l = 01;
        String date = ".07.2019";
        long flow= 12;


        for (int i = 1; i <= flow; i++) {
            StringBuilder task = new StringBuilder("Task ");
            task.append(i);
            String taskS = task.toString();
            DatabaseReference ref = userRef.child(taskS);

            //Task t = Repository.getInstance().getData(ref);
            StringBuilder s = new StringBuilder();
            s.append( taskS);
            String p = s.toString();

            //apter.add(new ListData(p, l + date));
            l++;
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListAdapter list = getListAdapter();
        ListData       item = (ListData) list.getItem(position);
        Toast.makeText(this, "clicked item " + item, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TaskDue.class);
        startActivity(i);
        finish();
     }


    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_arlog, _menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()){
            case R.id.menu_arlog_logout:{
                Log.i(TAG, "::onClick logOut Button was pressed");
                logOut();
                finish();}break;
            case R.id.menu_arlog_add: {
                Log.i(TAG, "::onClick add Button was pressed");
                Intent i = new Intent(this, ActivityRepeatTask.class);
                startActivity(i);
            }break;
            case R.id.menu_arlog_remove: {
                // long taskNumber = MainActivity.getTaskNumber() - 1;
                //MainActivity.setTaskNumber(taskNumber);
                Log.e(TAG, "::onClick delete Button was pressed");
            }
            break;
            default:
                Log.e(TAG, "::onClick unexpected ID encountered");
        }return true;
    }


    /**
     * logs the User out
     */
    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public void setData(Object _o) {
  /*      try {
        List <Object> data = (List<Object>) _o;
        for(int i = 0; data.get(i) != null; i++) {
            Object s = data.get(i);
                if(s.toString().contains("title")) {
                    adapter.add(new ListData(data.get(i)));
                    Log.i(TAG, data.get(i).toString());
                }
        }setListAdapter(adapter);

        }catch (ClassCastException e) {

        }catch(NullPointerException e){

        }catch(IndexOutOfBoundsException e){
            return;
        }*/
    }

    @Override
    public void setStringData(List<String> s) {

    }

    @Override
    public void setNotificationDeadlineData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<String> t) {
        if(d.size() != 0) {
            d.remove(d.size() - 1);
            m.remove(m.size() - 1);
            y.remove(y.size() - 1);
            h.remove(h.size() - 1);
            min.remove(min.size() - 1);
        }

        List<PendingIntent> piList = new LinkedList<>();

        for(int i = 0; i <d.size(); i++) {


                Calendar calendar = Calendar.getInstance();
                if (d.get(i) == null) {
                    d.set(i, 0);
                }
                if (m.get(i) == null) {
                    m.set(i, 0);
                }
                if (y.get(i) == null) {
                    y.set(i, 0);
                }
                if (h.get(i) == null) {
                    h.set(i, 0);
                }
                if (min.get(i) == null) {
                    min.set(i, 0);
                }
                int year = y.get(i);
                int month = m.get(i) - 1;
                int day = d.get(i);
                int hour = h.get(i);
                int minute = min.get(i);

                calendar.set(year, month, day, hour, minute);

                Random r = new Random();
                Intent intent = new Intent(this, NotificationAlarm.class);
                intent.putExtra("title", "The time for your task: " + t.get(i) + " is up!");
                intent.putExtra("text","You better have your things done");
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi = PendingIntent.getBroadcast(this, r.nextInt(1000), intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
            }


            Random r = new Random();
            Calendar calendar = Calendar.getInstance();
            int brutal = mBrutalMotivation.size();
            int cute = mCuteMotivation.size();
            int funny = mFunnyMotivation.size();
            int motivation = mMotivation.size();
            int snarky = mSnarkyMotivation.size();

            if(mGotIntoData != 1) {
                for (int i = 0; i < 4; i++) {
                    calendar.setTime(new Date());
                    calendar.add(Calendar.HOUR_OF_DAY, r.nextInt(12));
                    calendar.add(Calendar.MINUTE, r.nextInt(60));
                    Intent intent = new Intent(this, NotificationAlarm.class);
                    intent.putExtra("title","Motivation coming through");
                    intent.putExtra("text",mMotivation.get(r.nextInt(motivation)));
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pi = PendingIntent.getBroadcast(this, i, intent, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                }
            }
        mGotIntoData++;
    }

    @Override
    public void setNotificationRepeatData(List<Integer> r, List<String> c, List<String> t) {

        try {
            if (t.size() != 0) {
                c.remove(c.size() - 1);
                r.remove(r.size() - 1);
            }

            for (int i = 0; i < r.size(); i++) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                if (r.get(i) == null) {
                    r.set(i, 0);
                }
                if(c.get(i) == "year") {

                }
                if(c.get(i) == "month") {

                }
                if(c.get(i) == "week") {
                    calendar.add(Calendar.DAY_OF_MONTH,2);

                }

                int year = 0;
                int month = 0;
                int day = 0;
                int hour = 0;
                int minute = 0;

                calendar.set(year, month, day, hour, minute);

                Random rand = new Random();
                Intent intent = new Intent(this, NotificationAlarm.class);
                intent.putExtra("title", "The time for your task: " + t.get(i) + " is up!");
                intent.putExtra("text", "You better have your things done");
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi = PendingIntent.getBroadcast(this, rand.nextInt(1000), intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
            }
        }catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }catch(NullPointerException e){
            Log.e(TAG, e.getMessage());
        }catch(IndexOutOfBoundsException e){
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void setTitle(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y) {
        try {
            for(int i = 0; s.get(i) != null; i++){
                adapter.add(new ListData(s.get(i),d.get(i),m.get(i),y.get(i)));
                Log.i(TAG, s.get(i));
            }setListAdapter(adapter);

        }catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }catch(NullPointerException e){
            Log.e(TAG, e.getMessage());
        }catch(IndexOutOfBoundsException e){
            Log.e(TAG, e.getMessage());
        }
    }




}
