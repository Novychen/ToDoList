package at.fhooe.mc.android;

import java.util.List;

public class RepeatTask implements Task {

    private int mTask;
    private String mTitle;
    private String mDescription;
    private List<String> mLabel;
    private int mRepeats;
    private String mRepeatRotation;


    /**
     * Consturctor for the Class {@link RepeatTask} everything is the default value (null,0,false, ...)
     */
    RepeatTask() {
        this(null,null,null,0,null,0);
    }

    /**
     * Consturctor for the Class {@link RepeatTask}
     * @param _title title of the task
     * @param _label label/s of the task
     * @param _description descripton of the task
     * @param _repeats how many time the task needs to be done within {@link RepeatTask#mRepeatRotation}
     * @param _repeatRotation how often the repeats take place
     */
    private RepeatTask(String _title, List<String> _label, String _description, int _repeats, String _repeatRotation, int _task){
        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
        mRepeatRotation = _repeatRotation;
        mRepeats = _repeats;
        mTask = _task;
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
    void setLabel(List<String> _label) {
        mLabel = _label;
    }

    /**
     * getter for the variable {@link RepeatTask#mDescription}
     * @return the description of the task
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * setter for the variable {@link RepeatTask#mDescription}
     * @param _description the description of the task
     */
    void setDescription(String _description) {
        mDescription = _description;
    }

    /**
     * getter for the variable {@link RepeatTask#mTitle}
     * @return the title of the task
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * setter for the variable {@link RepeatTask#mTitle}
     * @param _title the title of the task
     */
    void setTitle(String _title) {
        mTitle = _title;
    }


    public int getRepeats() {
        return mRepeats;
    }

    void setRepeats(int _repeats) {
        mRepeats = _repeats;
    }

    public String getRepeatRotation() {
        return mRepeatRotation;
    }

    void setRepeatRotation(String _repeatRotation) {
        mRepeatRotation = _repeatRotation;
    }

    public int getTask() {
        return mTask;
    }

    public void setTask(int _task) {
        mTask = _task;
    }
}
