package at.fhooe.mc.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * this class displays the toDoList and implements action that can manipulate the list
 */
public class ActivityList extends Activity  {

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityList";


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list);

        final ActionBar ab = getActionBar();
        ab.setHomeButtonEnabled(true);
        //DataAdapter adapter = new DataAdapter(this);


        //for(long i = 1; 1<MainActivity.mTaskNumber;i++){}

        // adapter.add(new ListData(Repository.getInstance().getStringData(ref)));
        //setListAdapter(adapter);
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
                long taskNumber = MainActivity.getTaskNumber() + 1;
                MainActivity.setTaskNumber(taskNumber);
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
