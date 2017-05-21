package ir.madamas.kntushahed.kntushahed;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class ExtraCourseReq extends AppCompatActivity {
Button  btn_extracourseActivity ;
    SharedPreferences sharedPreferences;
    RequestQueue myrequestqueue;
    ProgressBar progressBar_ExtraCourse;
    boolean clicked = false;
    EditText editText_extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_course_req);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar_ExtraCourse = (ProgressBar) findViewById(R.id.progressBar_ExtraCourse);
        btn_extracourseActivity = (Button) findViewById(R.id.btn_extracourseActivity);
        editText_extra= (EditText) findViewById(R.id.editText_extra);
        myrequestqueue = Volley.newRequestQueue(getApplicationContext());
        btn_extracourseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar_ExtraCourse.setVisibility(View.VISIBLE);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                JSONObject courses = new JSONObject();
                btn_extracourseActivity.setEnabled(false);
                try {

                    // Toast.makeText(getContext(), Temp, Toast.LENGTH_SHORT).show();
                    courses.put("courseslist",editText_extra.getText().toString());
                    courses.put("studentID",sharedPreferences.getString("userID",""));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // String json = new Gson().toJson(selectedCourses);
                //sending request to server
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/InsertValue_courseListRequest_kntuShahed.php", courses,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray array = response.getJSONArray("courseBulkReserve");

                                    JSONObject jsontemp = array.getJSONObject(0);
                                    Log.i("log"," Bulk reserveanswer : "+ jsontemp.getString("flag"));

                                    Toast.makeText(getApplicationContext(), "درخواست شما ثبت شد ، با شما تماس خواهیم گرفت ", Toast.LENGTH_LONG).show();
                                    progressBar_ExtraCourse.setVisibility(View.GONE);
                                    btn_extracourseActivity.setEnabled(true);


                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }



                        finish();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "ثبت درخواست با مشکل مواجه شد ، بعدا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                                progressBar_ExtraCourse.setVisibility(View.GONE);
                            }
                        }


                );
                myrequestqueue.add(jsonObjectRequest);
            }
        });
    }

}