package at.fhooe.mc.android;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDue extends Activity implements View.OnClickListener {
    private static final String TAG = "at.fhooe.mc.toDoList :: TaskDue";
    public String mKey = null;
    private int mNotiCountRep;
    private int mNotiCountDead;

    String mTitle;
    String mDes;
    String mDate;
    String mLabel1;
    String mLabel2;
    String mLabel3;
    String mTime;

    boolean mBrutal;
    boolean mSnarky;
    boolean mFunny;
    boolean mCute;
    boolean mNormal;
    boolean mNoNoti;
    private String mCircle;
    private int mRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Repository.mEnable = false;
        Intent i = getIntent();
        int task = i.getIntExtra("task",-1);
        if(task ==1) {
            setContentView(R.layout.activity_repeat_task_due);
        }else {
            setContentView(R.layout.activity_deadline_task_due);
        }


        if( i.getBooleanExtra("fromNotification",false)) {
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(i.getIntExtra("NotificationID", 0));
            NotificationAlarm.mGroupCount--;
        }
        if(NotificationAlarm.mGroupCount == 0){
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }

        mTitle = i.getStringExtra("title");
        mDes = i.getStringExtra("des");
        mLabel1 = i.getStringExtra("label1");
        mLabel2 = i.getStringExtra("label2");
        mLabel3 = i.getStringExtra("label3");

        mBrutal = i.getBooleanExtra("brutal", false);
        mSnarky = i.getBooleanExtra("snarky", false);
        mFunny = i.getBooleanExtra("funny", false);
        mCute = i.getBooleanExtra("cute", false);
        mNormal = i.getBooleanExtra("normal", false);
        mNoNoti = i.getBooleanExtra("noNoti", true);
        mKey = i.getStringExtra("ref");

        if(task==0){
            mTime =  i.getStringExtra("time");
            mDate = i.getStringExtra("date");

            TextView tv = findViewById(R.id.activity_task_due_deadline_title);
            tv.setText(mTitle);

            tv = findViewById(R.id.activity_task_due_deadline_date);
            tv.setText(mDate);

            tv = findViewById(R.id.activity_task_due_deadline_time);
            tv.setText(mTime);

            tv = findViewById(R.id.activity_task_due_deadline_des);
            tv.setText(mDes);

            ImageView b = findViewById(R.id.activity_task_due_deadline_remove);
            b.setOnClickListener(this);

            ImageView x = findViewById(R.id.activity_task_due_deadline_change);
            x.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_deadline_back);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_deadline_noNotifi_Button);
            b.setOnClickListener(this);

            Drawable d = getDrawable(R.drawable.ic_button_round_green);
            assert d != null;

            if(!mBrutal){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxBrutal);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mSnarky){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxSnarky);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mFunny){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxFunny);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mCute){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxCute);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mNormal){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNormal);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(mNoNoti){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNot);
                d.setAlpha(50);
                y.setBackground(d);
                mNotiCountDead++;
            }
            if(mLabel1.equals("")){
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                ImageView iconl1 = findViewById(R.id.activity_task_due_deadline_icon_label1);
                l1.setVisibility(View.INVISIBLE);
                iconl1.setVisibility(View.INVISIBLE);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                ImageView iconl2 = findViewById(R.id.activity_task_due_deadline_icon_label2);
                l2.setVisibility(View.INVISIBLE);
                iconl2.setVisibility(View.INVISIBLE);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_deadline_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);

            }else if(mLabel2 == null){
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(mLabel1);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                ImageView iconl2 = findViewById(R.id.activity_task_due_deadline_icon_label2);
                l2.setVisibility(View.INVISIBLE);
                iconl2.setVisibility(View.INVISIBLE);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_deadline_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else if(mLabel3 == null){
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(mLabel1);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                l2.setText(mLabel2);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_deadline_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else{
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(mLabel1);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                l2.setText(mLabel2);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                l3.setText(mLabel3);
            }

        }else{
            mRepeat = i.getIntExtra("repeat",1);
            mCircle = i.getStringExtra("circle");

            TextView tv = findViewById(R.id.activity_task_due_repeat_title);
            tv.setText(mTitle);

            tv = findViewById(R.id.activity_task_due_repeat_repeat);
            tv.setText(String.valueOf(mRepeat));

            tv = findViewById(R.id.activity_task_due_repeat_circle);
            tv.setText(mCircle);

            tv = findViewById(R.id.activity_task_due_repeat_des);
            tv.setText(mDes);

            ImageView x = findViewById(R.id.activity_task_due_repeat_change);
            x.setOnClickListener(this);

            ImageView b = findViewById(R.id.activity_task_due_repeat_remove);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_repeat_back);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_repeat_noNotifi_Button);
            b.setOnClickListener(this);


            Drawable d = getDrawable(R.drawable.ic_button_round_blue);
            assert d != null;

            if(!mBrutal){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxBrutal);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mSnarky){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxSnarky);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mFunny){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxFunny);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mCute){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxCute);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!mNormal){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNormal);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(mNoNoti){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNo);
                d.setAlpha(50);
                y.setBackground(d);
                mNotiCountRep++;
            }
            if(mLabel1.equals("")){
                TextView l1 = findViewById(R.id.activity_task_due_repeat_label1);
                ImageView iconl1 = findViewById(R.id.activity_task_due_repeat_icon_label1);
                l1.setVisibility(View.INVISIBLE);
                iconl1.setVisibility(View.INVISIBLE);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                ImageView iconl2 = findViewById(R.id.activity_task_due_repeat_icon_label2);
                l2.setVisibility(View.INVISIBLE);
                iconl2.setVisibility(View.INVISIBLE);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_repeat_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);

            }else if(mLabel2 == null){
                TextView l1 = findViewById(R.id.activity_task_due_repeat_label1);
                l1.setText(mLabel1);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                ImageView iconl2 = findViewById(R.id.activity_task_due_repeat_icon_label2);
                l2.setVisibility(View.INVISIBLE);
                iconl2.setVisibility(View.INVISIBLE);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_repeat_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else if(mLabel3 == null){
                TextView l1 = findViewById(R.id.activity_task_due_repeat_label1);
                l1.setText(mLabel1);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                l2.setText(mLabel2);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_repeat_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else{
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(mLabel1);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                l2.setText(mLabel2);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                l3.setText(mLabel3);
            }

        }
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.activity_task_due_deadline_change:{

                    Log.i(TAG, "onClick :: Deadline change was clicked");
                    Intent i = new Intent(this, ActivityDeadlineTask.class);
                    i.putExtra("title", mTitle);
                    i.putExtra("des", mDes);
                    i.putExtra("date", mDate);
                    i.putExtra("time", mTime);
                    i.putExtra("label1", mLabel1);
                    i.putExtra("label2", mLabel2);
                    i.putExtra("label3", mLabel3);
                    i.putExtra("brutal", mBrutal);
                    i.putExtra("snarky", mSnarky);
                    i.putExtra("funny", mFunny);
                    i.putExtra("cute", mCute);
                    i.putExtra("normal", mNormal);
                    i.putExtra("noNoti", mNoNoti);
                    i.putExtra("ref", mKey);
                    i.putExtra("fromTaskDue", true);
                    startActivity(i);
                    finish();

            }break;
            case R.id.activity_task_due_repeat_change:{
                Log.i(TAG,"conClick :: Repeat change was clicked");
                Intent i = new Intent(this, ActivityRepeatTask.class);
                i.putExtra("title", mTitle);
                i.putExtra("des", mDes);
                i.putExtra("repeat",mRepeat);
                i.putExtra("circle",mCircle);
                i.putExtra("label1", mLabel1);
                i.putExtra("label2", mLabel2);
                i.putExtra("label3", mLabel3);
                i.putExtra("brutal", mBrutal);
                i.putExtra("snarky", mSnarky);
                i.putExtra("funny", mFunny);
                i.putExtra("cute", mCute);
                i.putExtra("normal", mNormal);
                i.putExtra("noNoti", mNoNoti);
                i.putExtra("ref", mKey);
                i.putExtra("fromTaskDue", true);
                startActivity(i);
                finish();
            }break;
            case R.id.activity_task_due_deadline_remove: {
                Log.i(TAG,"conClick :: Deadline remove was clicked");
                Repository.getInstance().removeDate(mKey);
                Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_Notification_Remove_toast, Toast.LENGTH_SHORT).show();

                finish();
            }break;
            case R.id.activity_task_due_repeat_remove: {
                Log.i(TAG,"conClick :: Repeat remove was clicked");
                Repository.getInstance().removeDate(mKey);
                Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_Notification_Remove_toast, Toast.LENGTH_SHORT).show();

                finish();
            }break;
            case R.id.activity_task_due_deadline_back: {
                Log.i(TAG,"conClick :: Deadline back was clicked");
                finish();
            }break;
            case R.id.activity_task_due_repeat_back: {
                Log.i(TAG,"conClick :: Repeat back was clicked");
                finish();
            }break;
            case R.id.activity_task_due_deadline_noNotifi_Button: {
                Log.i(TAG,"conClick :: Deadline noNoti was clicked");
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNot);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                assert d != null;

                if(mNotiCountDead % 2 != 0) {
                    d.setAlpha(255);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(false, mKey);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_noNotification_Button, Toast.LENGTH_SHORT).show();
                }else{
                    d.setAlpha(50);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(true, mKey);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_Notification_Button, Toast.LENGTH_SHORT).show();
                }
                mNotiCountDead++;
            }break;
            case R.id.activity_task_due_repeat_noNotifi_Button: {
                Log.i(TAG,"conClick :: Repeat noNoti was clicked");
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNo);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                if(mNotiCountRep % 2 != 0) {
                    assert d != null;
                    d.setAlpha(255);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(false, mKey);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_noNotification_Button, Toast.LENGTH_SHORT).show();
                }else{
                    assert d != null;
                    d.setAlpha(50);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(true, mKey);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_Notification_Button, Toast.LENGTH_SHORT).show();
                }
                mNotiCountRep++;
            }break;
            default:
                Log.e(TAG, "::onClick unexpected ID encountered");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Repository.mEnable = true;
    }
}
