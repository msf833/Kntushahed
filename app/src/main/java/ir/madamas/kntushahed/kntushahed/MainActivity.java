package ir.madamas.kntushahed.kntushahed;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    RequestQueue myrequestqueue;
    String tempMF;

JSONObject jobg = new  JSONObject();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                /*
                case R.id.navigation_dashboard:
                mTextMessage.setText(R.string.title_dashboard);
                return true;
                */
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myrequestqueue = Volley.newRequestQueue(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button btn_getData = (Button) findViewById(R.id.btn_getData);
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
                                    Toast.makeText(MainActivity.this, "on responsed", Toast.LENGTH_SHORT).show();
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
