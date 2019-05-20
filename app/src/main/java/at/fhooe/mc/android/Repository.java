package at.fhooe.mc.android;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {

    private static Repository mInstance;
    private String mUserId;

    public static Repository getInstance(){
        if(mInstance == null){
            mInstance = new Repository();
        }
        return mInstance;
    }

    private Repository(){
    }

    public void saveData(Object _o){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(mUserId);
        reference.setValue(_o);

    }

    public void getUserId(String _user){
        mUserId = _user;
    }

}
