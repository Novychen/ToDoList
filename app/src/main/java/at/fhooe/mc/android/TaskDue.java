package at.fhooe.mc.android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class TaskDue extends Activity implements IFirebaseCallback{
    private static final String TAG = "at.fhooe.mc.toDoList :: TaskDue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_due);


    }



    @Override
    public void setData(Object _o) {

    }

    @Override
    public void setStringData(List<String> s) {

    }

    @Override
    public void setTimeData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<Integer> task) {

    }

    @Override
    public void setTitle(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y) {

    }

    @Override
    public void setAll(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h, List<Integer> min, List<Integer> task) {
        TextView tv = null;
        tv.findViewById(R.id.activity_task_due_title);
    }
}
