package at.fhooe.mc.android;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class TaskDue extends Activity {
    private static final String TAG = "at.fhooe.mc.toDoList :: TaskDue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_due);
        Intent i = getIntent();

        String title = i.getStringExtra("title");
        int day = i.getIntExtra("day",0);
        int month = i.getIntExtra("month",0);
        int year = i.getIntExtra("year",0);
        int hour = i.getIntExtra("hour",0);
        int min = i.getIntExtra("min",0);
        String des = i.getStringExtra("des");
        String ref = i.getStringExtra("ref");

        StringBuilder s = new StringBuilder();
        s.append(day + "." + month +"." + year  + ",   " + hour +":"+ min);

        TextView tv = null;
        tv = (TextView) findViewById(R.id.activity_task_due_title);
        tv.setText(title);

        tv = (TextView) findViewById(R.id.activity_task_due_date);
        tv.setText(s.toString());


        tv = (TextView) findViewById(R.id.activity_task_due_Note);
        tv.setText(des);




        Log.i(TAG, "data ---->" + title +","+ day +","+ month +","+ year +","
                + hour+","+ min +"," + des);
        Log.i(TAG, "Reference ---->"+ref);


    }


}
