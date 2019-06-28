package at.fhooe.mc.android;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskDue extends Activity implements View.OnClickListener {
    private static final String TAG = "at.fhooe.mc.toDoList :: TaskDue";
    public String key = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        String title = i.getStringExtra("title");
        String des = i.getStringExtra("des");
        String date = i.getStringExtra("date");
        String label1 = i.getStringExtra("label1");
        String label2 = i.getStringExtra("label2");
        String label3 = i.getStringExtra("label3");

        boolean brutal = i.getBooleanExtra("brutal", false);
        boolean snarky = i.getBooleanExtra("snarky", false);
        boolean funny = i.getBooleanExtra("funny", false);
        boolean cute = i.getBooleanExtra("cute", false);
        boolean normal = i.getBooleanExtra("normal", false);
        boolean noNoti = i.getBooleanExtra("noNoti", true);
        key = i.getStringExtra("ref");

        if(task==0){
            String time =  i.getStringExtra("time");
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

            b = findViewById(R.id.activity_task_due_deadline_back);
            b.setOnClickListener(this);

            if(!brutal){
                ImageView x = findViewById(R.id.activity_task_due_deadline_brutal);
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxBrutal);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!snarky){
                ImageView x = findViewById(R.id.activity_task_due_deadline_snarky);
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxSnarky);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!funny){
                ImageView x = findViewById(R.id.activity_task_due_deadline_funny);
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxFunny);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!cute){
                ImageView x = findViewById(R.id.activity_task_due_deadline_cute);
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxCute);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!normal){
                ImageView x = findViewById(R.id.activity_task_due_deadline_normal);
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNormal);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(noNoti){
                ImageView x = findViewById(R.id.activity_task_due_deadline_not);
                ImageView y = findViewById(R.id.activity_task_due_deadline_boxNot);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
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
            TextView tv = findViewById(R.id.activity_task_due_repeat_title);
            tv.setText(title);

            tv = findViewById(R.id.activity_task_due_repeat_date);
            tv.setText(date);

            tv = findViewById(R.id.activity_task_due_repeat_des);
            tv.setText(des);

            ImageView b = findViewById(R.id.activity_task_due_repeat_remove);
            b.setOnClickListener(this);

            b = findViewById(R.id.activity_task_due_repeat_back);
            b.setOnClickListener(this);

            if(!brutal){
                ImageView x = findViewById(R.id.activity_task_due_repeat_brutal);
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxBrutal);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!snarky){
                ImageView x = findViewById(R.id.activity_task_due_repeat_snarky);
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxSnarky);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!funny){
                ImageView x = findViewById(R.id.activity_task_due_repeat_funny);
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxFunny);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!cute){
                ImageView x = findViewById(R.id.activity_task_due_repeat_cute);
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxCute);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(!normal){
                ImageView x = findViewById(R.id.activity_task_due_repeat_normal);
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNormal);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
            }
            if(noNoti){
                ImageView x = findViewById(R.id.activity_task_due_repeat_no);
                ImageView y = findViewById(R.id.activity_task_due_repeat_boxNo);
                y.setBackground(getDrawable(R.drawable.ic_button_round_white));
                x.setVisibility(View.INVISIBLE);
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
            case R.id.activity_task_due_deadline_remove: {
                Repository.getInstance().removeDate(key);
               /* long taskNumber = MainActivity.getTaskNumber() -1;
                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(taskNumber);*/
                finish();
            }break;
            case R.id.activity_task_due_repeat_remove: {
                Repository.getInstance().removeDate(key);
               /* long taskNumber = MainActivity.getTaskNumber() -1;
                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(taskNumber);*/
                finish();
            }break;
            case R.id.activity_task_due_deadline_back: {
                finish();
            }break;
            case R.id.activity_task_due_repeat_back: {
                finish();
            }break;
            default:
                Log.e(TAG, "::onClick unexpected ID encountered");
        }
    }
}
