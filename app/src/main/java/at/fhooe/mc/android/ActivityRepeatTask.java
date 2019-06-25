package at.fhooe.mc.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityRepeatTask extends Activity implements Task, View.OnClickListener {

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityRepeatTask";
    private RepeatTask mRepeatTask;
    private NumberPicker mPicker;
    private NumberPicker mTimesPicker;
    private Dialog d;
    private String[] s;
    int mLabelCount = 0;
    List<String> mLabelList;
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_repeat_task);

        mRepeatTask = new RepeatTask();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView repeat = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
        repeat.setOnClickListener(this);

        TextView repeatCircles = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
        repeatCircles.setOnClickListener(this);

        ImageView ok = findViewById(R.id.RepeatTask_Activity_Check_Button);
        ok.setOnClickListener(this);

        GridView tableRow = findViewById(R.id.task_Activity_Label_layout);
        mLabelList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter<>(ActivityRepeatTask.this, android.R.layout.simple_expandable_list_item_1, mLabelList);
        tableRow.setAdapter(mArrayAdapter);

        ImageView label = findViewById(R.id.RepeatTask_Activity_Label_Button);
        label.setOnClickListener(this);
    }



    public void showRepeatCircleDialog()
    {
        d = new Dialog(ActivityRepeatTask.this);
        d.setTitle("Repeat per");
        d.setContentView(R.layout.activity_repeat_picker);
        Button set = d.findViewById(R.id.RepeatTask_Activity_RepeatDialog_ok);
        set.setOnClickListener(this);
        Button cancel = d.findViewById(R.id.RepeatTask_Activity_RepeatDialog_cancel);
        cancel.setOnClickListener(this);
        mPicker = d.findViewById(R.id.RepeatTask_Activity_Picker);
        s = new String[] {"year","month","week"};
        mPicker.setDisplayedValues(s);
        mPicker.setMaxValue(s.length-1);
        mPicker.setMinValue(0);
        mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        d.show();
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {

            case R.id.RepeatTask_Activity_HowOftRepeat:{
                d = new Dialog(ActivityRepeatTask.this);
                d.setTitle("Repeat");
                d.setContentView(R.layout.activity_repeat_picker);
                Button set = d.findViewById(R.id.RepeatTask_Activity_RepeatDialog_ok);
                set.setOnClickListener(this);
                Button cancel = d.findViewById(R.id.RepeatTask_Activity_RepeatDialog_cancel);
                cancel.setOnClickListener(this);
                mTimesPicker = d.findViewById(R.id.RepeatTask_Activity_Picker);
                mTimesPicker.setValue(0);
                mTimesPicker.setMaxValue(20);
                mTimesPicker.setMinValue(0);
                mTimesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                d.show();

            }break;
            case R.id.RepeatTask_Activity_RepeatDialog_ok:{
                TextView circle = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
                TextView times = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
                if(mPicker != null){
                    int choice = mPicker.getValue();
                    circle.setText(s[choice]);
                    mRepeatTask.setRepeatRotation(s[choice]);
                }
                if(mTimesPicker != null){
                    times.setText(String.valueOf(mTimesPicker.getValue()));
                    mRepeatTask.setRepeats(mTimesPicker.getValue());
                }
                d.dismiss();

            }break;

            case R.id.RepeatTask_Activity_RepeatDialog_cancel:{
                d.dismiss();
            }

            case R.id.RepeatTask_Activity_Check_Button: {

                Log.i(TAG, "::onClick check Button was pressed");
                EditText d = findViewById(R.id.DeadlineTask_Activity_description_field);
                EditText t = findViewById(R.id.RepeatTask_Activity_title_field);

                String description = d.getText().toString();
                String title = t.getText().toString();

                mRepeatTask.setTitle(title);
                mRepeatTask.setDescription(description);
                mRepeatTask.setTask(1);

                long taskNumber = MainActivity.getTaskNumber() +1;

                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(mRepeatTask);
                Repository.getInstance().saveData(taskNumber);
                finish();
            }
            break;
            case R.id.RepeatTask_Activity_Label_Button: {
                mLabelCount++;
                if (mLabelCount <= 3) {
                    EditText txt = findViewById(R.id.RepeatTask_Activity_setLabel_field);
                    String getLabel = txt.getText().toString();
                    mLabelList.add(mLabelList.size(), getLabel);
                    mArrayAdapter.notifyDataSetChanged();
                    mRepeatTask.setLabel(mLabelList);
                    Toast.makeText(ActivityRepeatTask.this, R.string.task_Activity_LabelSuccess_Toast, Toast.LENGTH_SHORT).show();
                    txt.setText("");
                } else {
                    Toast.makeText(ActivityRepeatTask.this, R.string.task_Activity_LabelFail_Toast, Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.RepeatTask_Activity_RepeatCircles:{
                showRepeatCircleDialog();
            }
            default:
                Log.e(TAG, "task_Activity::onClick unexpected ID encountered");

        }
    }
}
