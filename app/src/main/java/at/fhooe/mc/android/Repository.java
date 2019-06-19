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
    private static List<Object> mValue = new LinkedList<>();

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
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               for (DataSnapshot listSnapshot: dataSnapshot.getChildren()) {
                   mValue.add(listSnapshot.getValue(Object.class));
               }
               Log.d(TAG, "Value is: " + mValue);
               if(mValue != null){
                   _callback.setData(mValue);
               }
           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
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

}
