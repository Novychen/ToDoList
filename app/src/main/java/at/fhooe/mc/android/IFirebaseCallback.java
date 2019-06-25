package at.fhooe.mc.android;

import java.util.List;

public interface IFirebaseCallback {
    void setData(Object _o);
    void setStringData(List<String> s);
    void setTimeData(List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h ,List<Integer> min, List<Integer> task);
    void setTitle(List<String> s,List<Integer> d, List<Integer> m, List<Integer> y);
    void setAll(List<String> s, List<Integer> d, List<Integer> m, List<Integer> y, List<Integer> h ,List<Integer> min, List<Integer> task);
}