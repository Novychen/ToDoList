package at.fhooe.mc.android;

import java.util.List;

/**
 * this class implements the task with the type Deadline
 */
public class DeadlineTask implements Task{

    private String mTitle;
    private String mDescription;
    private List<String> mLabel;
    private String mDate;


    /**
     * Consturctor for the Class {@link DeadlineTask} everthing is null
     */
    DeadlineTask(){
        this(null,null,null,null);
    }

    /**
     * Consturctor for the Class {@link DeadlineTask}
     * @param _title title of the task
     * @param _label label/s of the task
     * @param _description descripton of the task
     * @param _dateDead deadline of the task
     */
    DeadlineTask(String _title, List<String> _label, String _description, String _dateDead){

        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
        mDate = _dateDead;
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

    /**
     * getter for the variable {@link DeadlineTask#mDate}
     * @return the deadline of the task
     */
    public String getDate() {
        return mDate;
    }

    /**
     * setter for the variable {@link DeadlineTask#mDate}
     * @param _time the deadline time of the task
     * @param _date the deadline mDate of the task
     */
    public void setDate (String _time, String _date) {
        mDate = _date + _time;
    }





}
