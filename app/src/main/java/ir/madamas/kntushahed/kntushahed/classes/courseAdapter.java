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

import java.util.ArrayList;
import java.util.List;
import ir.madamas.kntushahed.kntushahed.R;
/**
 * Created by MSF on 5/12/2017.
 */

public class courseAdapter extends ArrayAdapter {

   List list = new ArrayList();

    public courseAdapter( Context context, int resource) {
        super(context, resource);
    }


    public void add( course object) {
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public Object getItem(int position) {
        return  list.get(position);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View row ;
        row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_course, null, false);
        }

        course crs = (course) getItem(position);

        TextView tv_crsfName = (TextView) row.findViewById(R.id.tv_courseName);
        tv_crsfName.setText(crs.getCourseName());
        TextView tv_courseID = (TextView) row.findViewById(R.id.tv_coureID);
        tv_courseID.setText(crs.getCourseID());
        return row;
    }
}
