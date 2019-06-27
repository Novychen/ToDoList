package at.fhooe.mc.android;

import java.util.List;

/**
 * this class implements the task with the type Deadline
 */
public class DeadlineTask implements Task{

    private int mTask;
    private String mTitle;
    private String mDescription;
    private List<String> mLabel;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHour;
    private int mMinute;
    private boolean mNotification;
    private int mCount;
    private boolean mBrutal;
    private boolean mCute;
    private boolean mNormal;
    private boolean mSnarky;
    private boolean mFunny;

    /**
     * Consturctor for the Class {@link DeadlineTask} everything is the default value (null,0,false, ...)
     */
    DeadlineTask(){
        this(null,null,null,0,0,0,0,0,0,false,0,false,false,false,false,false);
    }

    /**
     *
     * Consturctor for the Class {@link DeadlineTask}
     * @param _title title of the task
     * @param _label label/s of the task
     * @param _description description of the task
     * @param _day day of the deadline of the task
     * @param _month  month of the deadline of the task
     * @param _year year of the deadline of the task
     * @param _hour hour of the deadline of the task
     * @param _minute minute of the deadline of the task
     * @param _notification
     * @param _count
     * @param _brutal
     * @param _cute
     * @param _normal
     * @param _snarky
     * @param _funny
     */
    private DeadlineTask(String _title, List<String> _label, String _description, int _day, int _month, int _year, int _hour, int _minute, int _task,  boolean _notification, int _count, boolean _brutal, boolean _cute, boolean _normal, boolean _snarky, boolean _funny){

        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
        mDay = _day;
        mMonth = _month;
        mYear = _year;
        mHour = _hour;
        mMinute = _minute;
        mTask = _task;
        mCount =_count;
        mNotification = _notification;
        mBrutal = _brutal;
        mCute = _cute;
        mFunny = _funny;
        mNormal = _normal;
        mSnarky = _snarky;
    }


    /**
     * getter for the variable {@link DeadlineTask#mLabel}
     * @return the label {@link DeadlineTask#mLabel}
     */
    public List<String> getLabel() {
        return mLabel;
    }

    /**
     * setter for the variable {@link DeadlineTask#mLabel}
     * @param _label the label of the task
     */
    public void setLabel(List<String> _label) {
        mLabel = _label;
    }


    /**
     * setter for the variable {@link DeadlineTask#mDescription}
     * @param _description the description of the task
     */
    public void setDescription(String _description) {
        mDescription = _description;
    }

    /**
     * getter for the variable {@link DeadlineTask#mTitle}
     * @return the title of the task
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * setter for the variable {@link DeadlineTask#mTitle}
     * @param _title the title of the task
     */
    public void setTitle(String _title) {
        mTitle = _title;
    }

    public void setDay(int _day) {
        mDay = _day;
    }

    public int getDay() {
        return mDay;
    }

    public void setMonth(int _month) {
        mMonth = _month;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setHour(int _hour) {
        mHour = _hour;
    }

    public int getHour() {
        return mHour;
    }

    public void setYear(int _year) {
        mYear = _year;
    }

    public int getYear() {
        return mYear;
    }

    public void setMinute(int _minute) {
        mMinute = _minute;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setTask(int _task) {
        mTask = _task;
    }

    public int getTask() {
        return mTask;
    }

    public void setCute(boolean _cute) { mCute = _cute; }

    public boolean getCute() { return mCute; }

    public void setBrutal(boolean _brutal) { mBrutal = _brutal; }

    public boolean getBrutal() { return mBrutal; }

    public void setNormal(boolean _normal) { mNormal = _normal; }

    public boolean getNormal() { return mNormal; }

    public void setSnarky(boolean _snarky) { mSnarky = _snarky; }

    public boolean getSnarky() { return mSnarky; }

    public void setFunny(boolean _funny) { mFunny = _funny; }

    public boolean getFunny() { return mFunny; }

    public boolean getNotification() { return mNotification; }

    public void setNotification(boolean _notification) {
        mNotification = _notification;
    }

    public void setCount(int _count) { mCount = _count; }

    public int getCount() { return mCount; }
}
