package at.fhooe.mc.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
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
    ArrayAdapter<String> mLabelAdapter;
    GridView mLabelView;
    private int mClickedHelp;
    private int mBrutalClicked;
    private int mCuteClicked;
    private int mFunnyClicked;
    private int mNormalClicked;
    private int mSnarkyClicked;
    private int mNoClicked;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_repeat_task);
        Repository.mEnable = false;
        mRepeatTask = new RepeatTask();
        intent = getIntent();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView helpDialog = findViewById(R.id.RepeatTask_Activity_help_dialog);
        helpDialog.setVisibility(View.INVISIBLE);

        ImageView help = findViewById(R.id.RepeatTask_Activity_help_button);
        help.setOnClickListener(this);

        RadioButton brutal = findViewById(R.id.RepeatTask_Activity_MotiBrutal_RadioButton);
        brutal.setOnClickListener(this);

        RadioButton cute = findViewById(R.id.RepeatTask_Activity_MotiCute_RadioButton);
        cute.setOnClickListener(this);

        RadioButton snarky = findViewById(R.id.RepeatTask_Activity_MotiSnarky_RadioButton);
        snarky.setOnClickListener(this);

        RadioButton funny = findViewById(R.id.RepeatTask_Activity_MotiFunny_RadioButton);
        funny.setOnClickListener(this);

        RadioButton normal = findViewById(R.id.RepeatTask_Activity_MotiNormal_RadioButton);
        normal.setOnClickListener(this);

        RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
        b.setOnClickListener(this);

        TextView repeat = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
        repeat.setOnClickListener(this);

        TextView repeatCircles = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
        repeatCircles.setOnClickListener(this);

        ImageView ok = findViewById(R.id.RepeatTask_Activity_Check_Button);
        ok.setOnClickListener(this);

        mLabelView = findViewById(R.id.task_Activity_Label_layout);
        mLabelList = new ArrayList<>();
        mLabelAdapter = new ArrayAdapter<>(ActivityRepeatTask.this, android.R.layout.simple_expandable_list_item_1, mLabelList);
        mLabelView.setAdapter(mLabelAdapter);

        mLabelView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _parent, View _view, int _position, long _id) {

                mLabelAdapter.remove(mLabelList.get(_position));
                Toast.makeText(ActivityRepeatTask.this, R.string.task_Activity_LabelDelete_Toast, Toast.LENGTH_SHORT).show();
                mLabelCount--;
            }
        });
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
        mPicker = d.findViewById(R.id.RepeatTask_Activity_Picker);
        s = new String[] {"year","month","week", "day"};
        mPicker.setDisplayedValues(s);
        mPicker.setMaxValue(s.length-1);
        mPicker.setMinValue(0);
        mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        d.show();
    }

    public void showRepeatDialog(){

        d = new Dialog(ActivityRepeatTask.this);
        d.setTitle("Repeat");
        d.setContentView(R.layout.activity_repeat_picker);
        Button set = d.findViewById(R.id.RepeatTask_Activity_RepeatDialog_ok);
        set.setOnClickListener(this);
        mTimesPicker = d.findViewById(R.id.RepeatTask_Activity_Picker);
        mTimesPicker.setValue(1);
        mTimesPicker.setMaxValue(20);
        mTimesPicker.setMinValue(1);
        mTimesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        d.show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(intent.getBooleanExtra("fromTaskDue",false)){

            EditText t = findViewById(R.id.RepeatTask_Activity_title_field);
            t.setText(intent.getStringExtra("title"));
            EditText d = findViewById(R.id.RepeatTask_Activity_description_field);
            TextView repeat = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
            repeat.setText(String.valueOf(intent.getIntExtra("repeat",1)));
            TextView repeatCircles = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
            repeatCircles.setText(intent.getStringExtra("circle"));
            d.setText(intent.getStringExtra("des"));
            String label1 = intent.getStringExtra("label1");
            String label2 = intent.getStringExtra("label2");
            String label3 = intent.getStringExtra("label3");
            if(label1 != null) {
                mLabelList.add(label1);
            }if (label2 != null) {
                mLabelList.add(label1);
            }if (label3 != null) {
                mLabelList.add(label1);
            }
            RadioButton brutal = findViewById(R.id.RepeatTask_Activity_MotiBrutal_RadioButton);
            brutal.setChecked(intent.getBooleanExtra("brutal",false));

            RadioButton snarky = findViewById(R.id.RepeatTask_Activity_MotiSnarky_RadioButton);
            snarky.setChecked(intent.getBooleanExtra("snarky",false));

            RadioButton cute = findViewById(R.id.RepeatTask_Activity_MotiCute_RadioButton);
            cute.setChecked(intent.getBooleanExtra("cute",false));

            RadioButton funny = findViewById(R.id.RepeatTask_Activity_MotiFunny_RadioButton);
            funny.setChecked(intent.getBooleanExtra("funny",false));

            RadioButton normal = findViewById(R.id.RepeatTask_Activity_MotiNormal_RadioButton);
            normal.setChecked( intent.getBooleanExtra("normal",false));

            RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
            boolean notification = intent.getBooleanExtra("noNoti",false);
            if(notification){
                b.setChecked(false);
            }else {
                b.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {

            case R.id.RepeatTask_Activity_HowOftRepeat:{
               showRepeatDialog();

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

            case R.id.RepeatTask_Activity_Check_Button: {

                Log.i(TAG, "::onClick check Button was pressed");
                EditText d = findViewById(R.id.RepeatTask_Activity_description_field);
                EditText t = findViewById(R.id.RepeatTask_Activity_title_field);

                String description = d.getText().toString();
                String title = t.getText().toString();

                if(title.equals("")){
                    title = "Task";
                }
                mRepeatTask.setTitle(title);
                mRepeatTask.setDescription(description);
                mRepeatTask.setTask(1);

                if(mLabelList.size() == 0){
                    mLabelList.add("");
                }
                mRepeatTask.setLabel(mLabelList);

                if(mTimesPicker != null){
                    mRepeatTask.setRepeats(mTimesPicker.getValue());
                }else{
                    mRepeatTask.setRepeats(1);
                }

                if(mPicker != null){
                    int choice = mPicker.getValue();
                    mRepeatTask.setRepeatRotation(s[choice]);
                }else{
                    mRepeatTask.setRepeatRotation("day");
                }

                if(intent.getBooleanExtra("fromTaskDue",false)){
                    Repository.getInstance().changeData(mRepeatTask,intent.getStringExtra("ref"));
                    Toast.makeText(ActivityRepeatTask.this, R.string.DeadlineTask_Activity_Change_toast, Toast.LENGTH_SHORT).show();
                }else {
                    Repository.getInstance().saveData(mRepeatTask);
                }
                finish();
            }
            break;
            case R.id.RepeatTask_Activity_Label_Button: {
                mLabelCount++;
                if (mLabelCount <= 3) {
                    EditText txt = findViewById(R.id.RepeatTask_Activity_setLabel_field);
                    String getLabel = txt.getText().toString();
                    if(getLabel.equals("")){
                        mLabelCount--;
                        return;
                    }
                    mLabelList.add(mLabelList.size(), getLabel);
                    mLabelAdapter.notifyDataSetChanged();
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
            }break; 
            case R.id.RepeatTask_Activity_help_button:{
                ImageView helpDialog = findViewById(R.id.RepeatTask_Activity_help_dialog);
                if(mClickedHelp % 2 == 0) {
                    helpDialog.setVisibility(View.VISIBLE);
                } else{
                    helpDialog.setVisibility(View.INVISIBLE);
                }
                mClickedHelp++;
            }break;

            case R.id.RepeatTask_Activity_MotiBrutal_RadioButton:{
                Log.i(TAG, "::onClick MotivationBrutal RadioButton was clicked");
                if(mBrutalClicked % 2 == 0) {
                    mRepeatTask.setBrutal(true);
                }else{
                    mRepeatTask.setBrutal(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiBrutal_RadioButton);
                    b.setChecked(false);
                }mBrutalClicked++;

            }break;
            case R.id.RepeatTask_Activity_MotiCute_RadioButton:{
                Log.i(TAG, "::onClick MotivationCute RadioButton was clicked");
                if(mCuteClicked % 2 == 0) {
                    mRepeatTask.setCute(true);
                }else{
                    mRepeatTask.setCute(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiCute_RadioButton);
                    b.setChecked(false);
                }mCuteClicked++;

            }break;
            case R.id.RepeatTask_Activity_MotiFunny_RadioButton:{
                Log.i(TAG, "::onClick MotivationFunny RadioButton was clicked");
                if(mFunnyClicked % 2 == 0) {
                    mRepeatTask.setFunny(true);
                }else{
                    mRepeatTask.setFunny(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiFunny_RadioButton);
                    b.setChecked(false);
                }mFunnyClicked++;
            }break;
            case R.id.RepeatTask_Activity_MotiNormal_RadioButton:{
                Log.i(TAG, "::onClick MotivationNormal RadioButton was clicked");
                if(mNormalClicked % 2 == 0) {
                    mRepeatTask.setNormal(true);
                }else{
                    mRepeatTask.setNormal(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNormal_RadioButton);
                    b.setChecked(false);
                }mNormalClicked++;
            }break;
            case R.id.RepeatTask_Activity_MotiNo_RadioButton:{
                Log.i(TAG, "::onClick MotivationNormal RadioButton was clicked");
                if(mNoClicked % 2 == 0) {
                    mRepeatTask.setNotification(false);
                }else{
                    mRepeatTask.setNotification(true);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
                    b.setChecked(false);
                }mNoClicked++;
            }break;
            case R.id.RepeatTask_Activity_MotiSnarky_RadioButton:{
                Log.i(TAG, "::onClick MotivationSnarky RadioButton was clicked");
                if(mSnarkyClicked % 2 == 0) {
                    mRepeatTask.setSnarky(true);
                }else{
                    mRepeatTask.setSnarky(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiSnarky_RadioButton);
                    b.setChecked(false);
                }mSnarkyClicked++;
            }break;

            default:
                Log.e(TAG, "task_Activity::onClick unexpected ID encountered");

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Repository.mEnable = true;
    }
}
