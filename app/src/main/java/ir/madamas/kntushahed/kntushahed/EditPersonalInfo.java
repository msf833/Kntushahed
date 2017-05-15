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
    Button logout_button;

    int backPressedCounter = 0;

    RequestQueue myrequestqueue;
    String flag;
    String userID;

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

        //logout_button
        logout_button = (Button) findViewById(R.id.logout_button);


        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser(TBox_name_edit.getText().toString(), TBox_family_edit.getText().toString());

            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Tbox_username_edit.setText(sharedPreferences.getString("Username",""));
        TBox_password_edit.setText(sharedPreferences.getString("Password",""));
        TBox_name_edit.setText(sharedPreferences.getString("name",""));
        TBox_family_edit.setText(sharedPreferences.getString("family",""));
        TBox_stdID_edit.setText(sharedPreferences.getString("stdID",""));
        userID = sharedPreferences.getString("userID","");
        if (userID.length() < 2){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("Registered", false);
            editor.apply();
            finish();
        }

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Registered", false);
                editor.apply();
                finish();
            }
        });

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

    private void registerUser(final String n, final String f) {

        myrequestqueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject student = new JSONObject();
        try {

            student.put("userID", userID);
            student.put("name", n);
            student.put("family", f);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/UpdateValue_EditInfoActivity.php", student,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray array = response.getJSONArray("search_resualt");

                            JSONObject jsontemp = array.getJSONObject(0);

                            flag = jsontemp.getString("flag");

                            if (flag.toString().startsWith("d")){
                                flagTemp = true;
                                Toast.makeText(getApplicationContext(), "flag: " + flag.toString(), Toast.LENGTH_SHORT).show();

                            }else if (flag.toString().startsWith("u")){
                                flagTemp = false;
                                Toast.makeText(getApplicationContext(), "flag: " + flag.toString(), Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("Registered", false);
                                editor.apply();
                                finish();
                            }

                            if(flagTemp){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", n);
                                editor.putString("family", f);
                                editor.apply();
                                Toast.makeText(getApplicationContext(), "تغییرات شما ذخیره شد :)", Toast.LENGTH_SHORT).show();
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
