package at.fhooe.mc.android;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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

    private final static String TAG = "at.fhooe.mc.toDoList";

    protected Calendar mCalendar;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHour;
    private int mMinute;

    protected DeadlineTask mDeadlineTask;
    String mTime;
    String mDate;
    int mLabelCount = 0;
    List<String> mLabelList;
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_deadline_task);
        mDeadlineTask = new DeadlineTask();

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView timeFiled = findViewById(R.id.task_Activity_time_field);
        timeFiled.setOnClickListener(this);

        TextView dateFiled = findViewById(R.id.task_Activity_date_field);
        dateFiled.setOnClickListener(this);

        ImageView ok = findViewById(R.id.task_Activity_Check_Button);
        ok.setOnClickListener(this);

        GridView tableRow = findViewById(R.id.task_Activity_Label_layout);
        mLabelList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter<>(ActivityDeadlineTask.this,android.R.layout.simple_expandable_list_item_1,mLabelList);
        tableRow.setAdapter(mArrayAdapter);

        ImageView label = findViewById(R.id.task_Activity_Label_Button);
        label.setOnClickListener(this);

        mTime  = " null : null true";
    }

    @Override
    public void onClick(View v) {
        int year;
        int month;
        final int day;
        switch (v.getId()) {

            case R.id.task_Activity_Label_Button: {
                mLabelCount++;
                if (mLabelCount <= 3) {
                    EditText txt = findViewById(R.id.task_Activity_setLabel_field);
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

            case R.id.task_Activity_Check_Button: {
                Log.i(TAG, "task_Activity::onClick title was selected");
                EditText mTitleText = findViewById(R.id.task_Activity_title_field);
                EditText mDescription = findViewById(R.id.task_Activity_description_field);

                String title = mTitleText.getText().toString();
                String description = mDescription.getText().toString();

                mCalendar = Calendar.getInstance();
                mCalendar.set(mYear, mMonth, mDay, mHour, mMinute);

                if (mTime.contains("false")) {
                    mCalendar.set(mYear, (mMonth - 1), mDay, mHour, mMinute);
                } else {
                    mCalendar.set(mYear, mMonth, mDay);
                }
                mDeadlineTask.setTitle(title);
                mDeadlineTask.setDescription(description);
                mDeadlineTask.setDate(mTime, mDate);

                AlarmManager m = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent(this, NotificationAlarm.class);
                PendingIntent pi = PendingIntent.getBroadcast(this, 666, i, 0);

                m.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);

                long taskNumber = MainActivity.getTaskNumber() + 1;
                MainActivity.setTaskNumber(taskNumber);

                Log.i(TAG, "taskNumber Value is: " + taskNumber);
                Repository.getInstance().saveData(mDeadlineTask, taskNumber);
                Repository.getInstance().saveData(taskNumber);
                finish();
            }
            break;
            case R.id.task_Activity_time_field: {
                Log.i(TAG, "task_Activity::onClick SelectTime Button was pressed");
                final Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog selectTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        TextView dateField = findViewById(R.id.task_Activity_time_field);
                        if (hourOfDay < 10 && minute < 10) {
                            dateField.setText("0" + hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            dateField.setText(hourOfDay + ":" + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            dateField.setText("0" + String.valueOf(hourOfDay) + ":" + minute);
                        } else {
                            dateField.setText(hourOfDay + ":" + minute);
                        }

                        mTime = " " + hourOfDay + ":" + minute + " false";
                        mMinute = minute;
                        mHour = hourOfDay;

                    }
                }, hourOfDay, minute, true);

                selectTime.show();

            }
            break;
            case R.id.task_Activity_date_field: {
                Log.i(TAG, "task_Activity::onClick SelectDate Button was pressed");
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog selectDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        TextView dateField = findViewById(R.id.task_Activity_date_field);
                        month = month + 1;
                        dateField.setText(dayOfMonth + "." + month + "." + year + " ");
                        mDate = dayOfMonth + "." + month + "." + year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        mYear = year;
                    }
                }, year, month, day);
                selectDate.show();
            }
            break;

            default:
                Log.e(TAG, "task_Activity::onClick unexpected ID encountered");
        }

    }

}
