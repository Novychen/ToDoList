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

    private List<Integer> mDay = new LinkedList<>();
    private List<Integer> mMonth = new LinkedList<>();
    private List<Integer> mYear = new LinkedList<>();
    private List<Integer> mHour = new LinkedList<>();
    private List<Integer> mMinute = new LinkedList<>();

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


    /**
     * gets long values from the database. Is meant to get the highest Tasknumber (or the Number of Task the user currently has) as it saves the long value into the
     * {@link MainActivity#mTaskNumber} variable
     * @param _myRef the reference (path) of the long-value (in the database) that want to be fetched
     */
    void getLongData(DatabaseReference _myRef){
        _myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    MainActivity.setTaskNumber(dataSnapshot.getValue(Long.class));
                }catch(NullPointerException e){
                    Log.e(TAG, "getLongData: " + e.getMessage());
                }catch(ClassCastException e){
                    Log.e(TAG,"getLongData: " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * gets data from the database
     * @param _myRef the reference (path) of the object (in the database) that want to be fetched
     */
    void getData(DatabaseReference _myRef, final IFirebaseCallback _callback){
       _myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               mTasks.clear();

               mDay.clear();
               mMonth.clear();
               mYear.clear();
               mHour.clear();
               mMinute.clear();
               mBrutalDead.clear();
               mCuteDead.clear();
               mSnarkyDead.clear();
               mFunnyDead.clear();
               mNormalDead.clear();
               mTitleDead.clear();
               mLabelDead.clear();
               mDescriptionDead.clear();
               mReferenceDead.clear();

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
               mReferenceRepeat.clear();

               for (DataSnapshot listSnapshot: dataSnapshot.getChildren()) {

                   Integer task = listSnapshot.child("task").getValue(Integer.class);
                   mTasks.add(task);

                   if(task != null) {
                       if(task == 0){
                           String reference = listSnapshot.getKey();
                           mReferenceDead.add(reference);

                           Integer day = listSnapshot.child("day").getValue(Integer.class);
                           mDay.add(day);

                           Integer month = listSnapshot.child("month").getValue(Integer.class);
                           mMonth.add(month);

                           Integer year = listSnapshot.child("year").getValue(Integer.class);
                           mYear.add(year);

                           Integer hour = listSnapshot.child("hour").getValue(Integer.class);
                           mHour.add(hour);

                           Integer minute = listSnapshot.child("minute").getValue(Integer.class);
                           mMinute.add(minute);

                           String description = listSnapshot.child("description").getValue(String.class);
                           mDescriptionDead.add(description);

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

                           String firstLabel = listSnapshot.child("label").child("0").getValue(String.class);
                           String secondLabel = listSnapshot.child("label").child("1").getValue(String.class);
                           String thirdLabel = listSnapshot.child("label").child("2").getValue(String.class);
                           LinkedList<String> s = new LinkedList<>();
                           s.add(firstLabel);
                           s.add(secondLabel);
                           s.add(thirdLabel);
                           mLabelDead.add(s);
                      }else if(task == 1){
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

                           String firstLabel = listSnapshot.child("label").child("0").getValue(String.class);
                           String secondLabel = listSnapshot.child("label").child("1").getValue(String.class);
                           String thirdLabel = listSnapshot.child("label").child("2").getValue(String.class);
                           LinkedList<String> s = new LinkedList<>();
                           s.add(firstLabel);
                           s.add(secondLabel);
                           s.add(thirdLabel);
                           mLabelRepeat.add(s);
                       }
                   }
               }
                    if(mTasks.size() != 0) {
                        mTasks.remove(mTasks.size() - 1);
                    }
                   _callback.setTitle(mTitleRepeat, mTitleDead, mTasks,mDay, mMonth, mYear, mRepeats, mRepeatRotation);
                   _callback.setNotificationDeadlineData(mDay, mMonth, mYear, mHour, mMinute, mTitleDead,mNormalDead, mFunnyDead, mSnarkyDead, mCuteDead, mBrutalDead);
                   _callback.setNotificationRepeatData(mRepeats,mRepeatRotation, mTitleRepeat,mNormalRepeat, mFunnyRepeat, mSnarkyRepeat, mCuteRepeat, mBrutalRepeat);
                   _callback.setAll(mTitleRepeat,mDay,mMonth,mYear,mHour,mMinute,mTasks, mDescriptionRepeat, mReferenceRepeat, mLabelRepeat);
           }

           @Override
           public void onCancelled(DatabaseError error) {
               Log.w(TAG, "Failed to read value.", error.toException());
           }
       });
   }



    /**
     * saves a Task along with its tasknumber into the database
     * @param _t the task, that is saved
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

    /**
     * saves the current TaskNumber into the database
     * @param _currTaskNumber the number that represents the current Task
     */
    void saveData(long _currTaskNumber){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         String ref = mUserId + "/CurrentTask";
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(ref);
        reference.setValue(_currTaskNumber);
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
        long taskNumber = MainActivity.getTaskNumber() -1;
        Log.i(TAG, "taskNumber Value is: " + taskNumber);
        Repository.getInstance().saveData(taskNumber);
    }
}
