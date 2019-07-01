package at.fhooe.mc.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.content.Intent;
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

    private final static String TAG = "at.fhooe.mc.toDoList";

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
    List<String> mLabelList =  new ArrayList<>();
    ArrayAdapter<String> mLabelAdapter;
    private int mNoClicked;
    Intent mIntent = new Intent();


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_deadline_task);
        Repository.mEnable = false;
        Repository.mEnableRep = false;

        mDeadlineTask = new DeadlineTask();
        mIntent = getIntent();
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView timeFiled = findViewById(R.id.DeadlineTask_Activity_time_field);
        timeFiled.setOnClickListener(this);

        TextView dateFiled = findViewById(R.id.DeadlineTask_Activity_date_field);
        dateFiled.setOnClickListener(this);

        ImageView helpButton = findViewById(R.id.DeadlineTask_Activity_help_button);
        helpButton.setOnClickListener(this);

        ImageView helpDialog = findViewById(R.id.DeadlineTask_Activity_help_dialog);
        helpDialog.setVisibility(View.INVISIBLE);

        ImageView n = findViewById(R.id.deadlineTask_Activity_Normal);
        n.setVisibility(View.INVISIBLE);

        TextView helpDialogText = findViewById(R.id.DeadlineTask_Activity_dialog);
        helpDialogText.setVisibility(View.INVISIBLE);

        ImageView c = findViewById(R.id.deadlineTask_Activity_Cute);
        c.setVisibility(View.INVISIBLE);

        ImageView b = findViewById(R.id.deadlineTask_Activity_Brutal);
        b.setVisibility(View.INVISIBLE);

        ImageView no = findViewById(R.id.deadlineTask_Activity_No);
        no.setVisibility(View.INVISIBLE);

        ImageView f = findViewById(R.id.deadlineTask_Activity_Funny);
        f.setVisibility(View.INVISIBLE);

        ImageView s = findViewById(R.id.deadlineTask_Activity_Snarky);
        s.setVisibility(View.INVISIBLE);

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

        RadioButton x = findViewById(R.id.DeadlineTask_Activity_MotiNo_RadioButton);
        x.setOnClickListener(this);

        ImageView label = findViewById(R.id.DeadlineTask_Activity_Label_Button);
        label.setOnClickListener(this);

        mLabelView = findViewById(R.id.task_Activity_Label_layout);
        mLabelAdapter = new ArrayAdapter<>(ActivityDeadlineTask.this,android.R.layout.simple_expandable_list_item_1,mLabelList);
        mLabelView.setAdapter(mLabelAdapter);

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
    protected void onStart() {
        super.onStart();

        // check if the user makes a new Task or if he wants to change a existing one. If he wants to change it -> all fields are filled with the fields of the task that the user wants to change
        if(mIntent.getBooleanExtra("fromTaskDue",false)){
            Log.i(TAG, ":: ActivityDeadlineTask :: onStart was opend from TaskDue -> a change will be made");
            TextView change = findViewById(R.id.DeadlineTask_Activity_DeadlineTask);
            change.setText(R.string.DeadlineTask_Activity_DeadlineTask_change);
            EditText t = findViewById(R.id.DeadlineTask_Activity_title_field);
            t.setText(mIntent.getStringExtra("title"));
            EditText d = findViewById(R.id.DeadlineTask_Activity_description_field);
            d.setText(mIntent.getStringExtra("des"));
            TextView dateFiled = findViewById(R.id.DeadlineTask_Activity_date_field);
            dateFiled.setText(mIntent.getStringExtra("date"));
            TextView timeFiled = findViewById(R.id.DeadlineTask_Activity_time_field);
            timeFiled.setText(mIntent.getStringExtra("time"));
            String label1 = mIntent.getStringExtra("label1");
            String label2 = mIntent.getStringExtra("label2");
            String label3 = mIntent.getStringExtra("label3");
            if(label1 != null) {
                mLabelList.add(label1);
            }
            if (label2 != null) {
                mLabelList.add(label1);
            }if (label3 != null) {
                mLabelList.add(label1);
            }
            RadioButton brutal = findViewById(R.id.DeadlineTask_Activity_MotiBrutal_RadioButton);
            brutal.setChecked(mIntent.getBooleanExtra("brutal",false));
            RadioButton snarky = findViewById(R.id.DeadlineTask_Activity_MotiSnarky_RadioButton);
            snarky.setChecked(mIntent.getBooleanExtra("snarky",false));
            RadioButton cute = findViewById(R.id.DeadlineTask_Activity_MotiCute_RadioButton);
            cute.setChecked(mIntent.getBooleanExtra("cute",false));
            RadioButton funny = findViewById(R.id.DeadlineTask_Activity_MotiFunny_RadioButton);
            funny.setChecked(mIntent.getBooleanExtra("funny",false));
            RadioButton normal = findViewById(R.id.DeadlineTask_Activity_MotiNormal_RadioButton);
            normal.setChecked( mIntent.getBooleanExtra("normal",false));
            RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiNo_RadioButton);
            b.setChecked(mIntent.getBooleanExtra("noNoti",false));
        }
    }

    @Override
    public void onClick(View _v) {
        int year;
        int month;
        final int day;
        switch (_v.getId()) {

            case R.id.DeadlineTask_Activity_Label_Button: {
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick Label add button was pressed");
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
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick check Button was pressed");

                EditText t = findViewById(R.id.DeadlineTask_Activity_title_field);
                EditText d = findViewById(R.id.RepeatTask_Activity_description_field);
                String description;
                if(d == null){
                    description = "";
                }else{
                    description =  d.getText().toString();
                }
                String title = t.getText().toString();

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

                if(mIntent.getBooleanExtra("fromTaskDue",false)){
                    Repository.getInstance().changeData(mDeadlineTask, mIntent.getStringExtra("ref"));
                    Toast.makeText(ActivityDeadlineTask.this, R.string.DeadlineTask_Activity_Change_toast, Toast.LENGTH_SHORT).show();
                }else {
                    Repository.getInstance().saveData(mDeadlineTask);
                }
                finish();
            }
            break;
            case R.id.DeadlineTask_Activity_time_field: {
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick SelectTime Field was clicked");
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
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick SelectDate Field was clicked");
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
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick MotivationBrutal RadioButton was clicked");
                if(mBrutalClicked % 2 == 0) {
                    mDeadlineTask.setBrutal(true);
                }else{
                    mDeadlineTask.setBrutal(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiBrutal_RadioButton);
                    b.setChecked(false);
                }mBrutalClicked++;

            }break;
            case R.id.DeadlineTask_Activity_MotiCute_RadioButton:{
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick MotivationCute RadioButton was clicked");
                if(mCuteClicked % 2 == 0) {
                    mDeadlineTask.setCute(true);
                }else{
                    mDeadlineTask.setCute(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiCute_RadioButton);
                    b.setChecked(false);
                }mCuteClicked++;

            }break;
            case R.id.DeadlineTask_Activity_MotiFunny_RadioButton:{
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick MotivationFunny RadioButton was clicked");
                if(mFunnyClicked % 2 == 0) {
                    mDeadlineTask.setFunny(true);
                }else{
                    mDeadlineTask.setFunny(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiFunny_RadioButton);
                    b.setChecked(false);
                }mFunnyClicked++;
            }break;
            case R.id.DeadlineTask_Activity_MotiNormal_RadioButton:{
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick MotivationNormal RadioButton was clicked");
                if(mNormalClicked % 2 == 0) {
                    mDeadlineTask.setNormal(true);
                }else{
                    mDeadlineTask.setNormal(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiNormal_RadioButton);
                    b.setChecked(false);
                }mNormalClicked++;
            }break;
            case R.id.DeadlineTask_Activity_MotiSnarky_RadioButton:{
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick MotivationSnarky RadioButton was clicked");
                if(mSnarkyClicked % 2 == 0) {
                    mDeadlineTask.setSnarky(true);
                }else{
                    mDeadlineTask.setSnarky(false);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiSnarky_RadioButton);
                    b.setChecked(false);
                }mSnarkyClicked++;
            }break;
            case R.id.DeadlineTask_Activity_MotiNo_RadioButton:{
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick MotivationNormal RadioButton was clicked");
                if(mNoClicked % 2 == 0) {
                    mDeadlineTask.setNotification(false);
                }else{
                    mDeadlineTask.setNotification(true);
                    RadioButton b = findViewById(R.id.DeadlineTask_Activity_MotiNo_RadioButton);
                    b.setChecked(false);
                }mNoClicked++;
            }break;
            case R.id.DeadlineTask_Activity_help_button:{
                Log.i(TAG, ":: ActivityDeadlineTask ::onClick help button was pressed");
                ImageView helpDialog = findViewById(R.id.DeadlineTask_Activity_help_dialog);
                ImageView n = findViewById(R.id.deadlineTask_Activity_Normal);
                ImageView c = findViewById(R.id.deadlineTask_Activity_Cute);
                ImageView b = findViewById(R.id.deadlineTask_Activity_Brutal);
                ImageView no = findViewById(R.id.deadlineTask_Activity_No);
                ImageView f = findViewById(R.id.deadlineTask_Activity_Funny);
                ImageView s = findViewById(R.id.deadlineTask_Activity_Snarky);
                TextView helpDialogText = findViewById(R.id.DeadlineTask_Activity_dialog);
                if(mClickedHelp % 2 == 0) {
                    helpDialog.setVisibility(View.VISIBLE);
                    helpDialogText.setVisibility(View.VISIBLE);
                    n.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    no.setVisibility(View.VISIBLE);
                    f.setVisibility(View.VISIBLE);
                    s.setVisibility(View.VISIBLE);
                } else{
                    helpDialog.setVisibility(View.INVISIBLE);
                    helpDialogText.setVisibility(View.INVISIBLE);
                    n.setVisibility(View.INVISIBLE);
                    c.setVisibility(View.INVISIBLE);
                    b.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
                    f.setVisibility(View.INVISIBLE);
                    s.setVisibility(View.INVISIBLE);
                }
                mClickedHelp++;
            }break;
            default:
                Log.e(TAG, ":: ActivityDeadlineTask ::onClick unexpected ID encountered");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Repository.mEnable = true;
        Repository.mEnableRep = true;
    }
}
