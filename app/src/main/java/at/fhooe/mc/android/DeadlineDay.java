package at.fhooe.mc.android;

public class DeadlineDay {

    protected int mDeadlineDay;
    protected int mDeadlineMonth;
    protected int mDeadlineYear;

    DeadlineDay(){
        this(0,0,0);
    }

    DeadlineDay(int _day, int _month, int _year){

        mDeadlineDay = _day;
        mDeadlineMonth = _month;
        mDeadlineYear = _year;
            }

    public int getDay() {
        return mDeadlineDay;
    }

    public void setDay(int _day) {
        mDeadlineDay = _day;
    }

    public int getMonth() {
        return mDeadlineMonth;
    }

    public void setMonth(int _month) {
        mDeadlineMonth = _month;
    }

    public int getYear() {
        return mDeadlineYear;
    }

    public void setYear(int _year) {
        mDeadlineYear = _year;
    }

}
