package ir.madamas.kntushahed.kntushahed;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditPersonalInfo extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    EditText Tbox_username_edit;
    EditText TBox_password_edit;
    EditText TBox_name_edit;
    EditText TBox_family_edit;
    EditText TBox_stdID_edit;

    Button cancelBtn;
    Button confirmEdit;

    int backPressedCounter = 0;

    RequestQueue myrequestqueue;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        Tbox_username_edit = (EditText) findViewById(R.id.Tbox_username_edit);
        TBox_password_edit = (EditText) findViewById(R.id.TBox_password_edit);
        TBox_name_edit = (EditText) findViewById(R.id.TBox_name_edit);
        TBox_family_edit = (EditText) findViewById(R.id.TBox_family_edit);
        TBox_stdID_edit = (EditText) findViewById(R.id.TBox_stdID_edit);

        cancelBtn = (Button) findViewById(R.id.eCancel_button);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmEdit = (Button) findViewById(R.id.edit_button);

        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", TBox_name_edit.getText().toString().trim());
                editor.putString("family", TBox_family_edit.getText().toString().trim());
                editor.apply();

                registerUser(Tbox_username_edit.getText().toString().trim(), TBox_password_edit.getText().toString().trim(),TBox_name_edit.getText().toString().trim(), TBox_family_edit.getText().toString().trim());

            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Tbox_username_edit.setText(sharedPreferences.getString("Username",""));
        TBox_password_edit.setText(sharedPreferences.getString("Password",""));
        TBox_name_edit.setText(sharedPreferences.getString("name",""));
        TBox_family_edit.setText(sharedPreferences.getString("family",""));
        TBox_stdID_edit.setText(sharedPreferences.getString("schoolName",""));

    }

    @Override
    public void onBackPressed() {
        if (backPressedCounter == 1){
            finish();
        }
        Toast.makeText(getApplicationContext(), "با خروج از این صفحه تغییرات شما دخیره نخواهد شد!", Toast.LENGTH_SHORT).show();
        backPressedCounter++;
    }

    boolean flagTemp = false;

    private void registerUser(String u, String p, final String n, final String f) {

        myrequestqueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject student = new JSONObject();
        try {
            student.put("phoneNum", u);
            student.put("password", p);
            student.put("name", n);
            student.put("family", f);

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

                            if (flag.toString().startsWith("d")){
                                flagTemp = true;

                            }

                            if(flagTemp){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Registered", true);
                                editor.putString("name", n);
                                editor.putString("family", f);
                                editor.apply();
                                editor.apply();
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
}
