package at.fhooe.mc.android;

public class DeadlineTime {

    protected int mDeadlineHour;
    protected int mDeadlineMinute;

    protected boolean mWholeday;

    public DeadlineTime(){
        this(0,0,false);
    }

    DeadlineTime(int _hour, int _minute, boolean _wDay){

        mDeadlineMinute = _minute;
        mDeadlineHour = _hour;
        mWholeday = _wDay;
    }

    public boolean getwDay() {
        return mWholeday;
    }

    public void setwDay(boolean _wDay) {
        mWholeday = _wDay;
    }

    public int getHour() {
        return mDeadlineHour;
    }

    public void setHour(int _hour) {
        mDeadlineHour = _hour;
    }

    public int getMinute() {
        return mDeadlineMinute;
    }

    public void setMinute(int _minute) {
        mDeadlineMinute = _minute;
    }


}
