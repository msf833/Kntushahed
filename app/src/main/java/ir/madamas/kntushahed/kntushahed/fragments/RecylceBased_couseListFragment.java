package ir.madamas.kntushahed.kntushahed.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.madamas.kntushahed.kntushahed.R;
import ir.madamas.kntushahed.kntushahed.classes.RecycleAdapter;
import ir.madamas.kntushahed.kntushahed.classes.course;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecylceBased_couseListFragment extends Fragment {

RecyclerView recyclerView;
RecycleAdapter recycleAdapter;
    RequestQueue myrequestqueue;
    List<course> recievedCourseList;



    public RecylceBased_couseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_recylce_based_couse_list, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myrequestqueue = Volley.newRequestQueue(getContext());
        recievedCourseList = new ArrayList<>();

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

JSONObject jsob = new JSONObject();

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST,"http://api.mim-app.ir/SelectValue_coursesList.php" , jsob,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String courseID , courseName , courseImageUrl;
                        try {
                            JSONArray array = response.getJSONArray("lessons_list");
                            Log.i("request","on responsed listener");

                            for (int i =0;i<array.length();i++){
                                JSONObject jsontemp = array.getJSONObject(i);
                                Log.i("log"," courseName : "+jsontemp.getString("courseName") +"courseID "+jsontemp.getString("courseID"));
                                courseName = jsontemp.getString("courseName");
                                courseID =  jsontemp.getString("courseID");
                                courseImageUrl = jsontemp.getString("courseImageUrl");
                                course ocourse= new course(courseID,courseName,courseImageUrl);
                                recievedCourseList.add(ocourse);

                            }
                            //listView.setAdapter(cadapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recycleAdapter = new RecycleAdapter(recievedCourseList,getContext());
                        recyclerView.setAdapter(recycleAdapter);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "مشکل در دریافت داده از سرور ", Toast.LENGTH_SHORT).show();
                        Log.i("request","on error listener");
                    }
                }


        );
        myrequestqueue.add(jobrq);

    }







}
