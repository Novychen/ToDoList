package at.fhooe.mc.android;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
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

    private static List<Object> mValue = new LinkedList<>();
    private static List<Integer> mDay = new LinkedList<>();
    private static List<Integer> mMonth = new LinkedList<>();
    private static List<Integer> mYear = new LinkedList<>();
    private static List<Integer> mHour = new LinkedList<>();
    private static List<Integer> mMinute = new LinkedList<>();
    private static List<String> mDescription = new LinkedList<>();
    private static List<Integer> mTasks = new LinkedList<>();
    private static List<String> mTitle = new LinkedList<>();
    private static List<String> mReference = new LinkedList<>();

    private  List<Integer> mRepeats = new LinkedList<>();
    private  List<String> mRepeatRotation = new LinkedList<>();
    private  List<List<String>> mLabel = new LinkedList<>();



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
                    return;
                }catch(ClassCastException e){
                    return;
                }
                Log.i(TAG, "Value is: " + mValue);

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
               mValue.clear();
               mDay.clear();
               mMonth.clear();
               mYear.clear();
               mHour.clear();
               mMinute.clear();
               mTasks.clear();
               mTitle.clear();
               mRepeatRotation.clear();
               mRepeats.clear();
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               mValue.clear();
               mTitle.clear();
               mLabel.clear();
               mDescription.clear();
               mReference.clear();
               for (DataSnapshot listSnapshot: dataSnapshot.getChildren()) {
                   String reference = listSnapshot.getKey();
                   mReference.add(reference);

                   Object value = listSnapshot.getValue(Object.class);
                   mValue.add(value);
                   Integer task = listSnapshot.child("task").getValue(Integer.class);

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
                       mDescription.add(description);

                       String title = listSnapshot.child("title").getValue(String.class);
                       mTitle.add(title);

                       String firstLabel = listSnapshot.child("label").child("0").getValue(String.class);
                       String secondLabel = listSnapshot.child("label").child("1").getValue(String.class);
                       String thirdLabel = listSnapshot.child("label").child("2").getValue(String.class);
                       LinkedList <String> s = new LinkedList<>();
                       s.add(firstLabel);
                       s.add(secondLabel);
                       s.add(thirdLabel);
                       mLabel.add(s);

/*                   } else if (task != null && task == 1){
                        String repeatRotation  = listSnapshot.child("repeatRotation").getValue(String.class);
                        mRepeatRotation.add(repeatRotation);

                        Integer repeat  = listSnapshot.child("repeats").getValue(Integer.class);
                        mRepeats.add(repeat);

                        String firstLabel = listSnapshot.child("label").child("0").getValue(String.class);
                        String secondLabel = listSnapshot.child("label").child("1").getValue(String.class);
                        String thirdLabel = listSnapshot.child("label").child("2").getValue(String.class);
                        LinkedList <String> s = new LinkedList<>();
                        s.add(firstLabel);
                        s.add(secondLabel);
                        s.add(thirdLabel);
                        mLabel.add(s);

                    }*/

               }
               Log.d(TAG, "Value is: " + mValue);

                   _callback.setNotificationDeadlineData(mDay, mMonth, mYear, mHour, mMinute,mTitle);
                   _callback.setTitle(mTitle,mDay, mMonth, mYear);
                   _callback.setAll(mTitle,mDay,mMonth,mYear,mHour,mMinute,mTasks,mDescription,mReference,mLabel);

           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "Failed to read value.", error.toException());
           }
       });
   }


    /*private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = mRepeatRotation.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addChildEventListener(childListener);
    }*/


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

    public void removeDate(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Repository.getInstance().getUserId()).child(key);
        ref.removeValue();




    }
}
