package at.fhooe.mc.android;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import java.util.List;


/**
 * this class displays the toDoList and implements action that can manipulate the list
 */
public class ActivityList extends ListActivity implements IFirebaseCallback{

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityList";
    static DataAdapter adapter;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setContentView(R.layout.activity_list);*/

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref, this);

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

            adapter.add(new ListData(p, l + date));
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

            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
            Repository.getInstance().getData(ref2, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getData(ref, this);

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
        try {
        List <Object> data = (List<Object>) _o;
        for(int i = 0; data.get(i) != null; i++) {
            Object s = data.get(i);

                adapter.add(new ListData(data.get(i)));
                Log.i(TAG, data.get(i).toString());

        }setListAdapter(adapter);

        }catch (ClassCastException e) {

        }catch(NullPointerException e){

        }catch(IndexOutOfBoundsException e){
            return;
        }
    }


}
