package at.fhooe.mc.android;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
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
 * offline Daten
 * Wechseln zwischen tasks
 * notificationsarten einstellen
 * wiederholgun der noti einsetellen
 * gemeinere Notis nach der Zeit
 * notis fixen (es kommen zu viele)
 * repeat task fixen
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
    List<String> tREp=null;
    List<String> tDead=null;
    List<Integer> day = null;
    List<Integer> month = null;
    List<Integer> year = null;
    List<Integer> hour = null;
    List<Integer> min = null ;
    List<Integer> task = null;
    List<String> desRep = null;
    List<String> refRep = null;
    List<String> desDead = null;
    List<String> refDead = null;
    List<Boolean> mBrutal = null;
    List<Boolean> mSnarky = null;
    List<Boolean> mFunnny = null;
    List<Boolean> mCute = null;
    List<Boolean> mNormal = null;
    List<Boolean> mNoNoti = null;

    List<List<String>> label = null;
    List<Integer> repeats = null;
    List<String> circle = null;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/

        Log.e(TAG, "OnCreate :: ActivityList");

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref2, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getLongData(ref);

        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getNotificationData(ref3, this);

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
        Log.i(TAG,"TASK SIZE after SetALL-------->"+task.size());
        i.putExtra("task", task.get(_position));
        int deadp =0;
        int repp =0;
        for (int p  = 0; p!=_position;p++){
            if(task.get(p)==0) {
                deadp++;
            }else {
                repp++;
            }
        }
        Log.i(TAG, "dead " + deadp);
        Log.i(TAG, "rep " + repp);
        if(task.get(_position)==0){
            i.putExtra("title",tDead.get(deadp));
            i.putExtra("ref",refDead.get(deadp));
            StringBuilder s= new StringBuilder();
         //   s.append(day.get(deadp)+"."+month.get(deadp)+"."+year.get(deadp));
            i.putExtra("date",day.get(deadp) + "." + month.get(deadp) + "." + year.get(deadp));
            s = new StringBuilder();
          //  s.append(hour.get(deadp)+":"+min.get(deadp));
            i.putExtra("time",s.toString());
            i.putExtra("des",desDead.get(deadp));
            Log.i(TAG,"title,ref,time,des---------->"+tDead.get(deadp)+" ,"+refDead.get(deadp)+" ,"+s+" ,"+desDead.get(deadp));

            /*
            hier sind meine Extras für die 6 Buttons, ich übergebe sie der TaskDue wo ich sie weiter
            verarbeite. Damit ich sie hier verwenden kann habe ich die setAll um diese 6 Listen erweitert - Yvonne
            --------------------------------------------------------------- */
            i.putExtra("brutal", mBrutal.get(deadp));
            i.putExtra("snarky", mSnarky.get(deadp));
            i.putExtra("funny", mFunnny.get(deadp));
            i.putExtra("cute", mCute.get(deadp));
            i.putExtra("normal", mNormal.get(deadp));
            i.putExtra("noNoti", mNoNoti.get(deadp));
            //--------------------------------------------------------------
        }else {
            i.putExtra("title",tREp.get(repp));
            i.putExtra("ref",refRep.get(repp));
            StringBuilder s= new StringBuilder();
           // s.append(repeats.get(repp)+" times per "+circle.get(repp));
            i.putExtra("date",s.toString());
            i.putExtra("des",desRep.get(repp));
            Log.i(TAG,"title,ref,time,des---------->"+tREp.get(repp)+" ,"+refRep.get(repp)+" ,"+s+" ,"+desRep.get(repp));
        }


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
    public void setNotificationDeadlineData(List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _h, List<Integer> _min, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal,List<Boolean> _notification, List<Integer> _count, List<String> _ref) { // List<String> _des, List<List<String>> _label
        {

            List <String> motivation = new LinkedList<>();

            for (int i = 0; i < _d.size(); i++) {
                if(_notification.get(i) && _count.get(i) < 1) {
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
                    intent.putExtra("text", "You better have your things done");
                    intent.putExtra("Task", 0);
                    intent.putExtra("DeadLineTitle", _t.get(i));
                    /*intent.putExtra("DeadlineLabel1", _label.get(i).get(0));
                    intent.putExtra("DeadlineLabel2", _label.get(i).get(1));
                    intent.putExtra("DeadlineLabel3", _label.get(i).get(2));*/

                    int m = _min.get(i);
                    int h = _h.get(i);
                    String stringMin = String.valueOf(m);
                    String stringHour = String.valueOf(h);
                    if(m < 10){
                        stringMin = "0" + m;
                    }
                    if(h < 10){
                        stringHour = "0" + h;
                    }
                    intent.putExtra("Time",stringHour + ":" + stringMin);
                    intent.putExtra("Date", _d.get(i) + "." + _m.get(i) + "." + _y.get(i));
                    //intent.putExtra("Description", _des.get(i));
                    intent.putExtra("Notification", _notification.get(i));
                    Repository.getInstance().saveData(_count.get(i) +1,_ref.get(i));
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pi = PendingIntent.getBroadcast(this, r.nextInt(1000), intent, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

                    if (_norm.get(i)) {
                        motivation.addAll(mMotivation);
                    } else if (_brutal.get(i)) {
                        motivation.addAll(mBrutalMotivation);
                    } else if (_cute.get(i)) {
                        motivation.addAll(mCuteMotivation);
                    } else if (_funny.get(i)) {
                        motivation.addAll(mFunnyMotivation);
                    } else if (_snarky.get(i)) {
                        motivation.addAll(mSnarkyMotivation);
                    }
                }
            }

            Random r = new Random();
            Calendar calendar = Calendar.getInstance();
            int size = motivation.size();
            if(size != 0) {
                if (mGotIntoData != 1) {
                    for (int i = 0; i < 4; i++) {
                        calendar.setTime(new Date());
                        calendar.add(Calendar.HOUR_OF_DAY, r.nextInt(12));
                        calendar.add(Calendar.MINUTE, r.nextInt(60));
                        Intent intent = new Intent(this, NotificationAlarm.class);
                        intent.putExtra("title", "Motivation coming through");
                        intent.putExtra("text", motivation.get(r.nextInt(size)));
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        PendingIntent pi = PendingIntent.getBroadcast(this, i, intent, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                    }
                }
                mGotIntoData++;
            }
        }
    }

    @Override
    public void setNotificationRepeatData(List<Integer> _r, List<String> _c, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal ,List<Boolean> _notification) {

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
            Log.e(TAG, "setNotificationData: " + e.getMessage());
        }catch(NullPointerException e){
            Log.e(TAG, "setNotificationData: " + e.getMessage());
        }catch(IndexOutOfBoundsException e){
            Log.e(TAG, "setNotificationData: " + e.getMessage());
        }

    }

    @Override
    public void setTitle(List<String> _repeatT, List<String> _deadT, List<Integer> _task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _repeats, List<String> _repeatCircle) {

            Log.i(TAG, "LIST size --> " + _task.size());
            try {
                adapter.clear();

                int deadi= 0;
                int repi = 0;
                for (int i = 0; i < _task.size(); i++) {
                    if(_task.get(i)==0){
                        adapter.add(new ListData(_deadT.get(deadi),_d.get(deadi),_m.get(deadi),_y.get(deadi)));
                        Log.i(TAG, adapter.getCount() + " deadLine tile,ref,des"+i+" --> " + _deadT.get(deadi));
                        deadi++;
                    }
                    if(_task.get(i)==1){
                        adapter.add(new ListData(_repeatT.get(repi),_repeats.get(repi),_repeatCircle.get(repi)));
                        Log.i(TAG, adapter.getCount() + " deadLine tile,ref,des"+i+" --> " + _repeatT.get(repi));
                        repi++;
                    }
                }
                adapter.notifyDataSetChanged();

            } catch (ClassCastException e) {
                Log.e(TAG, "setTitle: " + e.getMessage());
            } catch (NullPointerException e) {
                Log.e(TAG, "setTitle: " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                Log.e(TAG, "setTitle: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "setTitle: " + e.getMessage());
            }
    }

    @Override
    public void setAll(List<String> _repeatT, List<String> _deadT, List<Integer> _d, List<Integer> _mo, List<Integer> _y, List<Integer> _h, List<Integer> _mi, List<Integer> _t, List<List<String>> _label,List<String> _desrep,List<String> _desdead,List<String> _refrep,List<String> _refdead,List<Integer> _repeats, List<String> _repeatCircle, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal,List<Boolean> _notification) {
        tDead = _deadT;
        tREp =_repeatT;
        task = _t;
        day =_d;
        month = _mo;
        year=_y;
        hour =_h;
        min = _mi;
        desDead=_desdead;
        desRep=_desrep;
        refDead=_refdead;
        refRep=_refrep;
        repeats = _repeats;
        circle = _repeatCircle;
        mBrutal = _brutal;
        mSnarky = _snarky;
        mFunnny = _funny;
        mCute = _cute;
        mNormal = _norm;
        mNoNoti = _notification;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
