package at.fhooe.mc.android;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * This class (Singelton Pattern) implements all methods that are connected to the database such as {@link Repository#getData(DatabaseReference, IFirebaseCallback)} or {@link Repository#saveData(Task)})}.
 */
class Repository {

    private static final String TAG = "at.fhooe.mc.toDoList :: Repository";
    private static Repository mInstance;
    private static String mUserId;

    private List<Integer> mTasks = new LinkedList<>();
    private List<Integer> mCountDead = new LinkedList<>();
    private List<Integer> mCountRep = new LinkedList<>();

    private List<Integer> mDayDead = new LinkedList<>();
    private List<Integer> mMonthDead = new LinkedList<>();
    private List<Integer> mYearDead = new LinkedList<>();
    private List<Integer> mHourDead = new LinkedList<>();
    private List<Integer> mMinuteDead = new LinkedList<>();

    private List<Integer> mRepeats = new LinkedList<>();
    private List<String> mRepeatRotation = new LinkedList<>();

    private List<String> mTitleRepeat = new LinkedList<>();
    private List<String> mTitleDead = new LinkedList<>();
    private List<List<String>> mLabelRepeat = new LinkedList<>();
    private List<List<String>> mLabelDead = new LinkedList<>();
    private List<String> mDescriptionRepeat = new LinkedList<>();
    private List<String> mDescriptionDead = new LinkedList<>();
    private List<String> mReferenceRepeat = new LinkedList<>();
    private List<String> mReferenceDead = new LinkedList<>();
    private List<Boolean> mBrutalRepeat = new LinkedList<>();
    private List<Boolean> mCuteRepeat = new LinkedList<>();
    private List<Boolean> mSnarkyRepeat = new LinkedList<>();
    private List<Boolean> mFunnyRepeat = new LinkedList<>();
    private List<Boolean> mNormalRepeat = new LinkedList<>();
    private List<Boolean> mBrutalDead = new LinkedList<>();
    private List<Boolean> mCuteDead = new LinkedList<>();
    private List<Boolean> mSnarkyDead = new LinkedList<>();
    private List<Boolean> mFunnyDead = new LinkedList<>();
    private List<Boolean> mNormalDead = new LinkedList<>();
    private List<Boolean> mNotificationDead = new LinkedList<>();
    private List<Boolean> mNotificationRepeat = new LinkedList<>();
    static boolean mEnable;

    /**
     * Constructor for Repository
     * @return Repository Object
     */
    static Repository getInstance(){
        if(mInstance == null){
            mInstance = new Repository();
        }
        return mInstance;
    }

    private Repository(){
    }


    private void cleanLists(){

        mTasks.clear();

        mDayDead.clear();
        mMonthDead.clear();
        mYearDead.clear();
        mHourDead.clear();
        mMinuteDead.clear();
        mBrutalDead.clear();
        mCuteDead.clear();
        mSnarkyDead.clear();
        mFunnyDead.clear();
        mNormalDead.clear();
        mTitleDead.clear();
        mLabelDead.clear();
        mDescriptionDead.clear();
        mReferenceDead.clear();
        mNotificationDead.clear();
        mCountDead.clear();

        mTitleRepeat.clear();
        mRepeatRotation.clear();
        mRepeats.clear();
        mBrutalRepeat.clear();
        mCuteRepeat.clear();
        mSnarkyRepeat.clear();
        mFunnyRepeat.clear();
        mNormalRepeat.clear();
        mLabelRepeat.clear();
        mDescriptionRepeat.clear();
        mNotificationRepeat.clear();
        mReferenceRepeat.clear();
        mCountRep.clear();
    }

    private void getRepeatData(DataSnapshot listSnapshot){
        String reference = listSnapshot.getKey();
        mReferenceRepeat.add(reference);

        String description = listSnapshot.child("description").getValue(String.class);
        mDescriptionRepeat.add(description);

        String title = listSnapshot.child("title").getValue(String.class);
        mTitleRepeat.add(title);

        String repeatRotation = listSnapshot.child("repeatRotation").getValue(String.class);
        mRepeatRotation.add(repeatRotation);

        Integer repeat = listSnapshot.child("repeats").getValue(Integer.class);
        mRepeats.add(repeat);

        Boolean notification = listSnapshot.child("notification").getValue(Boolean.class);
        mNotificationRepeat.add(notification);

        Boolean brutal = listSnapshot.child("brutal").getValue(Boolean.class);
        mBrutalRepeat.add(brutal);

        Boolean cute = listSnapshot.child("cute").getValue(Boolean.class);
        mCuteRepeat.add(cute);

        Boolean funny = listSnapshot.child("funny").getValue(Boolean.class);
        mFunnyRepeat.add(funny);

        Boolean snarky = listSnapshot.child("snarky").getValue(Boolean.class);
        mSnarkyRepeat.add(snarky);

        Boolean normal = listSnapshot.child("normal").getValue(Boolean.class);
        mNormalRepeat.add(normal);

        Integer count = listSnapshot.child("count").getValue(Integer.class);
        mCountRep.add(count);

        String firstLabel = listSnapshot.child("label").child("0").getValue(String.class);
        String secondLabel = listSnapshot.child("label").child("1").getValue(String.class);
        String thirdLabel = listSnapshot.child("label").child("2").getValue(String.class);
        LinkedList<String> s = new LinkedList<>();
        s.add(firstLabel);
        s.add(secondLabel);
        s.add(thirdLabel);
        mLabelRepeat.add(s);

    }

    private void geDeadlineData(DataSnapshot listSnapshot){
        String reference = listSnapshot.getKey();
        mReferenceDead.add(reference);

        Integer day = listSnapshot.child("day").getValue(Integer.class);
        mDayDead.add(day);

        Integer month = listSnapshot.child("month").getValue(Integer.class);
        mMonthDead.add(month);

        Integer year = listSnapshot.child("year").getValue(Integer.class);
        mYearDead.add(year);

        Integer hour = listSnapshot.child("hour").getValue(Integer.class);
        mHourDead.add(hour);

        Integer minute = listSnapshot.child("minute").getValue(Integer.class);
        mMinuteDead.add(minute);

        String description = listSnapshot.child("description").getValue(String.class);
        mDescriptionDead.add(description);

        Boolean notification = listSnapshot.child("notification").getValue(Boolean.class);
        mNotificationDead.add(notification);

        String title = listSnapshot.child("title").getValue(String.class);
        mTitleDead.add(title);

        Boolean brutal = listSnapshot.child("brutal").getValue(Boolean.class);
        mBrutalDead.add(brutal);

        Boolean cute = listSnapshot.child("cute").getValue(Boolean.class);
        mCuteDead.add(cute);

        Boolean funny = listSnapshot.child("funny").getValue(Boolean.class);
        mFunnyDead.add(funny);

        Boolean snarky = listSnapshot.child("snarky").getValue(Boolean.class);
        mSnarkyDead.add(snarky);

        Boolean normal = listSnapshot.child("normal").getValue(Boolean.class);
        mNormalDead.add(normal);

        Integer count = listSnapshot.child("count").getValue(Integer.class);
        mCountDead.add(count);

        String firstLabel = listSnapshot.child("label").child("0").getValue(String.class);
        String secondLabel = listSnapshot.child("label").child("1").getValue(String.class);
        String thirdLabel = listSnapshot.child("label").child("2").getValue(String.class);
        LinkedList<String> s = new LinkedList<>();
        s.add(firstLabel);
        s.add(secondLabel);
        s.add(thirdLabel);
        mLabelDead.add(s);

    }

    /**
     * gets data from the database
     * @param _myRef the reference (path) of the object (in the database) that want to be fetched
     */
    void getData(DatabaseReference _myRef, final IFirebaseCallback _callback){
       _myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               cleanLists();

               for (DataSnapshot listSnapshot: dataSnapshot.getChildren()) {
                   Integer task = listSnapshot.child("task").getValue(Integer.class);
                   mTasks.add(task);

                   if(task != null) {
                       if(task == 0){
                           geDeadlineData(listSnapshot);
                       }else if(task == 1){
                           getRepeatData(listSnapshot);
                       }
                   }
                }
               _callback.setTitle(mTitleRepeat,mTitleDead,mTasks, mDayDead, mMonthDead, mYearDead,mRepeats,mRepeatRotation);
               _callback.setAll(mTitleRepeat,mTitleDead, mDayDead, mMonthDead, mYearDead, mHourDead, mMinuteDead,mTasks,mLabelRepeat,mLabelDead,mDescriptionRepeat,mDescriptionDead,mReferenceRepeat,mReferenceDead,mRepeats,mRepeatRotation,mNormalRepeat,mFunnyRepeat,mSnarkyRepeat,mCuteRepeat,mBrutalRepeat,mNotificationRepeat,mNormalDead,mFunnyDead,mSnarkyDead,mCuteDead,mBrutalDead,mNotificationDead);
                }

           @Override
           public void onCancelled(DatabaseError error) {
               Log.w(TAG, "Failed to read value.", error.toException());
           }
       });
   }

    void getNotificationData(DatabaseReference _myRef, final IFirebaseCallback _callback){
            _myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mEnable) {

                        cleanLists();

                        for (DataSnapshot listSnapshot : dataSnapshot.getChildren()) {
                            Integer task = listSnapshot.child("task").getValue(Integer.class);
                            mTasks.add(task);

                            if (task != null) {
                                if (task == 0) {
                                    geDeadlineData(listSnapshot);
                                } else if (task == 1) {
                                    getRepeatData(listSnapshot);
                                }
                            }
                        }

                        _callback.setNotificationDeadlineData(mTasks, mDayDead, mMonthDead, mYearDead, mHourDead, mMinuteDead, mTitleDead, mNormalDead, mFunnyDead, mSnarkyDead, mCuteDead, mBrutalDead, mNotificationDead, mCountDead, mReferenceDead, mDescriptionDead, mLabelDead);
                        _callback.setNotificationRepeatData(mTasks, mRepeats, mRepeatRotation, mTitleRepeat, mNormalRepeat, mFunnyRepeat, mSnarkyRepeat, mCuteRepeat, mBrutalRepeat, mNotificationRepeat, mDescriptionRepeat, mLabelRepeat, mReferenceRepeat, mCountRep);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

    /**
     * saves a Task along with its tasknumber into the database
     * @param _t the mTask, that is saved
     */
    void saveData(Task _t){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String ref = mUserId;
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(ref);
        reference.push().setValue(_t);
    }

    void changeData(Task _t, String _ref){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(mUserId).child(_ref);
        reference.setValue(_t);
    }

    void saveNotificationData(boolean _notification, String _ref){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(mUserId).child(_ref).child("notification");
        reference.setValue(_notification);
    }

    void saveData(int _count, String _ref){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(mUserId).child(_ref).child("count");
        reference.setValue(_count);
    }

    /**
     * setter for the {@link Repository#mUserId}, which saves who is logged in
     * @param _user the UserID of the currentUser
     */
    void setUserId(String _user){
        mUserId = _user;
    }

    /**
     * getter for the {@link Repository#mUserId}, which saves who is logged in
     * @return mUserId, the ID of the currentUser
     */
    String getUserId(){
        return mUserId;
    }

    void removeDate(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child(key);
        ref.removeValue();
    }
}
