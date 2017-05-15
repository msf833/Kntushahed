package ir.madamas.kntushahed.kntushahed.classes;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ir.madamas.kntushahed.kntushahed.*;
import java.util.ArrayList;
import java.util.List;

import ir.madamas.kntushahed.kntushahed.R;

/**
 * Created by MSF on 5/15/2017.
 */

public class notificationAdapter extends ArrayAdapter {

    List list = new ArrayList();
    public notificationAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row ;
        row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_notification, null, false);
        }

        notification noti = (notification) getItem(position);

        TextView tv_notititle = (TextView) row.findViewById(R.id.tv_notititle);
        tv_notititle.setText(noti.getTitle());
        TextView tv_maincontent = (TextView) row.findViewById(R.id.tv_maincontent);
        tv_maincontent.setText(noti.getMaincontent());
        tv_maincontent.setEnabled(false);
        TextView tv_datetime = (TextView) row.findViewById(R.id.tv_datetime);
        tv_datetime.setText(noti.getTimedate());
        return row;
    }
}
