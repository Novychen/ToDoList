package at.fhooe.mc.android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * this class displays the toDoList and implements action that can manipulate the list
 */
public class ActivityList extends ListActivity{

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityList";
    private static List<String> mDate = new LinkedList<String>();
    public Object t;

    public static List<String> getDate(){
        return mDate;
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/

        DataAdapter adapter = new DataAdapter(this);
        addData(adapter);
       setListAdapter(adapter);

        final ActionBar ab = getActionBar();
        ab.setHomeButtonEnabled(true);
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

            Task t = Repository.getInstance().getTaskData(ref);
            StringBuilder s = new StringBuilder();
            s.append(t);
            s.append(": " + taskS);
            String p = s.toString();

            _adapter.add(new ListData(p, l+date));
            l++;
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListAdapter list = getListAdapter();
        ListData       item = (ListData) list.getItem(position);
        Toast.makeText(this, "clicked item " + item, Toast.LENGTH_SHORT).show();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list);

        for (int i = 1; i <= 6; i++) {
            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("Task " + i).child("date");
            Repository.getInstance().getData(ref2);
            final ActionBar ab = getActionBar();
            ab.setHomeButtonEnabled(true);
            //DataAdapter adapter = new DataAdapter(this);


            //for(long i = 1; 1<MainActivity.mTaskNumber;i++){}

            // adapter.add(new ListData(Repository.getInstance().getStringData(ref)));
            //setListAdapter(adapter);
        }

    }
    protected static void setValue(Object o) {
        mDate.add(o.toString());
        Log.i(MainActivity.TAG, "setValue --> Object Value is: " + mDate);

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
                Log.i(TAG, "list_Activity::onClick logOut Button was pressed");
                logOut();
                finish();}break;
            case R.id.menu_arlog_add: {
                Log.i(TAG, "list_Activity::onClick add Button was pressed");
                Intent i = new Intent(this, ActivityDeadlineTask.class);

                startActivity(i);
            }break;
            case R.id.menu_arlog_remove: {
                // long taskNumber = MainActivity.getTaskNumber() - 1;
                //MainActivity.setTaskNumber(taskNumber);
                Log.e(TAG, "list_Activity::onClick delete Button was pressed");
            }
            break;
            default:
                Log.e(TAG, "list_Activity::onClick unexpected ID encountered");
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
}
