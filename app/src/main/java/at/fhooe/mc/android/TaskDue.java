package at.fhooe.mc.android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskDue extends Activity implements  View.OnClickListener{
    private static final String TAG = "at.fhooe.mc.toDoList :: TaskDue";
    public String key = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        int task = i.getIntExtra("Task",0);
        if(task == 0){
            setContentView(R.layout.activity_deadline_task_due);


        }else if (task == 1){
            setContentView(R.layout.activity_repeat_task_due);
        }

        key = i.getStringExtra("ref");

        ImageView b = findViewById(R.id.activity_deadline_task_due_remove);
        b.setOnClickListener(this);

        b = findViewById(R.id.activity_deadline_task_due_close);
        b.setOnClickListener(this);

        Log.i(TAG, "Reference ---->"+key);
    }


    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.activity_deadline_task_due_remove: {
                Repository.getInstance().removeDate(key);
                long taskNumber = MainActivity.getTaskNumber() -1;
                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(taskNumber);
                finish();
            }
            case R.id.activity_deadline_task_due_close: {
                finish();
            }
        }
    }
}
