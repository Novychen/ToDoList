package at.fhooe.mc.android;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {

    private static Repository mInstance;
    private String mUserId;

    protected static Repository getInstance(){
        if(mInstance == null){
            mInstance = new Repository();
        }
        return mInstance;
    }

    private Repository(){
    }

    protected void saveData(Object _o,int _taskNumber){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String ref = mUserId + "/" + "Task " + _taskNumber;
        DatabaseReference reference = database.getReference(ref);
        reference.setValue(_o);

    }

    protected void getUserId(String _user){
        mUserId = _user;
    }

}
