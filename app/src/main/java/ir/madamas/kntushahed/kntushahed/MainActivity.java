package ir.madamas.kntushahed.kntushahed;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
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

import ir.madamas.kntushahed.kntushahed.fragments.coursesListFragment;
import ir.madamas.kntushahed.kntushahed.fragments.notificationFragment;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    int eCounter = 0;

    RequestQueue myrequestqueue;
    String tempMF;
    SharedPreferences sharedPreferences;


    FragmentManager fragmentManager ;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                   /// mTextMessage.setText(R.string.title_dashboard);
                    FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.content,new coursesListFragment());
                    frm.commit();
                    return true;

                case R.id.navigation_notifications:
                  //  mTextMessage.setText(R.string.title_notifications);
                    FragmentTransaction frm1 = fragmentManager.beginTransaction().replace(R.id.content,new notificationFragment());
                    frm1.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myrequestqueue = Volley.newRequestQueue(getApplicationContext());
         fragmentManager = getSupportFragmentManager();
        FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.content,new coursesListFragment());
        frm.commit();

      //  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = sharedPreferences.getString("name","کاربر");

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean Registered = sharedPreferences.getBoolean("Registered", false);

        if (Registered == false){
            Intent item_intent = new Intent(getApplicationContext(), SignupLoginActivity.class);
            startActivityForResult(item_intent, 1);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add){
            Intent intent = new Intent(getApplicationContext(), EditPersonalInfo.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (eCounter == 1){
            this.finishAffinity();

        }else {
            eCounter ++;
            Toast.makeText(getApplicationContext(), "برای خارج شدن از برنامه مجددا کلیک کنید", Toast.LENGTH_SHORT).show();
        }
    }
}
