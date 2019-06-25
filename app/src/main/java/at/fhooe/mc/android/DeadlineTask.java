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

    /**
     * Consturctor for the Class {@link DeadlineTask} everything is the default value (null,0,false, ...)
     */
    DeadlineTask(){
        this(null,null,null,0,0,0,0,0,0);
    }

    /**
     * Consturctor for the Class {@link DeadlineTask}
     * @param _title title of the task
     * @param _label label/s of the task
     * @param _description descripton of the task
     * @param _day day of the deadline of the task
     * @param _month  month of the deadline of the task
     * @param _year year of the deadline of the task
     * @param _hour hour of the deadline of the task
     * @param _minute minute of the deadline of the task
     */
    private DeadlineTask(String _title, List<String> _label, String _description, int _day, int _month, int _year, int _hour, int _minute, int _task){

        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
        mDay = _day;
        mMonth = _month;
        mYear = _year;
        mHour = _hour;
        mMinute = _minute;
        mTask = _task;
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
    void setLabel(List<String> _label) {
        mLabel = _label;
    }

    /**
     * getter for the variable {@link DeadlineTask#mDescription}
     * @return the description of the task
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * setter for the variable {@link DeadlineTask#mDescription}
     * @param _description the description of the task
     */
    void setDescription(String _description) {
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
     void setTitle(String _title) {
        mTitle = _title;
    }


    public int getDay() {
        return mDay;
    }

     void setDay(int _day) {
        mDay = _day;
    }

    public int getMonth() {
        return mMonth;
    }

     void setMonth(int _month) {
        mMonth = _month;
    }

    public int getHour() {
        return mHour;
    }

     void setHour(int _hour) {
        mHour = _hour;
    }

    public int getYear() {
        return mYear;
    }

     void setYear(int _year) {
        mYear = _year;
    }

    public int getMinute() {
        return mMinute;
    }

     void setMinute(int _minute) {
        mMinute = _minute;
    }

    public int getTask() {
        return mTask;
    }

    public void setTask(int _task) {
        mTask = _task;
    }
}
