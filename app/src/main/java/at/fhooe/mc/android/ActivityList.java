package at.fhooe.mc.android;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


/**
 * this class displays the toDoList and implements action that can manipulate the list
 */
public class ActivityList extends ListActivity implements IFirebaseCallback{

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityList";
    static DataAdapter adapter;
    List<String> title = null;
    List<Integer> day = null;
    List<Integer> month = null;
    List<Integer> year = null;
    List<Integer> hour = null;
    List<Integer> min = null ;
    List<Integer> task = null;
    List<String> des = null;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref2, this);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getData(ref, this);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/



        adapter = new DataAdapter(this);
        //    addData(adapter);
        setListAdapter(adapter);

        final ActionBar ab = getActionBar();
        ab.setHomeButtonEnabled(true);

        CheckBox check = (CheckBox) findViewById(R.id.activity_list_checkbox);

    }




    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListAdapter list = getListAdapter();
        ListData       item = (ListData) list.getItem(position);
       StringBuilder s = new StringBuilder();
       s.append(position);
        Log.i(TAG, "Position ------> " +s.toString() );
        Toast.makeText(this, "clicked item " + item, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TaskDue.class);
        i.putExtra("title", title.get(position));
        i.putExtra("day",day.get(position));
        i.putExtra("month", month.get(position));
        i.putExtra("year",year.get(position));
        i.putExtra("hour", hour.get(position));
        i.putExtra("min",min.get(position));
        i.putExtra("des",des.get(position));

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
            case R.id.menu_arlog_add: {
                Log.i(TAG, "::onClick add Button was pressed");
                Intent i = new Intent(this, ActivityDeadlineTask.class);

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

    }

    @Override
    public void setStringData(List<String> s) {

    }

    @Override
    public void setTimeData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<Integer> task) {

        if(d.size() != 0) {
            d.remove(d.size() - 1);
            m.remove(m.size() - 1);
            y.remove(y.size() - 1);
            h.remove(h.size() - 1);
            min.remove(min.size() - 1);
        }

        Log.i(TAG, ":: setTimeData - day List -----> " + d.toString());
        Log.i(TAG, ":: setTimeData - month List -----> " + m.toString());
        Log.i(TAG, ":: setTimeData - year List -----> " + y.toString());
        Log.i(TAG, ":: setTimeData - hour List -----> " + h.toString());
        Log.i(TAG, ":: setTimeData - minute List -----> " + min.toString());

        List<PendingIntent> piList = new LinkedList<>();

        for(int i = 0; i <d.size(); i++) {


                if (task.get(i) != null && task.get(i) == 0) {
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
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Log.i(TAG, "Time:      -----> " + calendar.getTime());
                    NotificationAlarm a = new NotificationAlarm();
                    a.setCheckTime(year + "." + month + "." + day + "   " + hour + ":" + minute);
                    Log.i(TAG, "Message:   -----> " + a.getCheckTime());
                    Intent intent = new Intent(this, NotificationAlarm.class);
                    PendingIntent pi = PendingIntent.getBroadcast(this, i, intent, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                    piList.add(pi);
                }
            }
    }

    @Override
    public void setTitle(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y) {
        try {
            Log.i(TAG, "LIST size --> " + s.size());
            adapter.clear();
            for(int i = 0; i < s.size(); i++){
                Log.i(TAG, adapter.getCount() + " --> " + s.get(i));
                adapter.add(new ListData(s.get(i),d.get(i),m.get(i),y.get(i)));
            }
            adapter.notifyDataSetChanged();
//            setListAdapter(adapter);

        }catch (ClassCastException e) {

        }catch(NullPointerException e){

        }catch(IndexOutOfBoundsException e){
            return;
        }
    }

    @Override
    public void setAll(List<String> _s, List<Integer> _d, List<Integer> _mo, List<Integer> _y, List<Integer> _h, List<Integer> _mi, List<Integer> _t, List<String> _des) {
        title = _s;
        day = _d;
        month = _mo;
        year = _y;
        hour = _h;
        min =_mi;
        task = _t;
        des = _des;
    }


}
