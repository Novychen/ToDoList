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

        }else{
            TextView tv = findViewById(R.id.activity_task_due_repeat_title);
            tv.setText(title);

            tv = findViewById(R.id.activity_task_due_repeat_date);
            tv.setText(date);

            tv = findViewById(R.id.activity_task_due_repeat_des);
            tv.setText(des);

            ImageView b = findViewById(R.id.activity_task_due_repeat_remove);
            b.setOnClickListener(this);



        }


   /*     if(l0 == null){
            l0 = "    ";
        }
        if(l1 == null){
            l1 = "    ";
        }
        if(l2 == null){
            l2 = "    ";
        }
        String label = l0 + " " + l1 + " " + l2;
        String s = day + "." + month + "." + year  + "       " + hour + ":" + min;*/

/*     TextView tv = findViewById(R.id.activity_task_due_title);
        tv.setText(title);*/
/*
        tv = findViewById(R.id.activity_task_due_date);
        tv.setText(s);

        tv = findViewById(R.id.activity_task_due_Note);
        tv.setText(des);*/
/*
        ImageView b = findViewById(R.id.activity_task_due_remove);
        b.setOnClickListener(this);*/

  /*      Log.i(TAG, "data ---->" + title +","+ day +","+ month +","+ year +","
                + hour+","+ min +"," + des);*/
/*        Log.i(TAG, "Title ---->"+title);*/
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
            }
        }
    }
}
