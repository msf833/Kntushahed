package ir.madamas.kntushahed.kntushahed.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.madamas.kntushahed.kntushahed.R;
import ir.madamas.kntushahed.kntushahed.classes.course;
import ir.madamas.kntushahed.kntushahed.classes.courseAdapter;
import ir.madamas.kntushahed.kntushahed.classes.notification;
import ir.madamas.kntushahed.kntushahed.classes.notificationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class notificationFragment extends Fragment {
    SharedPreferences sharedPreferences;
    RequestQueue myrequestqueue;
    ListView listView;
    ProgressBar notification_progressBar;
    notificationAdapter nadapter;
    public notificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View notificationFragmentView =  inflater.inflate(R.layout.fragment_notification, container, false);

        // Inflate the layout for this fragment
        return notificationFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notification_progressBar = (ProgressBar) getView().findViewById(R.id. notification_progressBar);
        listView = (ListView) getView().findViewById(R.id.listview_notifi);
        nadapter = new notificationAdapter(getContext(),R.layout.row_notification);
        myrequestqueue = Volley.newRequestQueue(getContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        notification_progressBar.setVisibility(View.VISIBLE);
        JSONObject getNoti = new JSONObject();
        try {
            // Toast.makeText(getContext(), Temp, Toast.LENGTH_SHORT).show();

            getNoti.put("studentID",sharedPreferences.getString("stdID",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST,"http://api.mim-app.ir/select_notification_kntushahed.php" , getNoti,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String maincontent , title , timedate;
                        try {
                            JSONArray array = response.getJSONArray("eventList");


                            for (int i =0;i<array.length();i++){
                                JSONObject jsontemp = array.getJSONObject(i);
                               // Log.i("log"," courseName : "+jsontemp.getString("courseName") +"courseID "+jsontemp.getString("courseID"));
                                maincontent = jsontemp.getString("mainContent");
                                title =  jsontemp.getString("title");
                                timedate =jsontemp.getString("dateTime");
                                Log.i("notifrag","main : "+maincontent + "title : "+title + "timedate : "+timedate);
                                ir.madamas.kntushahed.kntushahed.classes.notification notif= new notification(maincontent,title,timedate);
                                nadapter.add(notif);

                            }
                            listView.setAdapter(nadapter);
                            notification_progressBar.setVisibility(View.GONE);

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
