package at.fhooe.mc.android;



import android.content.SharedPreferences;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LableList {
    private static final String VALUE_KEY = "LabelList";
    private static final Object SP_KEY = "foo";

    private class Node{
        Node next;
        /** Ref to the prev elem in the list, or null if it is the first */
         String label;
    }
    Node root;
    private  int size;

    void addLable(String _label){
        Node n = new Node();
        n.label = _label;
        if(root == null){
            root = n;
        }
        Node r = root;
        while(r.next !=null) {
            r = r.next;
        }
        r.next = n;
        size++;
    }
    void removeLable(String _lable){

        Node p = root;
        Node n = root;
        while(!_lable.equals(n.label)){
            p=n;
            n=n.next;
        }
        p.next = n.next;
        size--;
    }

    public int getSize(){
        return size;
    }
}
