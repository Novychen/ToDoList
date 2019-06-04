package at.fhooe.mc.android;

public class DeadlineTask implements Task{

    private String mTitle;
    private String mDescription;
    private String mLabel;
    private String mDate;



    DeadlineTask(){
        this(null,null,null,null);
    }

    DeadlineTask(String _title, String _label, String _description, String _dateDead){

        mDescription = _description;
        mTitle = _title;
        mLabel = _label;
        mDate = _dateDead;
    }

    DeadlineTask(String _title, String _description, String _dateDead){
        mLabel = "task";
        mDescription = _description;
        mTitle = _title;
        mDate = _dateDead;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String _title) {
        mTitle = _title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate (String _time, String _date) {
        mDate = _date + _time;
    }





}
