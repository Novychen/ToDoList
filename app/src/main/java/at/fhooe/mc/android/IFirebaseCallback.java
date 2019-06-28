package at.fhooe.mc.android;

import java.util.List;

public interface IFirebaseCallback {
    void setNotificationDeadlineData(List <Integer>_task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _h, List<Integer> _min, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal,List<Boolean> _notification, List<Integer> _count, List<String> _ref,List<String> _des, List<List<String>> _label);
    void setNotificationRepeatData(List <Integer>_task, List<Integer> _r, List<String> _c, List<String> _t, List<Boolean> _norm, List<Boolean> _funny, List<Boolean> _snarky, List<Boolean> _cute, List<Boolean> _brutal ,List<Boolean> _notification, List<String> _des, List<List<String>> _label);
    void setTitle(List<String> _repeatT, List<String> _deadT, List<Integer> _task, List<Integer> _d, List<Integer> _m, List<Integer> _y, List<Integer> _repeats, List<String> _repeatCircle);
    void setAll(List<String> _repeatT, List<String> _deadT, List<Integer> _d, List<Integer> _mo, List<Integer> _y, List<Integer> _h, List<Integer> _mi, List<Integer> _t, List<List<String>> _labelRep, List<List<String>> _labelDead,List<String> _desrep,List<String> _desdead,List<String> _refrep,List<String> _refdead,List<Integer> _repeats, List<String> _repeatCircle, List<Boolean> _normRep, List<Boolean> _funnyRep, List<Boolean> _snarkyRep, List<Boolean> _cuteRep, List<Boolean> _brutalRep,List<Boolean> _notificationRep, List<Boolean> _normDead, List<Boolean> _funnyDead, List<Boolean> _snarkyDead, List<Boolean> _cuteDead, List<Boolean> _brutalDead,List<Boolean> _notificationDead);

    }