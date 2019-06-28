package at.fhooe.mc.android;


import android.app.Activity;
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

        String title = i.getStringExtra("title");
        String des = i.getStringExtra("des");
        String date = i.getStringExtra("date");
        boolean brutal = i.getBooleanExtra("brutal", false);
        boolean snarky = i.getBooleanExtra("snarky", false);
        boolean funny = i.getBooleanExtra("funny", false);
        boolean cute = i.getBooleanExtra("cute", false);
        boolean normal = i.getBooleanExtra("normal", false);
        boolean noNoti = i.getBooleanExtra("noNoti", false);
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
                x.setVisibility(View.INVISIBLE);
            }
            if(!snarky){
                ImageView x = findViewById(R.id.activity_task_due_deadline_snarky);
                x.setVisibility(View.INVISIBLE);
            }
            if(!funny){
                ImageView x = findViewById(R.id.activity_task_due_deadline_funny);
                x.setVisibility(View.INVISIBLE);
            }
            if(!cute){
                ImageView x = findViewById(R.id.activity_task_due_deadline_cute);
                x.setVisibility(View.INVISIBLE);
            }
            if(!normal){
                ImageView x = findViewById(R.id.activity_task_due_deadline_normal);
                x.setVisibility(View.INVISIBLE);
            }
            if(!noNoti){
                ImageView x = findViewById(R.id.activity_task_due_deadline_not);
                x.setVisibility(View.INVISIBLE);
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
        }
    }


    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.activity_task_due_deadline_remove: {
                Repository.getInstance().removeDate(key);
                long taskNumber = MainActivity.getTaskNumber() -1;
                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(taskNumber);
                finish();
            }break;
            case R.id.activity_task_due_repeat_remove: {
                Repository.getInstance().removeDate(key);
                long taskNumber = MainActivity.getTaskNumber() -1;
                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(taskNumber);
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
