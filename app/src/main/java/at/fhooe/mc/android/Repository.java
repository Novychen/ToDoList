package at.fhooe.mc.android;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Repository {

    private static final String TAG = "Repository";
    private static Repository mInstance;
    private static String mUserId;
    private Object mValue;

    protected static Repository getInstance(){
        if(mInstance == null){
            mInstance = new Repository();
        }
        return mInstance;
    }

    private Repository(){
    }

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

    protected String getStringData(DatabaseReference _myRef){
        try {
        return (String) getData(_myRef);
        }catch(NullPointerException e){
            return null;
        }catch(ClassCastException e){
            return null;
        }
    }

    protected Task getTaskData(DatabaseReference _myRef){
        try {
        return (Task) getData(_myRef);
        }catch(NullPointerException e){
            return null;
        }catch(ClassCastException e){
            return null;
        }
    }

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

    protected void saveData(Task _t,long _taskNumber){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String ref = mUserId + "/" + "Task " + _taskNumber;
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(ref);
        reference.setValue(_t);
    }

    protected void saveData(long _currTaskNumber){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         String ref = mUserId + "/CurrentTask";
        if (mUserId == null){
            return;
        }
        DatabaseReference reference = database.getReference(ref);
        reference.setValue(_currTaskNumber);
    }

    protected void setUserId(String _user){
        mUserId = _user;
    }

    protected String getUserId(){
        return mUserId;
    }

}
