package ir.madamas.kntushahed.kntushahed.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.madamas.kntushahed.kntushahed.MainActivity;
import ir.madamas.kntushahed.kntushahed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class coursesListFragment extends Fragment {

    RequestQueue myrequestqueue;
    String tempMF;
    Button btn_getData;

    JSONObject jobg = new  JSONObject();

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

        myrequestqueue = Volley.newRequestQueue(getContext());
        btn_getData = (Button)getView().findViewById(R.id.btn_getData);

        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MSF","btn request clicked");
                JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/SelectValue_profList.php",jobg,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray array = response.getJSONArray("prof_list");
                                    Toast.makeText(getContext(), "on responsed", Toast.LENGTH_SHORT).show();
                                    for (int i =0 ; i < array.length();i++){
                                        JSONObject temp = array.getJSONObject(i);

                                        tempMF  = temp.getString("ProfessorsID");
                                    }

                                    Log.i("resp",tempMF);
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
                myrequestqueue.add(JsonRequest);
            }
        });
    }

}
