package ir.madamas.kntushahed.kntushahed.classes;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.madamas.kntushahed.kntushahed.R;
import ir.madamas.kntushahed.kntushahed.Statics.staticFields;

/**
 * Created by MSF on 5/27/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.courseHolder> {

    List<course> courses ;
    Context context;

    public RecycleAdapter(List<course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    @Override
    public courseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_course,parent,false);
        return new courseHolder(view);
    }

    @Override
    public void onBindViewHolder(courseHolder holder, int position) {

        holder.tv_crsfName.setText(courses.get(position).getCourseName());
        holder.tv_courseID.setText(courses.get(position).getCourseID());

        if(courses.get(position).getCourseImageUrl().equals("")){

        }
        else {

            Picasso.with(context).load(courses.get(position).getCourseImageUrl()).into( holder.IV_goodtpic);
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class courseHolder extends RecyclerView.ViewHolder {
        TextView tv_crsfName;
        RelativeLayout relativeLayout;
        TextView tv_courseID;
        ImageView IV_goodtpic;

        public courseHolder(View itemView) {
            super(itemView);
            tv_crsfName = (TextView) itemView.findViewById(R.id.tv_courseName);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.background_layout);
            tv_courseID = (TextView) itemView.findViewById(R.id.tv_coureID);
            IV_goodtpic = (ImageView) itemView.findViewById(R.id.iv_row_course);

           relativeLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (staticFields.selectedCourses.contains(tv_crsfName.getText().toString()) ){

                       //tv_courseName.setBackgroundColor(Color.WHITE);
                       relativeLayout.setBackgroundColor(Color.parseColor("#fffdf5"));

                       staticFields.selectedCourses.remove(tv_crsfName.getText().toString());

                   }else {
                       // tv_courseName.setBackgroundColor(Color.RED);
                       relativeLayout.setBackgroundColor(Color.parseColor("#FFFFECB8"));
                       staticFields.selectedCourses.add(tv_crsfName.getText().toString());
                   }
               }
           });



        }
    }
}
