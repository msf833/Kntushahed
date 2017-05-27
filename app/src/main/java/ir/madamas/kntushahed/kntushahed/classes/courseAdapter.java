package ir.madamas.kntushahed.kntushahed.classes;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import ir.madamas.kntushahed.kntushahed.R;
/**
 * Created by MSF on 5/12/2017.
 */

public class courseAdapter extends ArrayAdapter {

    ArrayList<course> list = new ArrayList();
    ArrayList<course> filerlist = new ArrayList();
    customFilter filter;
    public courseAdapter( Context context, int resource) {
        super(context, resource);
    }


    public void add( course object) {
        filerlist.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public course getItem(int position) {
        return  list.get(position);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        View row ;
        row = convertView;
     ;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_course, parent,false);
        }

        course crs = getItem(position);

        TextView tv_crsfName = (TextView) row.findViewById(R.id.tv_courseName);
        tv_crsfName.setText(crs.getCourseName());
        RelativeLayout relativeLayout = (RelativeLayout) row.findViewById(R.id.background_layout);
        TextView tv_courseID = (TextView) row.findViewById(R.id.tv_coureID);
        tv_courseID.setText(crs.getCourseID());

        String imageurl = crs.getCourseImageUrl();
        if(imageurl.equals("")){

        }
        else {
            ImageView IV_goodtpic = (ImageView) row.findViewById(R.id.iv_row_course);
            Picasso.with(getContext()).load(imageurl).into(IV_goodtpic);
        }
            if (crs.isSelected()){
                relativeLayout.setBackgroundColor(Color.parseColor("#FFFFECB8"));
             //  this.notifyDataSetChanged();
            }else{
                relativeLayout.setBackgroundColor(Color.WHITE);
                //this.notifyDataSetChanged();
            }
        return row;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null ){
            filter = new customFilter();
        }
        return filter;
    }

    class customFilter extends Filter{

       @Override
       protected FilterResults performFiltering(CharSequence constraint) {

           FilterResults results = new FilterResults();
           ArrayList<course> filters = new ArrayList<>();


           if ( constraint != null && constraint.length() > 0 ){

               constraint = constraint.toString().toUpperCase();

               for (int i =0 ; i<filerlist.size();i++){
                   if (filerlist.get(i).getCourseName().toUpperCase().contains(constraint)){
                       course c = new course(filerlist.get(i).getCourseID(),filerlist.get(i).getCourseName(),filerlist.get(i).getCourseImageUrl());
                       filters.add(c);
                   }
               }
               results.count = filters.size();
               results.values = filters;
          }else {
               results.count = filerlist.size();
               results.values = filerlist;
           }


           return results;
       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {
           list = (ArrayList<course>) results.values;
           notifyDataSetChanged();
       }
   }

}
