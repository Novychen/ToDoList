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
import android.widget.ListAdapter;
import android.widget.ListView;

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
 * Repeat mTask algorithmus überlegen für Notifications
 * offline Daten
 * Wechseln zwischen tasks
 * notificationsarten einstellen
 * wiederholgun der noti einsetellen
 * gemeinere Notis nach der Zeit
 * notis fixen (es kommen zu viele)
 * repeat mTask fixen
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
    private List<String> mTitleRep;
    private List<String> mTitleDead;
    private List<List<String>> mDeadLabel;
    List<Integer> mDay;
    List<Integer> mMonth;
    List<Integer> mYear;
    List<Integer> mHour;
    List<Integer> mMin ;
    List<Integer> mTask;
    List<String> mDescriptionDead;
    List<String> refDead;
    List<Boolean> mBrutalDead;
    List<Boolean> mSnarkyDead;
    List<Boolean> mFunnnyDead;
    List<Boolean> mCuteDead;
    List<Boolean> mNormalDead;
    List<Boolean> mNoNotiDead;

    List<String> mDescriptionRep;
    List<String> refRep;
    List<List<String>> mRepeatLabel;
    List<Integer> mRepeats;
    List<String> mCircle;
    List<String> mCircleRepeat;
    List<Boolean> mBrutalRepeat;
    List<Boolean> mSnarkyRepeat;
    List<Boolean> mFunnnyRepeat;
    List<Boolean> mCuteRepeat;
    List<Boolean> mNormalRepeat;
    List<Boolean> mNoNotiRepeat;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/

        Log.i(TAG, "OnCreate :: ActivityList");

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref2, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getLongData(ref);

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
        mCuteMotivation.add("Life is not a problem to be solved but a gift to be enjoyed. Make the best of this mDay!");
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
        Log.i(TAG,"TASK SIZE after SetALL-------->"+ mTask.size());
        Log.i(TAG,"mCircle---------->" + mCircle);
        i.putExtra("mTask", mTask.get(_position));
        int deadp =0;
        int repp =0;
        for (int p  = 0; p!=_position;p++){
            if(mTask.get(p)==0) {
                deadp++;
            }else {
                repp++;
            }
        }
        Log.i(TAG, "dead " + deadp);
        Log.i(TAG, "rep " + repp);

        if(mTask.get(_position)==0){
            i.putExtra("title", mTitleDead.get(deadp));
            i.putExtra("ref",refDead.get(deadp));
            StringBuilder s= new StringBuilder();
         //   s.append(mDay.get(deadp)+"."+mMonth.get(deadp)+"."+mYear.get(deadp));
            i.putExtra("date", mDay.get(deadp) + "." + mMonth.get(deadp) + "." + mYear.get(deadp));
            s = new StringBuilder();
          //  s.append(mHour.get(deadp)+":"+mMin.get(deadp));
            i.putExtra("time",s.toString());
            i.putExtra("des", mDescriptionDead.get(deadp));
            Log.i(TAG,"title,ref,time,des---------->"+ mTitleDead.get(deadp)+" ,"+refDead.get(deadp)+" ,"+s+" ,"+ mDescriptionDead.get(deadp));

            i.putExtra("brutal", mBrutalDead.get(deadp));
            i.putExtra("snarky", mSnarkyDead.get(deadp));
            i.putExtra("funny", mFunnnyDead.get(deadp));
            i.putExtra("cute", mCuteDead.get(deadp));
            i.putExtra("normal", mNormalDead.get(deadp));
            i.putExtra("noNoti", mNoNotiDead.get(deadp));
            i.putExtra("label1", mDeadLabel.get(deadp).get(0));
            i.putExtra("label2", mDeadLabel.get(deadp).get(1));
            i.putExtra("label3", mDeadLabel.get(deadp).get(2));

        }else {
            i.putExtra("title", mTitleRep.get(repp));
            i.putExtra("ref",refRep.get(repp));
            StringBuilder s= new StringBuilder();
            String x = mRepeats.get(repp)+" times per " + mCircle.get(repp);
            Log.i(TAG,"mCircle---------->" + mRepeats.get(repp)+" times per "+ mCircle.get(repp));
            i.putExtra("date", x);
            i.putExtra("des", mDescriptionRep.get(repp));
            i.putExtra("brutal", mBrutalDead.get(repp));
            i.putExtra("snarky", mSnarkyDead.get(repp));
            i.putExtra("funny", mFunnnyDead.get(repp));
            i.putExtra("cute", mCuteDead.get(repp));
            i.putExtra("normal", mNormalDead.get(repp));
            i.putExtra("noNoti", mNoNotiDead.get(repp));
            i.putExtra("label1", mRepeatLabel.get(repp).get(0));
            i.putExtra("label2", mRepeatLabel.get(repp).get(1));
            i.putExtra("label3", mRepeatLabel.get(repp).get(2));
            Log.i(TAG,"title,ref,time,des---------->"+ mTitleRep.get(repp)+" ,"+refRep.get(repp)+" ,"+s+" ,"+ mDescriptionRep.get(repp));
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
    public void setNotificationDeadlineData(List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _h, List<Integer> _min, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal, List<Boolean> _notification, List<Integer> _count, List<String> _ref, List<String> _des, List<List<String>> _label) {

        mTitleDead =_t;
        mDescriptionDead =_des;
        mDeadLabel = _label;
        mDay =_d;
        mMonth = _m;
        mYear =_y;
        mHour =_h;
        mMin = _min;
        refDead = _ref;

        List <String> motivation = new LinkedList<>();

        for (int i = 0; i < _d.size(); i++) {
            if( _notification.get(i) && _count.get(i) < 1) {
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
                intent.putExtra("NotificationTitle", "The time for your mTask: " + _t.get(i) + " is up!");
                intent.putExtra("text", "You better have your things done");
                intent.putExtra("mTask", 0);

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
                intent.putExtra("title", _t.get(i));
                intent.putExtra("time",stringHour + ":" + stringMin);
                intent.putExtra("date", _d.get(i) + "." + _m.get(i) + "." + _y.get(i));
                intent.putExtra("des", _des.get(i));
                intent.putExtra("label1",_label.get(i).get(0));
                intent.putExtra("label2",_label.get(i).get(1));
                intent.putExtra("label3",_label.get(i).get(2));
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

    @Override
    public void setNotificationRepeatData(List<Integer>_repeats, List<String> _repeatCircle, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal, List<Boolean> _notification,List<String> _ref, List<String> _des, List<List<String>> _label) {
        try{

            mTitleRep =_t;
            mDescriptionRep = _des;
            refRep = _ref;
            mRepeats = _repeats;
            mCircleRepeat = _repeatCircle;
            mBrutalRepeat = _brutal;
            mSnarkyRepeat = _snarky;
            mFunnnyRepeat = _funny;
            mCuteRepeat = _cute;
            mNormalRepeat = _norm;
            mNoNotiRepeat = _notification;
            mRepeatLabel = _label;

        if (_t.size() != 0) {
            _repeatCircle.remove(_repeatCircle.size() - 1);
            _repeats.remove(_repeats.size() - 1);
        }

        for (int i = 0; i < _repeats.size(); i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            if (_repeats.get(i) == null) {
                _repeats.set(i, 0);
            }
            if(_repeatCircle.get(i).equals("mYear")) {

            }
            if(_repeatCircle.get(i).equals("mMonth")) {

            }
            if(_repeatCircle.get(i).equals("week")) {
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
            intent.putExtra("title", "The time for your mTask: " + _t.get(i) + " is up!");
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
                mTask = _task;
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
    protected void onResume() {
        super.onResume();

    }
}
