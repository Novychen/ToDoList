package at.fhooe.mc.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityList extends Activity implements View.OnClickListener {

    private final static String TAG = "at.fhooe.mc.toDoList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list);

        Button b = null;

        b = (Button) findViewById(R.id.list_Activity_LogOut_Button);
        b.setOnClickListener(this);

        b = (Button) findViewById(R.id.list_Activity_add_button);
        b.setOnClickListener(this);

        b = (Button) findViewById(R.id.list_Activity_delete_button);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_Activity_LogOut_Button: {
                Log.i(TAG, "list_Activity::onClick logOut Button was pressed");
                logOut();
                finish();
            }
            break;
            case R.id.list_Activity_add_button: {
                Log.i(TAG, "list_Activity::onClick add Button was pressed");
                Intent i = new Intent(this, ActivityDeadlineTask.class);
                startActivity(i);
            }
            break;
            case R.id.list_Activity_delete_button: {
                Log.e(TAG, "list_Activity::onClick delete Button was pressed");

            }
            break;
            default:
                Log.e(TAG, "list_Activity::onClick unexpected ID encountered");
        }
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
