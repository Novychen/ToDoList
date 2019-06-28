package at.fhooe.mc.android;

import java.util.List;

public class RepeatTask implements Task {

    private int mTask;
    private String mTitle;
    private String mDescription;
    private List<String> mLabel;
    private int mRepeats;
    private String mRepeatRotation;
    private boolean mNotification;
    private int mCount;
    private boolean mBrutal;
    private boolean mCute;
    private boolean mNormal;
    private boolean mSnarky;
    private boolean mFunny;



    /**
     * Consturctor for the Class {@link RepeatTask} everything is the default value (null,0,false, ...)
     */
    RepeatTask() {
        this(null,null,null,0,null,0,true,0,false,false,false,false,false);
    }

    /**
     * Consturctor for the Class {@link RepeatTask}
     * @param _title title of the task
     * @param _label label/s of the task
     * @param _description descripton of the task
     * @param _repeats how many time the task needs to be done within {@link RepeatTask#mRepeatRotation}
     * @param _repeatRotation how often the repeats take place
     */
    private RepeatTask(String _title, List<String> _label, String _description, int _repeats, String _repeatRotation, int _task, boolean _notification, int _count, boolean _brutal, boolean _cute, boolean _normal, boolean _snarky, boolean _funny){
        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
        mRepeatRotation = _repeatRotation;
        mRepeats = _repeats;
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
     * getter for the variable {@link RepeatTask#mLabel}
     * @return the label {@link RepeatTask#mLabel}
     */
    public List<String> getLabel() {
        return mLabel;
    }

    /**
     * setter for the variable {@link RepeatTask#mLabel}
     * @param _label the label of the task
     */
    public void setLabel(List<String> _label) {
        mLabel = _label;
    }

    /**
     * setter for the variable {@link RepeatTask#mDescription}
     * @param _description the description of the task
     */
    public void setDescription(String _description) {
        mDescription = _description;
    }

    /**
     * setter for the variable {@link RepeatTask#mTitle}
     * @param _title the title of the task
     */
    public void setTitle(String _title) {
        mTitle = _title;
    }

   public String getTitle() {
        return mTitle;
    }

   public void setRepeats(int _repeats) {
        mRepeats = _repeats;
    }

   public int getRepeats() {
        return mRepeats;
    }

   public void setRepeatRotation(String _repeatRotation) {
        mRepeatRotation = _repeatRotation;
    }

   public String getRepeatRotation() {
        return mRepeatRotation;
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

    public String getDescription() {
        return mDescription;
    }
}
