package at.fhooe.mc.android;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This class (Singelton Pattern) implements all methods that are connected to the database such as {@link Repository#getData(DatabaseReference)} or {@link Repository#saveData(Task, long)}.
 */
public class Repository {

    private static final String TAG = "Repository";
    private static Repository mInstance;
    private static String mUserId;
    private Object mValue;

    /**
     * Constructor for Repository
     * @return Repository Object
     */
    protected static Repository getInstance(){
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
    protected void getLongData(DatabaseReference _myRef){
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
     * gets String values from the database
     * @param _myRef the reference (path) of the String-value (in the database) that want to be fetched
     * @return the fetched String from the database
     */
    protected String getStringData(DatabaseReference _myRef){
        try {
        return (String) getData(_myRef);
        }catch(NullPointerException e){
            return null;
        }catch(ClassCastException e){
            return null;
        }
    }

    /**
     * gets Task-Objects from the database
     * @param _myRef the reference (path) of the Task object (in the database) that want to be fetched
     * @return the fetched Task object from the database
     */
    protected Task getTaskData(DatabaseReference _myRef){
        try {
        return (Task) getData(_myRef);
        }catch(NullPointerException e){
            return null;
        }catch(ClassCastException e){
            return null;
        }
    }

    /**
     * gets data from the database
     * @param _myRef the reference (path) of the object (in the database) that want to be fetched
     * @return the object that is fetched from the database
     */
   protected Object getData(DatabaseReference _myRef){
       _myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               mValue = dataSnapshot.getValue(Object.class);
               Log.i(TAG, "Value is: " + mValue);
           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.i(TAG, "Failed to read value.", error.toException());
           }
       });
       return mValue;
   }

    /**
     * saves a Task along with its tasknumber into the database
     * @param _t the task, that is saved
     * @param _taskNumber the number of the task, that is saved
     */
    protected void saveData(Task _t,long _taskNumber){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String ref = mUserId + "/" + "Task " + _taskNumber;
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(ref);
        reference.setValue(_t);
    }

    /**
     * saves the current TaskNumber into the database
     * @param _currTaskNumber the number that represents the current Task
     */
    protected void saveData(long _currTaskNumber){
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
    protected void setUserId(String _user){
        mUserId = _user;
    }

    /**
     * getter for the {@link Repository#mUserId}, which saves who is logged in
     * @return mUserId, the ID of the currentUser
     */
    protected String getUserId(){
        return mUserId;
    }

}
