package at.fhooe.mc.android;

import java.util.List;

public interface IFirebaseCallback {
    void setNotificationDeadlineData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h , List<Integer> min, List<String> t , List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal);
    void setNotificationRepeatData(List<Integer> r, List<String> c,  List<String> t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal);
    void setTitle(List<String> _repeatT, List<String> _deadT, List<Integer> _task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _repeats, List<String> _repeatCircle);
    void setAll(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h ,List<Integer> min, List<Integer> task, List<String> des,List<String> ref,List<List<String>> label);
}