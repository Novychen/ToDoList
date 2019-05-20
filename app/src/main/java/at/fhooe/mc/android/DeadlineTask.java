package at.fhooe.mc.android;

public class DeadlineTask {

    private DeadlineTime mDeadlineTime;
    private DeadlineDay mDeadLineDay;
    private String mTitle;
    private String mDescription;
    private String mLabel;


    DeadlineTask(){
        this(null,null,null,null,null);
    }

    DeadlineTask(String _title, String _label, String _description, DeadlineTime _d, DeadlineDay _dd){

        mDeadlineTime = _d;
        mDeadLineDay = _dd;
        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
    }

    DeadlineTask(String _title, String _description, DeadlineTime _d, DeadlineDay _dd){
        mLabel = "task";
        mDeadlineTime = _d;
        mDeadLineDay = _dd;
        mDescription = _description;
        mTitle = _title;
    }


    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String _label) {
        mLabel = _label;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String _description) {
        mDescription = _description;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setTitle(String _title) {
        mTitle = _title;
    }

    public DeadlineTime getDeadlineTime() {
        return mDeadlineTime;
    }

    public void setDeadlineTime(DeadlineTime _deadline) {
        mDeadlineTime = _deadline;
    }

    public DeadlineDay getDeadlineDay() {
        return mDeadLineDay;
    }

    public void setDeadlineDay(DeadlineDay _deadline) {
        mDeadLineDay = _deadline;
    }

}
