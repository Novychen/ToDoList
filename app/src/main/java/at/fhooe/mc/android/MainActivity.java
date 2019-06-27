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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    String mPass;
    int mClickedHelp;
    String mMail;
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

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mEmail = findViewById(R.id.main_Activity_Auth_LogIn_Email);
        mPassword = findViewById(R.id.main_Activity_Auth_LogIn_Password);

        Button b;
        b = findViewById(R.id.main_Activity_LogIn_Button);
        b.setOnClickListener(this);

        b = findViewById(R.id.main_Activity_help_dialog);
        b.setVisibility(View.INVISIBLE);

        ImageView v = findViewById(R.id.main_Activity_help_button);
        v.setOnClickListener(this);

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

    private boolean check(){

        mMail = mEmail.getText().toString();
        mPass = mPassword.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(mMail.isEmpty() && mPass.isEmpty()){
            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_EmailPassEmpty_Toast), Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPass.length() < 7 && !mMail.trim().matches(emailPattern)){
            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_EmailPasswordInvalid_Toast), Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPass.length() < 7){
            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_PasswordInvalid_Toast), Toast.LENGTH_SHORT).show();
            return false;
        }else if(!mMail.trim().matches(emailPattern)){
            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_EmailInvalid_Toast), Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPass.isEmpty()) {
            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_PasswordEmpty_Toast), Toast.LENGTH_SHORT).show();
            return false;
        }else if(mMail.isEmpty()){
            Toast.makeText(MainActivity.this, getText(R.string.main_Activity_EmailEmpty_Toast), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {

            case R.id.main_Activity_help_button:{
                Button b = findViewById(R.id.main_Activity_help_dialog);
                if(mClickedHelp % 2 == 0) {
                    b.setVisibility(View.VISIBLE);
                } else{
                    b.setVisibility(View.INVISIBLE);
                }
                mClickedHelp++;

            }break;

            case R.id.main_Activity_LogIn_Button: {
                if(check()) {
                    signIn();
                }
            }break;
            case R.id.main_Activity_SignIn_Button: {
                if(check()) {
                    createAccount();
                }
            }break;
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
    public void setNotificationDeadlineData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<String> t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal) {

    }

    @Override
    public void setNotificationRepeatData(List<Integer> r, List<String> c, List<String> t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal) {

    }

    @Override
    public void setTitle(List<String> _repeatT, List<String> _deadT, List<Integer> _task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _repeats, List<String> _repeatCircle) {

    }


    @Override
    public void setAll(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<Integer> task, List<String> _des,List<String> ref, List<List<String>> label) {
    }
}





