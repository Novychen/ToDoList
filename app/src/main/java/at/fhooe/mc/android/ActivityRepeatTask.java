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

    private final static String TAG = "at.fhooe.mc.toDoList";
    private RepeatTask mRepeatTask;
    private NumberPicker mPicker;
    private NumberPicker mTimesPicker;
    private Dialog mDialog;
    private String[] mRepeatCircles;
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
    Intent mIntent = new Intent();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_repeat_task);
        Repository.mEnable = false;
        Repository.mEnableRep = false;

        mRepeatTask = new RepeatTask();
        mIntent = getIntent();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView helpDialog = findViewById(R.id.RepeatTask_Activity_help_dialog);
        helpDialog.setVisibility(View.INVISIBLE);

        ImageView n = findViewById(R.id.RepeatTask_Activity_Normal);
        n.setVisibility(View.INVISIBLE);

        TextView helpDialogText = findViewById(R.id.RepeatTask_Activity_dialog);
        helpDialogText.setVisibility(View.INVISIBLE);

        ImageView c = findViewById(R.id.repeatTask_Activity_Cute);
        c.setVisibility(View.INVISIBLE);

        ImageView b = findViewById(R.id.repeatTask_Activity_Brutal);
        b.setVisibility(View.INVISIBLE);

        ImageView no = findViewById(R.id.repeatTask_Activity_No);
        no.setVisibility(View.INVISIBLE);

        ImageView f = findViewById(R.id.repeatTask_Activity_Funny);
        f.setVisibility(View.INVISIBLE);

        ImageView s = findViewById(R.id.repeatTask_Activity_Snarky);
        s.setVisibility(View.INVISIBLE);

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

        RadioButton not = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
        not.setOnClickListener(this);

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
        mDialog = new Dialog(ActivityRepeatTask.this);
        mDialog.setTitle("Repeat per");
        mDialog.setContentView(R.layout.activity_repeat_picker);
        Button set = mDialog.findViewById(R.id.RepeatTask_Activity_RepeatDialog_ok);
        set.setOnClickListener(this);
        mPicker = mDialog.findViewById(R.id.RepeatTask_Activity_Picker);
        mRepeatCircles = new String[] {"year","month","week", "day"};
        mPicker.setDisplayedValues(mRepeatCircles);
        mPicker.setMaxValue(mRepeatCircles.length-1);
        mPicker.setMinValue(0);
        mPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDialog.show();
    }

    public void showRepeatDialog(){

        mDialog = new Dialog(ActivityRepeatTask.this);
        mDialog.setTitle("Repeat");
        mDialog.setContentView(R.layout.activity_repeat_picker);
        Button set = mDialog.findViewById(R.id.RepeatTask_Activity_RepeatDialog_ok);
        set.setOnClickListener(this);
        mTimesPicker = mDialog.findViewById(R.id.RepeatTask_Activity_Picker);
        mTimesPicker.setValue(1);
        mTimesPicker.setMaxValue(20);
        mTimesPicker.setMinValue(1);
        mTimesPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // check if the user makes a new Task or if he wants to change a existing one. If he wants to change it -> all fields are filled with the fields of the task that the user wants to change
        if(mIntent.getBooleanExtra("fromTaskDue",false)){
            Log.i(TAG, " :: ActivityRepeatTask :: onStart was opend from TaskDue -> a change will be made");

            TextView change = findViewById(R.id.RepeatTask_Activity_RepeatTask);
            change.setText(R.string.RepeatTask_Activity_RepeatTask_change);

            EditText t = findViewById(R.id.RepeatTask_Activity_title_field);
            t.setText(mIntent.getStringExtra("title"));
            EditText d = findViewById(R.id.RepeatTask_Activity_description_field);
            TextView repeat = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
            repeat.setText(String.valueOf(mIntent.getIntExtra("repeat",1)));
            TextView repeatCircles = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
            repeatCircles.setText(mIntent.getStringExtra("circle"));
            d.setText(mIntent.getStringExtra("des"));
            String label1 = mIntent.getStringExtra("label1");
            String label2 = mIntent.getStringExtra("label2");
            String label3 = mIntent.getStringExtra("label3");
            if(label1 != null) {
                mLabelList.add(label1);
            }if (label2 != null) {
                mLabelList.add(label2);
            }if (label3 != null) {
                mLabelList.add(label3);
            }
            RadioButton brutal = findViewById(R.id.RepeatTask_Activity_MotiBrutal_RadioButton);
            brutal.setChecked(mIntent.getBooleanExtra("brutal",false));

            RadioButton snarky = findViewById(R.id.RepeatTask_Activity_MotiSnarky_RadioButton);
            snarky.setChecked(mIntent.getBooleanExtra("snarky",false));

            RadioButton cute = findViewById(R.id.RepeatTask_Activity_MotiCute_RadioButton);
            cute.setChecked(mIntent.getBooleanExtra("cute",false));

            RadioButton funny = findViewById(R.id.RepeatTask_Activity_MotiFunny_RadioButton);
            funny.setChecked(mIntent.getBooleanExtra("funny",false));

            RadioButton normal = findViewById(R.id.RepeatTask_Activity_MotiNormal_RadioButton);
            normal.setChecked( mIntent.getBooleanExtra("normal",false));

            RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
            boolean notification = !mIntent.getBooleanExtra("noNoti",false);
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
                Log.i(TAG, " :: ActivityRepeatTask ::onClick HowOfRepeat was pressed");
                showRepeatDialog();

            }break;
            case R.id.RepeatTask_Activity_RepeatDialog_ok:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick ok Button (from Dialog) was pressed");
                TextView circle = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
                TextView times = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
                if(mPicker != null){
                    int choice = mPicker.getValue();
                    circle.setText(mRepeatCircles[choice]);
                    mRepeatTask.setRepeatRotation(mRepeatCircles[choice]);
                   }
                if(mTimesPicker != null){
                    times.setText(String.valueOf(mTimesPicker.getValue()));
                    mRepeatTask.setRepeats(mTimesPicker.getValue());
                }

                mDialog.dismiss();

            }break;

            case R.id.RepeatTask_Activity_Check_Button: {
                Log.i(TAG, " :: ActivityRepeatTask ::onClick check Button was pressed");
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
                    mRepeatTask.setRepeatRotation(mRepeatCircles[choice]);
                }else{
                    mRepeatTask.setRepeatRotation("day");
                }

                if(mIntent.getBooleanExtra("fromTaskDue",false)){

                    if(mPicker == null){
                        TextView tv = findViewById(R.id.RepeatTask_Activity_HowOftRepeat);
                        String repeat = tv.getText().toString();
                        mRepeatTask.setRepeats(Integer.parseInt(repeat));
                    }
                    if(mTimesPicker == null){
                        TextView tv = findViewById(R.id.RepeatTask_Activity_RepeatCircles);
                        String repeatCircle = tv.getText().toString();
                        mRepeatTask.setRepeatRotation(repeatCircle);
                    }
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiBrutal_RadioButton);
                    boolean brutal = b.isChecked();
                    mRepeatTask.setBrutal(brutal);

                    RadioButton c = findViewById(R.id.RepeatTask_Activity_MotiCute_RadioButton);
                    boolean cute = c.isChecked();
                    mRepeatTask.setCute(cute);

                    RadioButton n = findViewById(R.id.RepeatTask_Activity_MotiNormal_RadioButton);
                    boolean normal = n.isChecked();
                    mRepeatTask.setNormal(normal);

                    RadioButton s = findViewById(R.id.RepeatTask_Activity_MotiSnarky_RadioButton);
                    boolean snarky = s.isChecked();
                    mRepeatTask.setSnarky(snarky);

                    RadioButton no = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
                    boolean noNot = no.isChecked();
                    mRepeatTask.setNotification(!noNot);

                    RadioButton f = findViewById(R.id.RepeatTask_Activity_MotiFunny_RadioButton);
                    boolean funny = f.isChecked();
                    mRepeatTask.setFunny(funny);

                    Repository.getInstance().changeData(mRepeatTask, mIntent.getStringExtra("ref"));
                    Toast.makeText(ActivityRepeatTask.this, R.string.DeadlineTask_Activity_Change_toast, Toast.LENGTH_SHORT).show();
                }else {
                    Repository.getInstance().saveData(mRepeatTask);
                }
                finish();
            }
            break;
            case R.id.RepeatTask_Activity_Label_Button: {
                Log.i(TAG, " :: ActivityRepeatTask ::onClick Label add button was pressed");
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
                Log.i(TAG, " :: ActivityRepeatTask ::onClick RepeatCircles was pressed");
                showRepeatCircleDialog();
            }break; 
            case R.id.RepeatTask_Activity_help_button:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick help Button was pressed");

                ImageView helpDialog = findViewById(R.id.RepeatTask_Activity_help_dialog);
                ImageView n = findViewById(R.id.RepeatTask_Activity_Normal);
                ImageView c = findViewById(R.id.repeatTask_Activity_Cute);
                ImageView b = findViewById(R.id.repeatTask_Activity_Brutal);
                ImageView no = findViewById(R.id.repeatTask_Activity_No);
                ImageView f = findViewById(R.id.repeatTask_Activity_Funny);
                ImageView s = findViewById(R.id.repeatTask_Activity_Snarky);
                TextView helpDialogText = findViewById(R.id.RepeatTask_Activity_dialog);
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

            case R.id.RepeatTask_Activity_MotiBrutal_RadioButton:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick MotivationBrutal RadioButton was clicked");
                if(mBrutalClicked % 2 == 0) {
                    mRepeatTask.setBrutal(true);
                }else{
                    mRepeatTask.setBrutal(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiBrutal_RadioButton);
                    b.setChecked(false);
                }mBrutalClicked++;

            }break;
            case R.id.RepeatTask_Activity_MotiCute_RadioButton:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick MotivationCute RadioButton was clicked");
                if(mCuteClicked % 2 == 0) {
                    mRepeatTask.setCute(true);
                }else{
                    mRepeatTask.setCute(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiCute_RadioButton);
                    b.setChecked(false);
                }mCuteClicked++;

            }break;
            case R.id.RepeatTask_Activity_MotiFunny_RadioButton:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick MotivationFunny RadioButton was clicked");
                if(mFunnyClicked % 2 == 0) {
                    mRepeatTask.setFunny(true);
                }else{
                    mRepeatTask.setFunny(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiFunny_RadioButton);
                    b.setChecked(false);
                }mFunnyClicked++;
            }break;
            case R.id.RepeatTask_Activity_MotiNormal_RadioButton:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick MotivationNormal RadioButton was clicked");
                if(mNormalClicked % 2 == 0) {
                    mRepeatTask.setNormal(true);
                }else{
                    mRepeatTask.setNormal(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNormal_RadioButton);
                    b.setChecked(false);
                }mNormalClicked++;
            }break;
            case R.id.RepeatTask_Activity_MotiNo_RadioButton:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick MotivationNormal RadioButton was clicked");
                if(mNoClicked % 2 == 0) {
                    mRepeatTask.setNotification(false);
                }else{
                    mRepeatTask.setNotification(true);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiNo_RadioButton);
                    b.setChecked(false);
                }mNoClicked++;
            }break;
            case R.id.RepeatTask_Activity_MotiSnarky_RadioButton:{
                Log.i(TAG, " :: ActivityRepeatTask ::onClick MotivationSnarky RadioButton was clicked");
                if(mSnarkyClicked % 2 == 0) {
                    mRepeatTask.setSnarky(true);
                }else{
                    mRepeatTask.setSnarky(false);
                    RadioButton b = findViewById(R.id.RepeatTask_Activity_MotiSnarky_RadioButton);
                    b.setChecked(false);
                }mSnarkyClicked++;
            }break;

            default:
                Log.e(TAG, " :: ActivityRepeatTask ::onClick unexpected ID encountered");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Repository.mEnable = true;
        Repository.mEnableRep = true;

    }
}
