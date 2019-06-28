package at.fhooe.mc.android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskDue extends Activity {
    private static final String TAG = "at.fhooe.mc.toDoList :: TaskDue";
    public String key = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_task_due);

        Intent i = getIntent();
   /*     String title = i.getStringExtra("title");*/
 /*      int day = i.getIntExtra("day",0);
        int month = i.getIntExtra("month",0);
        int year = i.getIntExtra("year",0);
        int hour = i.getIntExtra("hour",0);
        int min = i.getIntExtra("min",0);
        String l0 =  i.getStringExtra("label_0");
        String l1 = i.getStringExtra("label_1");
        String l2  = i.getStringExtra("label_2");
        String des = i.getStringExtra("des");*/
/*        key = i.getStringExtra("ref");*/
       /* int task = i.getIntExtra("task",0);*/

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


 /*   @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.activity_task_due_remove: {
                Repository.getInstance().removeDate(key);
                long taskNumber = MainActivity.getTaskNumber() -1;
                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(taskNumber);
                finish();
            }
        }
    }*/
}
