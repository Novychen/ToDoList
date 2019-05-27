package at.fhooe.mc.android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ActivityDeadlineTask extends Activity implements View.OnClickListener {

    private final static String TAG = "at.fhooe.mc.toDoList";
    protected DeadlineTask mDeadlineTask;
    int i = ActivityList.mTaskNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeadlineTask = new DeadlineTask();

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_task);

        TextView time = findViewById(R.id.task_Activity_time_field);
        time.setOnClickListener(this);

        TextView date = findViewById(R.id.task_Activity_date_field);
        date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int year;
        int month;
        int day;
        switch (v.getId()) {
            case R.id.task_Activity_time_field: {
                Log.i(TAG, "task_Activity::onClick SelectTime Button was pressed");
                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog selectTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        TextView dateField = findViewById(R.id.task_Activity_time_field);
                        if (hourOfDay < 10 && minute < 10) {
                            dateField.setText("0" + String.valueOf(hourOfDay) + ":" + "0" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            dateField.setText(hourOfDay + ":"  + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            dateField.setText("0" + String.valueOf(hourOfDay) +":" + minute);
                        } else {
                            dateField.setText(hourOfDay + ":" + minute);
                        }
                                          }
                }, hourOfDay, minute, true);

                selectTime.show();
                DeadlineTime d = new DeadlineTime();
                d.setHour(hourOfDay);
                d.setMinute(minute);
                d.setwDay(false);
                mDeadlineTask.setDeadlineTime(d);

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
                        dateField.setText(dayOfMonth + "." + month + "." + year);

                    }
                }, year, month, day);
                selectDate.show();
                DeadlineDay d2 = new DeadlineDay();
                d2.setDay(day);
                d2.setMonth(month);
                d2.setYear(year);
                mDeadlineTask.setDeadlineDay(d2);
            }
            break;

            default:
                Log.e(TAG, "task_Activity::onClick unexpected ID encountered");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Repository.getInstance().saveData(mDeadlineTask,i);
    }
}
