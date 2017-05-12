package ir.madamas.kntushahed.kntushahed;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignupLoginActivity extends AppCompatActivity {

    // UI references.
    private EditText username_signup;
    private EditText Password_signup;
    private EditText name_signup;
    private EditText family_signup;
    private EditText username_login;
    private EditText Password_login;
    private EditText stdID_signup;
    private ProgressBar ProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

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
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("ثبت نام");
        spec.setContent(R.id.tab1);
        spec.setIndicator("ثبت نام");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("ورود");
        spec.setContent(R.id.tab2);
        spec.setIndicator("ورود");
        host.addTab(spec);

        signun_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
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



                }

            }
        });

        login_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String Txt_username_login = username_login.getText().toString();
                String Txt_Password_login = Password_login.getText().toString();
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


    private boolean isUsernameValid(String phoneNum) {
        if (phoneNum.startsWith("9")){

            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String name) {
        if (name.length() > 4){

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
        /*if (stdID.length() > 2){

            return true;
        }*/
        return false;
    }

}

