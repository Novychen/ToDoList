package at.fhooe.mc.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * this class implements the DeadlineTask Activity
 */
public class ActivityDeadlineTask extends Activity implements View.OnClickListener, Task {

    private final static String TAG = "at.fhooe.mc.toDoList :: ActivityDeadlineTask";

    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHour;
    private int mMinute;

    protected DeadlineTask mDeadlineTask;
    int mLabelCount = 0;
    List<String> mLabelList;
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_deadline_task);
        mDeadlineTask = new DeadlineTask();

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView timeFiled = findViewById(R.id.DeadlineTask_Activity_time_field);
        timeFiled.setOnClickListener(this);

        TextView dateFiled = findViewById(R.id.DeadlineTask_Activity_date_field);
        dateFiled.setOnClickListener(this);

        ImageView ok = findViewById(R.id.DeadlineTask_Activity_Check_Button);
        ok.setOnClickListener(this);

        GridView tableRow = findViewById(R.id.task_Activity_Label_layout);
        mLabelList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter<>(ActivityDeadlineTask.this,android.R.layout.simple_expandable_list_item_1,mLabelList);
        tableRow.setAdapter(mArrayAdapter);

        ImageView label = findViewById(R.id.DeadlineTask_Activity_Label_Button);
        label.setOnClickListener(this);

    }

    @Override
    public void onClick(View _v) {
        int year;
        int month;
        final int day;
        switch (_v.getId()) {

            case R.id.DeadlineTask_Activity_Label_Button: {
                mLabelCount++;
                if (mLabelCount <= 3) {
                    EditText txt = findViewById(R.id.DeadlineTask_Activity_setLabel_field);
                    String getLabel = txt.getText().toString();
                    mLabelList.add(mLabelList.size(), getLabel);
                    mArrayAdapter.notifyDataSetChanged();
                    mDeadlineTask.setLabel(mLabelList);
                    Toast.makeText(ActivityDeadlineTask.this, R.string.task_Activity_LabelSuccess_Toast, Toast.LENGTH_SHORT).show();
                    txt.setText("");
                } else {
                    Toast.makeText(ActivityDeadlineTask.this, R.string.task_Activity_LabelFail_Toast, Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.DeadlineTask_Activity_Check_Button: {
                Log.i(TAG, "::onClick check Button was pressed");
                EditText t = findViewById(R.id.DeadlineTask_Activity_title_field);
                EditText d = findViewById(R.id.DeadlineTask_Activity_description_field);

                String title = t.getText().toString();
                String description = d.getText().toString();

                mDeadlineTask.setTitle(title);
                mDeadlineTask.setDescription(description);
                mDeadlineTask.setDay(mDay);
                mDeadlineTask.setMonth(mMonth);
                mDeadlineTask.setYear(mYear);

                mDeadlineTask.setHour(mHour);
                mDeadlineTask.setMinute(mMinute);
                mDeadlineTask.setTask(0);

                long taskNumber = MainActivity.getTaskNumber() +1;

                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(mDeadlineTask);
                Repository.getInstance().saveData(taskNumber);
                finish();
            }
            break;
            case R.id.DeadlineTask_Activity_time_field: {
                Log.i(TAG, "task_Activity::onClick SelectTime Field was clicked");
                final Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog selectTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        TextView dateField = findViewById(R.id.DeadlineTask_Activity_time_field);
                        if (hourOfDay < 10 && minute < 10) {
                            dateField.setText("0" + hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            dateField.setText(hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay < 10) {
                            dateField.setText("0" + hourOfDay + ":" + minute);
                        } else {
                            dateField.setText(hourOfDay + ":" + minute);
                        }
                        if(hourOfDay+minute <= Calendar.getInstance().get(Calendar.HOUR_OF_DAY) +  Calendar.getInstance().get(Calendar.MINUTE)){
                            mMinute = 0;
                            mHour = 0;
                        }else{
                            mMinute = minute;
                            mHour = hourOfDay;
                        }
                    }
                }, hourOfDay, minute, true);

                selectTime.show();

            }
            break;
            case R.id.DeadlineTask_Activity_date_field: {
                Log.i(TAG, "::onClick SelectDate Field was clicked");
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog selectDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TextView dateField = findViewById(R.id.DeadlineTask_Activity_date_field);
                        month = month + 1;
                        dateField.setText(dayOfMonth + "." + month + "." + year + " ");

                        mMonth = month;
                        mDay = dayOfMonth;
                        mYear = year;
                    }
                }, year, month, day);
                selectDate.show();
            }
            break;

            default:
                Log.e(TAG, "::onClick unexpected ID encountered");
        }

    }

}
