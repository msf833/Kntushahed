package ir.madamas.kntushahed.kntushahed.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import ir.madamas.kntushahed.kntushahed.classes.courseAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ir.madamas.kntushahed.kntushahed.classes.course;
import ir.madamas.kntushahed.kntushahed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class coursesListFragment extends Fragment {

    RequestQueue myrequestqueue;
    String tempMF;
    courseAdapter cadapter;
    ListView listView;
    GridView gridView;

    public coursesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses_list, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        JSONObject jsob = new JSONObject();
        cadapter = new courseAdapter(getContext(),R.layout.row_course);
       // listView = (ListView) getView().findViewById(R.id.listView_fragment_courseList);
        gridView = (GridView)  getView().findViewById(R.id.gridView_fragment);

        myrequestqueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/SelectValue_coursesList.php", jsob,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String courseID , courseName;
                        try {
                            JSONArray array = response.getJSONArray("lessons_list");


                            for (int i =0;i<array.length();i++){
                                JSONObject jsontemp = array.getJSONObject(i);
                                Log.i("log"," courseName : "+jsontemp.getString("courseName") +"courseID "+jsontemp.getString("courseID"));
                                courseName = jsontemp.getString("courseName");
                                courseID =  jsontemp.getString("courseID");
                                course ocourse= new course(courseID,courseName);
                               cadapter.add(ocourse);

                            }
                            //listView.setAdapter(cadapter);
                            gridView.setAdapter(cadapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }


        );
        myrequestqueue.add(jobrq);

    }
}
