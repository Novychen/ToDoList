package at.fhooe.mc.android;

import java.util.List;

public interface IFirebaseCallback {
    void setData(Object _o);
    void setStringData(List<String> s);
    void setNotificationDeadlineData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h , List<Integer> min, List<String> t);
    void setNotificationRepeatData(List<Integer> r, List<String> c,  List<String> t);
    void setTitle(List<String> s,List<Integer> d, List<Integer> m, List<Integer> y);
}