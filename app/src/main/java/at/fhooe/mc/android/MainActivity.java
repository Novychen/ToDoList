package at.fhooe.mc.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * This class implements the MainActivity as well as the login/Signin Process from the user
 */
public class MainActivity extends Activity implements View.OnClickListener, IFirebaseCallback {

    private FirebaseAuth mAuthentication;
    public final static String TAG = "at.fhooe.mc.toDoList :: MainActivity";
    private EditText mEmail = null;
    private EditText mPassword = null;
    static long mTaskNumber;

    public static long getTaskNumber(){
        return mTaskNumber;
    }

    public static void setTaskNumber(long _number){
        mTaskNumber = _number;
    }


    /**
     * listens to the Buttons and checks if the user is already logged in if so it changes to {@link ActivityList}
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.main_Activity_Auth_LogIn_Email);
        mPassword = findViewById(R.id.main_Activity_Auth_LogIn_Password);

        Button b;
        b = findViewById(R.id.main_Activity_LogIn_Button);
        b.setOnClickListener(this);

        b = findViewById(R.id.main_Activity_SignIn_Button);
        b.setOnClickListener(this);

        mAuthentication = FirebaseAuth.getInstance();
        Repository.getInstance().setUserId(mAuthentication.getUid());
        if (Repository.getInstance().getUserId() != null) { // is user already logged in?
            logIn();
        } 
    }

    /**
     * logs the user in and changes the activity to {@link ActivityList}
     */
    private void logIn() {
        Intent i = new Intent(this, ActivityList.class);
        Repository.getInstance().setUserId(mAuthentication.getUid());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId());
        Repository.getInstance().getData(ref, this);

        DatabaseReference refTask = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child("CurrentTask");
        Repository.getInstance().getLongData(refTask);
        startActivity(i);
        finish();
    }

    /**
     * starts the login process for the user
     * if the data is correct the user is logged in, if not a error message is displayed
     */
    private void signIn() {
        mAuthentication.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "main_Activity::signInWithEmail:success");
                            logIn();

                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "main_Activity::signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_LogInFail_Toast), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.main_Activity_LogIn_Button: {
                signIn();
            }
            break;
            case R.id.main_Activity_SignIn_Button: {
                createAccount();
            }
            break;
            default:
                Log.e(TAG, "main_Activity::onClick unexpected ID encountered");
        }
    }

    /**
     * creates Account in database
     */
    private void createAccount() {

        mAuthentication.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "main_Activity::createUserWithEmail success");
                            Repository.getInstance().setUserId(mAuthentication.getUid());
                            mTaskNumber = 0;
                            Repository.getInstance().saveData(mTaskNumber);
                            Log.i(TAG, "MainActivity :: createAccount mTaskNumber is" + mTaskNumber);
                            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_SignIn_Toast), Toast.LENGTH_SHORT).show();
                            logIn();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "main_Activity::createUserWithEmail failure - could not create account", task.getException());
                            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_SignInFail_Toast), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void setData(Object _o) {
        try {
            List<Object> data = (List<Object>) _o;
            //setAlarm(data);
        }catch(IndexOutOfBoundsException e){
            return;
        }
    }
   /* public void setAlarm(List <Object> _data){

        int[] alarm = new int[_data.size() * 5];

        String month;
        String day;
        String hour;
        String minute;

        for (int i = 0; i < alarm.length; i  = i + 5){
            String s = _data.get(i).toString();
            int d = s.indexOf("day=") + 4;
            int m = s.indexOf("month=") + 6;

            int y = s.indexOf("year=") + 5;
            int h = s.indexOf("hour=") + 4;
            int min = s.indexOf("minute=") + 6;

            if(s.substring(++d) == ",") {
                day = s.substring(--d);
            }else {
                day = s.substring(d, ++d);
            }

            if(s.substring(++m) == ","){
                month = s.substring(m);
            }else {
                month = s.substring(m, --m);
            }
            String year = s.substring(y, y + 4);

            if(s.substring(++h) == ",") {
                hour = s.substring(--h);
            }else{
                hour = s.substring(h, ++h);
            }
            if(s.substring(++min) == ",") {
                minute = s.substring(--min);
            }else{
                minute = s.substring(min, ++min);
            }

            alarm [i] = Integer.parseInt(day);
            alarm [++i] = Integer.parseInt(month);
            alarm [++i] = Integer.parseInt(year);
            alarm [++i] = Integer.parseInt(hour);
            alarm [++i] = Integer.parseInt(minute);
        }

        for(int i = 0; i <alarm.length; i = i +5) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(alarm[i], alarm[++i],  alarm[++i],  alarm[++i],  alarm[++i]);
            AlarmManager m = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, NotificationAlarm.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 666, intent, 0);
            m.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }
    }
**/
}





