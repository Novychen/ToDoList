package at.fhooe.mc.android;

import android.util.Log;

class ListData {
    String mTitle;
    String mdate;
    Object data;
    private final static String TAG = "at.fhooe.mc.toDoList :: ListData";

    public ListData(Object _o){
        data = _o;
    }
    public ListData(String _titel, String _date) {
        mTitle = _titel;
        mdate = _date;
        Object data;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getMdate() {
        return mdate ;
    }
    public String getTitle(){
        StringBuilder s  = new StringBuilder("Task:");
        if(data.toString().contains("title=")){
            Log.i(TAG, "found");
        }
        return data.toString();
    }
}
