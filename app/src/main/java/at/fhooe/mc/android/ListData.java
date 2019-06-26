package at.fhooe.mc.android;

import android.util.Log;

class ListData {
    String mTitle;
    String mdate;
    private final static String TAG = "at.fhooe.mc.toDoList :: ListData";

    public ListData(String _titel, int _d, int _m, int _y) {
        mTitle = _titel;
        StringBuilder s = new StringBuilder();
        s.append(_d + ".");
        s.append(_m+ ".");
        s.append(_y);
        mdate = s.toString();

    }

    public String getmTitle() {
        return mTitle;
    }

    public String getMdate() {
        return mdate ;
    }

}
