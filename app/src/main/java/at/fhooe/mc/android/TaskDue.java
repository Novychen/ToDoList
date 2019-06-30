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
    public String key = null;
    private int mNotiCountRep;
    private int mNotiCountDead;


    String title;
    String des;
    String date;
    String label1;
    String label2;
    String label3;
    String time;

    boolean brutal;
    boolean snarky;
    boolean funny;
    boolean cute;
    boolean normal;
    boolean noNoti;
    private String circle;
    private int repeat;

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

        title = i.getStringExtra("title");
        des = i.getStringExtra("des");
        label1 = i.getStringExtra("label1");
        label2 = i.getStringExtra("label2");
        label3 = i.getStringExtra("label3");

        brutal = i.getBooleanExtra("brutal", false);
        snarky = i.getBooleanExtra("snarky", false);
        funny = i.getBooleanExtra("funny", false);
        cute = i.getBooleanExtra("cute", false);
        normal = i.getBooleanExtra("normal", false);
        noNoti = i.getBooleanExtra("noNoti", true);
        key = i.getStringExtra("ref");

        if(task==0){
            time =  i.getStringExtra("time");
            date = i.getStringExtra("date");

            TextView tv = findViewById(R.id.activity_task_due_deadline_title);
            tv.setText(title);

            tv = findViewById(R.id.activity_task_due_deadline_date);
            tv.setText(date);

            tv = findViewById(R.id.activity_task_due_deadline_time);
            tv.setText(time);

            tv = findViewById(R.id.activity_task_due_deadline_des);
            tv.setText(des);

            ImageView b = findViewById(R.id.activity_task_due_deadline_remove);
            b.setOnClickListener(this);

            ImageView x = findViewById(R.id.activity_task_due_deadline_change);
            x.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_deadline_back);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_deadline_noNotifi_Button);
            b.setOnClickListener(this);

            if(!brutal){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxBrutal);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!snarky){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxSnarky);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!funny){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxFunny);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!cute){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxCute);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!normal){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNormal);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(noNoti){
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNot);
                Drawable d = getDrawable(R.drawable.ic_button_round_green);
                d.setAlpha(50);
                y.setBackground(d);
                mNotiCountDead++;
            }
            if(label1.equals("")){
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

            }else if(label2 == null){
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(label1);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                ImageView iconl2 = findViewById(R.id.activity_task_due_deadline_icon_label2);
                l2.setVisibility(View.INVISIBLE);
                iconl2.setVisibility(View.INVISIBLE);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_deadline_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else if(label3 == null){
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(label1);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                l2.setText(label2);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_deadline_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else{
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(label1);
                TextView l2 = findViewById(R.id.activity_task_due_deadline_label2);
                l2.setText(label2);
                TextView l3 = findViewById(R.id.activity_task_due_deadline_label3);
                l3.setText(label3);
            }

        }else{
            repeat = i.getIntExtra("repeat",1);
            circle = i.getStringExtra("circle");

            TextView tv = findViewById(R.id.activity_task_due_repeat_title);
            tv.setText(title);

            tv = findViewById(R.id.activity_task_due_repeat_repeat);
            tv.setText(String.valueOf(repeat));

            tv = findViewById(R.id.activity_task_due_repeat_circle);
            tv.setText(circle);

            tv = findViewById(R.id.activity_task_due_repeat_des);
            tv.setText(des);

            ImageView x = findViewById(R.id.activity_task_due_repeat_change);
            x.setOnClickListener(this);

            ImageView b = findViewById(R.id.activity_task_due_repeat_remove);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_repeat_back);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_repeat_noNotifi_Button);
            b.setOnClickListener(this);


            if(!brutal){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxBrutal);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!snarky){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxSnarky);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!funny){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxFunny);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!cute){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxCute);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(!normal){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNormal);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                d.setAlpha(50);
                y.setBackground(d);
            }
            if(noNoti){
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNo);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                d.setAlpha(50);
                y.setBackground(d);
                mNotiCountRep++;
            }
            if(label1.equals("")){
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

            }else if(label2 == null){
                TextView l1 = findViewById(R.id.activity_task_due_repeat_label1);
                l1.setText(label1);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                ImageView iconl2 = findViewById(R.id.activity_task_due_repeat_icon_label2);
                l2.setVisibility(View.INVISIBLE);
                iconl2.setVisibility(View.INVISIBLE);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_repeat_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else if(label3 == null){
                TextView l1 = findViewById(R.id.activity_task_due_repeat_label1);
                l1.setText(label1);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                l2.setText(label2);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                ImageView iconl3 = findViewById(R.id.activity_task_due_repeat_icon_label3);
                l3.setVisibility(View.INVISIBLE);
                iconl3.setVisibility(View.INVISIBLE);
            }else{
                TextView l1 = findViewById(R.id.activity_task_due_deadline_label1);
                l1.setText(label1);
                TextView l2 = findViewById(R.id.activity_task_due_repeat_label2);
                l2.setText(label2);
                TextView l3 = findViewById(R.id.activity_task_due_repeat_label3);
                l3.setText(label3);
            }

        }
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.activity_task_due_deadline_change:{

                    Log.i(TAG, "onClick :: Deadline change was clicked");
                    Intent i = new Intent(this, ActivityDeadlineTask.class);
                    i.putExtra("title", title);
                    i.putExtra("des", des);
                    i.putExtra("date", date);
                    i.putExtra("time", time);
                    i.putExtra("label1", label1);
                    i.putExtra("label2", label2);
                    i.putExtra("label3", label3);
                    i.putExtra("brutal", brutal);
                    i.putExtra("snarky", snarky);
                    i.putExtra("funny", funny);
                    i.putExtra("cute", cute);
                    i.putExtra("normal", normal);
                    i.putExtra("noNoti", noNoti);
                    i.putExtra("ref", key);
                    i.putExtra("fromTaskDue", true);
                    startActivity(i);
                    finish();

            }break;
            case R.id.activity_task_due_repeat_change:{
                Log.i(TAG,"conClick :: Repeat change was clicked");
                Intent i = new Intent(this, ActivityRepeatTask.class);
                i.putExtra("title", title);
                i.putExtra("des", des);
                i.putExtra("repeat",repeat);
                i.putExtra("circle",circle);
                i.putExtra("label1", label1);
                i.putExtra("label2", label2);
                i.putExtra("label3", label3);
                i.putExtra("brutal", brutal);
                i.putExtra("snarky", snarky);
                i.putExtra("funny", funny);
                i.putExtra("cute", cute);
                i.putExtra("normal", normal);
                i.putExtra("noNoti", noNoti);
                i.putExtra("ref", key);
                i.putExtra("fromTaskDue", true);
                startActivity(i);
                finish();
            }break;
            case R.id.activity_task_due_deadline_remove: {
                Log.i(TAG,"conClick :: Deadline remove was clicked");
                Repository.getInstance().removeDate(key);
                Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_Notification_Remove_toast, Toast.LENGTH_SHORT).show();

                finish();
            }break;
            case R.id.activity_task_due_repeat_remove: {
                Log.i(TAG,"conClick :: Repeat remove was clicked");
                Repository.getInstance().removeDate(key);
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
                if(mNotiCountDead % 2 != 0) {
                    d.setAlpha(255);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(false, key);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_noNotification_Button, Toast.LENGTH_SHORT).show();
                }else{
                    d.setAlpha(50);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(true, key);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_Notification_Button, Toast.LENGTH_SHORT).show();
                }
                mNotiCountDead++;
            }break;
            case R.id.activity_task_due_repeat_noNotifi_Button: {
                Log.i(TAG,"conClick :: Repeat noNoti was clicked");
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNo);
                Drawable d = getDrawable(R.drawable.ic_button_round_blue);
                if(mNotiCountRep % 2 != 0) {
                    d.setAlpha(255);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(false, key);
                    Toast.makeText(TaskDue.this, R.string.TaskDue_Activity_noNotification_Button, Toast.LENGTH_SHORT).show();
                }else{
                    d.setAlpha(50);
                    y.setBackground(d);
                    Repository.getInstance().saveNotificationData(true, key);
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
