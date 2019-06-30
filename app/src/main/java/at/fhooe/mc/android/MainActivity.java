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
    public void setNotificationDeadlineData(List <Integer>_task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _h, List<Integer> _min, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal, List<Boolean> _notification, List<Integer> _count, List<String> _ref, List<String> _des, List<List<String>> _label) {

    }

    @Override
    public void setNotificationRepeatData(List<Integer> _task, List<Integer> _r, List<String> _c, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal, List<Boolean> _notification, List<String> _des, List<List<String>> _label, List<String> _ref, List<Integer> _count) {

    }

    @Override
    public void setTitle(List<String> _repeatT, List<String> _deadT, List<Integer> _task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _repeats, List<String> _repeatCircle) {

    }

    @Override
    public void setAll(List<String> _repeatT, List<String> _deadT, List<Integer> _d, List<Integer> _mo, List<Integer> _y, List<Integer> _h, List<Integer> _mi, List<Integer> _t, List<List<String>> _labelRep, List<List<String>> _labelDead, List<String> _desrep, List<String> _desdead, List<String> _refrep, List<String> _refdead, List<Integer> _repeats, List<String> _repeatCircle, List<Boolean> _normRep, List<Boolean> _funnyRep, List<Boolean> _snarkyRep, List<Boolean> _cuteRep, List<Boolean> _brutalRep, List<Boolean> _notificationRep, List<Boolean> _normDead, List<Boolean> _funnyDead, List<Boolean> _snarkyDead, List<Boolean> _cuteDead, List<Boolean> _brutalDead, List<Boolean> _notificationDead) {

    }
}





