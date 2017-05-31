package ir.madamas.kntushahed.kntushahed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.madamas.kntushahed.kntushahed.classes.course;

import static android.Manifest.permission.READ_CONTACTS;
import static android.accounts.AccountManager.KEY_PASSWORD;

/**
 * A login screen that offers login via email/password.
 */
public class SignupLoginActivity extends AppCompatActivity {
    //stored data
    SharedPreferences sharedPreferences;

    // UI references.
    private EditText username_signup;
    private EditText Password_signup;
    private EditText name_signup;
    private EditText family_signup;
    private EditText username_login;
    private EditText Password_login;
    private EditText stdID_signup;
    private ProgressBar ProgressView;

    //exit counter
    int eCounter = 0;

    //volley RequestQueue
    RequestQueue myrequestqueue;
    String flag;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Animation anim_m_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_right);
        Animation anim_m_left = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_left);

        //progressBar
        ProgressView = (ProgressBar) findViewById(R.id.login_progress);

        // Set up the login form.
        username_signup = (EditText) findViewById(R.id.Tbox_username_signup);
        Password_signup = (EditText) findViewById(R.id.TBox_password_signup);
        username_login = (EditText) findViewById(R.id.TBox_username_login);
        Password_login = (EditText) findViewById(R.id.TBox_password_login);
        name_signup = (EditText) findViewById(R.id.TBox_name_signup);
        family_signup = (EditText) findViewById(R.id.TBox_family_signup);
        stdID_signup = (EditText) findViewById(R.id.TBox_stdID_signup);

        Button signun_button = (Button) findViewById(R.id.signun_button);
        Button login_button = (Button) findViewById(R.id.login_button);

        //mLoginFormView = findViewById(R.id.login_form);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.startAnimation(anim_m_left);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("ورود");
        spec.setContent(R.id.tab2);
        spec.setIndicator("ورود");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("ثبت نام");
        spec.setContent(R.id.tab1);
        spec.setIndicator("ثبت نام");
        host.addTab(spec);

        signun_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressView.setVisibility(View.VISIBLE);
                String Txt_username_signup = username_signup.getText().toString();
                String Txt_Password_signup = Password_signup.getText().toString();
                String Txt_name_signup = name_signup.getText().toString();
                String Txt_family_signup = family_signup.getText().toString();
                String Txt_stdID_signup = stdID_signup.getText().toString();

                if (isUsernameValid(Txt_username_signup) == true &&
                        isPasswordValid(Txt_Password_signup) == true &&
                        isNameFamilyValid(Txt_name_signup) == true &&
                        isNameFamilyValid(Txt_family_signup) == true &&
                        isStdIDValid(Txt_stdID_signup) == true) {

                        registerUser(Txt_username_signup, Txt_Password_signup, Txt_name_signup, Txt_family_signup, Txt_stdID_signup);

                }

                if (!isUsernameValid(Txt_username_signup)){
                    username_signup.setError("شماره تلفن وارد شده معتبر نیست!");
                    ProgressView.setVisibility(View.GONE);
                }

                if (!isPasswordValid(Txt_Password_signup)){
                    Password_signup.setError("پسورد قویتری انتخاب کنید!");
                    ProgressView.setVisibility(View.GONE);
                }

                if (!isNameFamilyValid(Txt_name_signup)){
                    name_signup.setError("نام وارد شده صحیح نیست!");
                    ProgressView.setVisibility(View.GONE);
                }

                if (!isNameFamilyValid(Txt_family_signup)){
                    family_signup.setError("نام وارد شده صحیح نیست!");
                    ProgressView.setVisibility(View.GONE);
                }

                if (!isStdIDValid(Txt_stdID_signup)){
                    stdID_signup.setError("شماره دانشجویی باید بین 7 تا 15 رقم باشد!");
                    ProgressView.setVisibility(View.GONE);
                }

            }
        });

        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressView.setVisibility(View.VISIBLE);
                String Txt_username_login = username_login.getText().toString();
                String Txt_Password_login = Password_login.getText().toString();

                if (isUsernameValid(Txt_username_login) == true &&
                        isPasswordValid(Txt_Password_login) == true) {

                    loginUser(Txt_username_login, Txt_Password_login);

                }

                if (!isUsernameValid(Txt_username_login)){
                    username_login.setError("شماره تلفن وارد شده معتبر نیست!");
                    ProgressView.setVisibility(View.GONE);
                }

                if (!isPasswordValid(Txt_Password_login)){
                    Password_login.setError("پسورد وارد شده صیح نیست!");
                    ProgressView.setVisibility(View.GONE);
                }
            }
        });

    }

    boolean flagTemp = false;

    private void registerUser(final String u, final String p, final String n, final String f, final String s) {

        myrequestqueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject student = new JSONObject();
        try {
            student.put("phoneNum", u);
            student.put("password", p);
            student.put("name", n);
            student.put("family", f);
            student.put("stdID", s);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/InsertValue_SignupActivity.php", student,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray array = response.getJSONArray("search_resualt");

                            JSONObject jsontemp = array.getJSONObject(0);

                            flag = jsontemp.getString("flag");
                            userID = jsontemp.getString("studentID");
                            Toast.makeText(getApplicationContext(), "userID: " + userID, Toast.LENGTH_SHORT).show();

                            if (flag.toString().startsWith("d")){

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Registered", true);
                                editor.putString("Username", u);
                                editor.putString("Password", p);
                                editor.putString("name", n);
                                editor.putString("family", f);
                                editor.putString("stdID", s);
                                editor.putString("userID", userID);
                                editor.apply();
                                Intent item_intent = new Intent(getApplicationContext(), splash.class);
                                startActivity(item_intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), "something went wrong :(", Toast.LENGTH_SHORT).show();
                            }

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

    private void loginUser(final String u, final String p) {

        myrequestqueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject student = new JSONObject();
        try {
            student.put("phoneNum", u);
            student.put("password", p);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/SelectValue_LoginActivity.php", student,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String n = "";
                        String f = "";
                        String s = "";
                        try {
                            JSONArray array = response.getJSONArray("search_resualt");
                            JSONObject jsontemp = array.getJSONObject(0);

                            flag = jsontemp.getString("flag");

                            if (flag.toString().startsWith("d")){

                                userID = jsontemp.getString("studentID");
                                n = jsontemp.getString("name");
                                f = jsontemp.getString("family");
                                s = jsontemp.getString("stdID");

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Registered", true);
                                editor.putString("Username", u);
                                editor.putString("Password", p);
                                editor.putString("name", n);
                                editor.putString("family", f);
                                editor.putString("stdID", s);
                                editor.putString("userID", userID);
                                editor.apply();
                                Intent item_intent = new Intent(getApplicationContext(), splash.class);
                                startActivity(item_intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), "something went wrong :(", Toast.LENGTH_SHORT).show();
                                ProgressView.setVisibility(View.GONE);
                            }


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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


    private boolean isUsernameValid(String phoneNum) {
        if (phoneNum.startsWith("98") || phoneNum.startsWith("+98") ||phoneNum.startsWith("0")){
            return false;
        }

        if (phoneNum.startsWith("9")){

            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() > 4){

            return true;
        }
        return false;
    }

    private boolean isNameFamilyValid(String family) {
        if (family.length() > 2){

            return true;
        }
        return false;
    }

    private boolean isStdIDValid(String stdID) {
        if (stdID.length() < 15 && stdID.length() >= 7){
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        if (eCounter == 1){

            this.finishAffinity();

        }else {
            eCounter ++;
            Toast.makeText(getApplicationContext(), "برای خارج شدن از برنامه مجددا کلیک کنید", Toast.LENGTH_SHORT).show();
        }

    }
}

