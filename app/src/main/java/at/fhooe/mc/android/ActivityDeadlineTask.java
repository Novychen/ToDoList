package at.fhooe.mc.android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

/**
 *
 * Fehler:  Datenbank zählt falsch wenn User1 log out to User2 log in
 *          LogIn fail wenn nicht alle Felder ausgefüllt
 *          LogIn keine Unterscheidung was falsch ist (Passwort/Email)
 *
 */

public class ActivityDeadlineTask extends Activity implements View.OnClickListener, Task {

    private final static String TAG = "at.fhooe.mc.toDoList";
    protected DeadlineTask mDeadlineTask;
    String time;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeadlineTask = new DeadlineTask();

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_task);

        TextView timeFiled = findViewById(R.id.task_Activity_time_field);
        timeFiled.setOnClickListener(this);

        TextView dateFiled = findViewById(R.id.task_Activity_date_field);
        dateFiled.setOnClickListener(this);

        TextView title = findViewById(R.id.task_Activity_Check_Button);
        title.setOnClickListener(this);

        time  = " null : null true";
    }

    @Override
    public void onClick(View v) {
        int year;
        int month;
        int day;
        switch (v.getId()) {

            case R.id.task_Activity_Check_Button: {
                Log.i(TAG, "task_Activity::onClick title was selected");
                EditText  mTitleText = findViewById(R.id.task_Activity_title_field);
                EditText mDescription = findViewById(R.id.task_Activity_description_field);

                String title = mTitleText.getText().toString();
                String description = mDescription.getText().toString();

                mDeadlineTask.setTitle(title);
                mDeadlineTask.setDescription(description);
                mDeadlineTask.setDate(time,date);

                long taskNumber = MainActivity.getTaskNumber();
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
                            dateField.setText("0" + String.valueOf(hourOfDay) + ":" + "0" + minute);
                        } else if (hourOfDay >= 10 && minute < 10) {
                            dateField.setText(hourOfDay + ":"  + "0" + minute);
                        } else if (hourOfDay < 10 && minute >= 10) {
                            dateField.setText("0" + String.valueOf(hourOfDay) +":" + minute);
                        } else {
                            dateField.setText(hourOfDay + ":" + minute);
                        }

                        time  = hourOfDay + ":" + minute + " false";
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
                        dateField.setText(dayOfMonth + "." + month + "." + year);

                        date = dayOfMonth + "." + month + "." + year + " ";
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
