package at.fhooe.mc.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.pm.ActivityInfo;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.RadioButton;
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
    private boolean mCheckTime;
    private int mClickedHelp;
    private int mBrutalClicked;
    private int mCuteClicked;
    private int mFunnyClicked;
    private int mNormalClicked;
    private int mSnarkyClicked;



    protected DeadlineTask mDeadlineTask;
    int mLabelCount = 0;
    GridView mLabelView;
    List<String> mLabelList;
    ArrayAdapter<String> mLabelAdapter;
    private int mNoClicked;

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

        ImageView helpButton = findViewById(R.id.DeadlineTask_Activity_help_button);
        helpButton.setOnClickListener(this);

        ImageView helpDialog = findViewById(R.id.DeadlineTask_Activity_help_dialog);
        helpDialog.setVisibility(View.INVISIBLE);

        ImageView ok = findViewById(R.id.DeadlineTask_Activity_Check_Button);
        ok.setOnClickListener(this);

        RadioButton brutal = findViewById(R.id.DeadlineTask_Activity_MotiBrutal_RadioButton);
        brutal.setOnClickListener(this);

        RadioButton cute = findViewById(R.id.DeadlineTask_Activity_MotiCute_RadioButton);
        cute.setOnClickListener(this);

        RadioButton snarky = findViewById(R.id.DeadlineTask_Activity_MotiSnarky_RadioButton);
        snarky.setOnClickListener(this);

        RadioButton funny = findViewById(R.id.DeadlineTask_Activity_MotiFunny_RadioButton);
        funny.setOnClickListener(this);

        RadioButton normal = findViewById(R.id.DeadlineTask_Activity_MotiNormal_RadioButton);
        normal.setOnClickListener(this);

        RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiNo_RadioButton);
        b.setOnClickListener(this);

        mLabelView = findViewById(R.id.task_Activity_Label_layout);
        mLabelList = new ArrayList<>();
        mLabelAdapter = new ArrayAdapter<>(ActivityDeadlineTask.this,android.R.layout.simple_expandable_list_item_1,mLabelList);
        mLabelView.setAdapter(mLabelAdapter);

        ImageView label = findViewById(R.id.DeadlineTask_Activity_Label_Button);
        label.setOnClickListener(this);

        mLabelView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {

        mLabelAdapter.remove(mLabelList.get(_position));
        Toast.makeText(ActivityDeadlineTask.this, R.string.task_Activity_LabelDelete_Toast, Toast.LENGTH_SHORT).show();
        mLabelCount--;
            }
        });

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
                    if(getLabel.equals("")){
                        mLabelCount--;
                        return;
                    }
                    mLabelList.add(mLabelList.size(), getLabel);
                    mLabelAdapter.notifyDataSetChanged();
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

                if(title.equals("")){
                    title = "Task";
                }

                mDeadlineTask.setTitle(title);
                mDeadlineTask.setDescription(description);
                Calendar calendar = Calendar.getInstance();
                if(mDay == 0){
                    mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    mMonth = calendar.get(Calendar.MONTH);
                    mYear = calendar.get(Calendar.YEAR);
                }

                if(!mCheckTime){
                    mMinute = calendar.get(Calendar.MINUTE);
                    mHour = calendar.get(Calendar.HOUR_OF_DAY);
                }

                mDeadlineTask.setDay(mDay);
                mDeadlineTask.setMonth(mMonth);
                mDeadlineTask.setYear(mYear);

                mDeadlineTask.setHour(mHour);
                mDeadlineTask.setMinute(mMinute);
                mDeadlineTask.setTask(0);

                 if(mLabelList.size() == 0){
                     mLabelList.add("");
                 }
                mDeadlineTask.setLabel(mLabelList);

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
                            mCheckTime = true;
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

            case R.id.DeadlineTask_Activity_MotiBrutal_RadioButton:{
                Log.i(TAG, "::onClick MotivationBrutal RadioButton was clicked");
                if(mBrutalClicked % 2 == 0) {
                    mDeadlineTask.setBrutal(true);
                }else{
                    mDeadlineTask.setBrutal(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiBrutal_RadioButton);
                    b.setChecked(false);
                }mBrutalClicked++;

            }break;
            case R.id.DeadlineTask_Activity_MotiCute_RadioButton:{
                Log.i(TAG, "::onClick MotivationCute RadioButton was clicked");
                if(mCuteClicked % 2 == 0) {
                    mDeadlineTask.setCute(true);
                }else{
                    mDeadlineTask.setCute(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiCute_RadioButton);
                    b.setChecked(false);
                }mCuteClicked++;

            }break;
            case R.id.DeadlineTask_Activity_MotiFunny_RadioButton:{
                Log.i(TAG, "::onClick MotivationFunny RadioButton was clicked");
                if(mFunnyClicked % 2 == 0) {
                    mDeadlineTask.setFunny(true);
                }else{
                    mDeadlineTask.setFunny(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiFunny_RadioButton);
                    b.setChecked(false);
                }mFunnyClicked++;
            }break;
            case R.id.DeadlineTask_Activity_MotiNormal_RadioButton:{
                Log.i(TAG, "::onClick MotivationNormal RadioButton was clicked");
                if(mNormalClicked % 2 == 0) {
                    mDeadlineTask.setNormal(true);
                }else{
                    mDeadlineTask.setNormal(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiNormal_RadioButton);
                    b.setChecked(false);
                }mNormalClicked++;
            }break;
            case R.id.DeadlineTask_Activity_MotiSnarky_RadioButton:{
                Log.i(TAG, "::onClick MotivationSnarky RadioButton was clicked");
                if(mSnarkyClicked % 2 == 0) {
                    mDeadlineTask.setSnarky(true);
                }else{
                    mDeadlineTask.setSnarky(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiSnarky_RadioButton);
                    b.setChecked(false);
                }mSnarkyClicked++;
            }break;
            case R.id.DeadlineTask_Activity_MotiNo_RadioButton:{
                Log.i(TAG, "::onClick MotivationNormal RadioButton was clicked");
                if(mNoClicked % 2 == 0) {
                    mDeadlineTask.setNotification(false);
                }else{
                    mDeadlineTask.setNotification(true);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiNo_RadioButton);
                    b.setChecked(false);
                }mNoClicked++;
            }break;
            case R.id.DeadlineTask_Activity_help_button:{
                ImageView helpDialog = findViewById(R.id.DeadlineTask_Activity_help_dialog);
                if(mClickedHelp % 2 == 0) {
                    helpDialog.setVisibility(View.VISIBLE);
                } else{
                    helpDialog.setVisibility(View.INVISIBLE);
                }
                mClickedHelp++;
            }break;
            default:
                Log.e(TAG, "::onClick unexpected ID encountered");
        }

    }

}
