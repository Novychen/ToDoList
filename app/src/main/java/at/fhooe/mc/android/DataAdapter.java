package at.fhooe.mc.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class DataAdapter extends ArrayAdapter<ListData> {
    public DataAdapter(Context _c) {
        super(_c, -1);
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        if(_convertView == null){ // wenn keine View für Listeneintrag erstellt wurde wo wir was reinspeichern könnnten
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = inflater.inflate(R.layout.activity_list,null);
        }
        ListData data = getItem(_position);
        TextView tv = null;
        tv = (TextView) _convertView.findViewById(R.id.activity_list_titel);
        tv.setText(data.getmTitle());

        tv = (TextView) _convertView.findViewById(R.id.activity_list_date);
        tv.setText( data.getMdate());
        return _convertView;
    }
}
