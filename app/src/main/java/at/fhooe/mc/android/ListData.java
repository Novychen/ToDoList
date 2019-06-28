package at.fhooe.mc.android;

import android.util.Log;

class ListData {
    String mTitle;
    String mDate;
    private final static String TAG = "at.fhooe.mc.toDoList :: ListData";
    public ListData(String _Title){
        mTitle=_Title;
    }

    public ListData(String _titel, int _d, int _m, int _y) {
        mTitle = _titel;
        StringBuilder s = new StringBuilder("  ");
        s.append(_d + ".");
        s.append(_m+ ".");
        s.append(_y);
        mDate = s.toString();

    }
    public ListData(String _titel,int _rep, String _c) {
        mTitle = _titel;
        StringBuilder s = new StringBuilder("  ");
       s.append(_rep + " times per ");
       s.append(_c);
        mDate = s.toString();
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getMdate() {
        return mDate ;
    }

}
