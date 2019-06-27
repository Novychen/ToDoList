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

    List<String> title = null;
    List<Integer> day = null;
    List<Integer> month = null;
    List<Integer> year = null;
    List<Integer> hour = null;
    List<Integer> min = null ;
    List<Integer> task = null;
    List<String> des = null;
    List<String> ref = null;
    List<List<String>> label = null;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref2, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getData(ref, this);

        adapter = new DataAdapter(this);
        setListAdapter(adapter);


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



        final ActionBar ab = getActionBar();
        ab.setHomeButtonEnabled(true);


    }





    @Override
    protected void onListItemClick(ListView l, View v, int _position, long id) {
        ListAdapter list = getListAdapter();
        ListData       item = (ListData) list.getItem(_position);
        Log.i(TAG, "Position ------> " + _position );
        Log.i(TAG, "clicked item " + item);
        Intent i = new Intent(this, TaskDue.class);
        i.putExtra("title", title.get(_position));
        i.putExtra("day",day.get(_position));
        i.putExtra("month", month.get(_position));
        i.putExtra("year",year.get(_position));
        i.putExtra("hour", hour.get(_position));
        i.putExtra("min",min.get(_position));
        i.putExtra("des",des.get(_position));
        i.putExtra("ref",ref.get(_position));
        i.putExtra("label_0",label.get(_position).get(0));
        i.putExtra("label_1",label.get(_position).get(1));
        i.putExtra("label_2",label.get(_position).get(2));
        startActivity(i);
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
            case R.id.menu_arlog_add_dead: {
                Log.i(TAG, "::onClick add Button was pressed");
                Intent i = new Intent(this, ActivityDeadlineTask.class);
                startActivity(i);
            }break;

            case R.id.menu_arlog_add_rep: {
                Log.i(TAG, "::onClick add Button was pressed");
                Intent i = new Intent(this, ActivityRepeatTask.class);
                startActivity(i);
            }break;

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
    public void setNotificationDeadlineData(List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _h, List<Integer> _min, List<String> _t) {
        if(_d.size() != 0) {
            _d.remove(_d.size() - 1);
            _m.remove(_m.size() - 1);
            _y.remove(_y.size() - 1);
            _h.remove(_h.size() - 1);
            _min.remove(_min.size() - 1);
        }

        List<PendingIntent> piList = new LinkedList<>();

        for(int i = 0; i <_d.size(); i++) {

                Calendar calendar = Calendar.getInstance();
                if (_d.get(i) == null) {
                    _d.set(i, 0);
                }
                if (_m.get(i) == null) {
                    _m.set(i, 0);
                }
                if (_y.get(i) == null) {
                    _y.set(i, 0);
                }
                if (_h.get(i) == null) {
                    _h.set(i, 0);
                }
                if (_min.get(i) == null) {
                    _min.set(i, 0);
                }
                int year = _y.get(i);
                int month = _m.get(i) - 1;
                int day = _d.get(i);
                int hour = _h.get(i);
                int minute = _min.get(i);

                calendar.set(year, month, day, hour, minute);

                Random r = new Random();
                Intent intent = new Intent(this, NotificationAlarm.class);
                intent.putExtra("title", "The time for your task: " + _t.get(i) + " is up!");
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
    public void setNotificationRepeatData(List<Integer> _r, List<String> _c, List<String> _t) {

        try {
            if (_t.size() != 0) {
                _c.remove(_c.size() - 1);
                _r.remove(_r.size() - 1);
            }

            for (int i = 0; i < _r.size(); i++) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                if (_r.get(i) == null) {
                    _r.set(i, 0);
                }
                if(_c.get(i).equals("year")) {

                }
                if(_c.get(i).equals("month")) {

                }
                if(_c.get(i).equals("week")) {
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
                intent.putExtra("title", "The time for your task: " + _t.get(i) + " is up!");
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
    public void setTitle(List<String> _s, List<Integer> _d, List<Integer> _m, List<Integer> _y) {


            Log.i(TAG, "LIST size --> " + _s.size());
            try {adapter.clear();

                Log.i(TAG, "LIST size --> " + _s.size());

                Log.i(TAG, "LIST  1 --> " + _s.get(0));
                Log.i(TAG, "LIST  2 --> " + _s.get(1));
                Log.i(TAG, "LIST  3 --> " + _s.get(2));
                Log.i(TAG, "LIST  4 --> " + _s.get(3));
                for (int i = 0; i < _s.size(); i++) {

                    adapter.add(new ListData(_s.get(i), _d.get(i), _m.get(i), _y.get(i)));
                    Log.i(TAG, adapter.getCount() + " --> " + _s.get(i));
                }
                adapter.notifyDataSetChanged();

            } catch (ClassCastException e) {
                Log.e(TAG, e.getMessage());
            } catch (NullPointerException e) {
                Log.e(TAG, e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                Log.e(TAG, e.getMessage());
            }
    }

    @Override
    public void setAll(List<String> _s, List<Integer> _d, List<Integer> _mo, List<Integer> _y, List<Integer> _h, List<Integer> _mi, List<Integer> _t, List<String> _des,List<String> _ref, List<List<String>> _label) {
        title = _s;
        day = _d;
        month = _mo;
        year = _y;
        hour = _h;
        min =_mi;
        task = _t;
        des = _des;
        ref = _ref;
        label = _label;

        Log.i(TAG,"Label: " + _label);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
