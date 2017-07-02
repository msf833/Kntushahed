package ir.madamas.kntushahed.kntushahed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import ir.madamas.kntushahed.kntushahed.Statics.attributes;
import ir.madamas.kntushahed.kntushahed.fragments.coursesListFragment;
import ir.madamas.kntushahed.kntushahed.fragments.notificationFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.drawer_layout);

        String token = FirebaseInstanceId.getInstance().getToken();
        //Log.i("this is the token: ", token);

        Toolbar toolbar;

        if(Build.VERSION.SDK_INT > 19) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }else {
            toolbar = (Toolbar) findViewById(R.id.toolbar1);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //done with making navigation drawer :)
        /*--------------------------------------------------------------------------------------------------------------*/

        myrequestqueue = Volley.newRequestQueue(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.content,new coursesListFragment());
        frm.commit();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                checkupdate();
            }
        });
        th.run();
      //  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = sharedPreferences.getString("name","کاربر");

    }

    static boolean flag = true;

    @Override
    protected void onResume() {
        super.onResume();
        boolean Registered = sharedPreferences.getBoolean("Registered", false);
        if (Registered == false){
            Intent item_intent = new Intent(getApplicationContext(), SignupLoginActivity.class);
            startActivity(item_intent);
        }

        if (flag) {
            Intent item_intent = new Intent(getApplicationContext(), splash.class);
            startActivity(item_intent);
            flag = false;
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
        if (id == R.id.otherlesson){
            Intent intent = new Intent(getApplicationContext(), ExtraCourseReq.class);
          startActivity(intent);


            return true;
        }
        if (id == R.id.about_menuBTN){
            Intent intent = new Intent(getApplicationContext(), about.class);
          startActivity(intent);


            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    void checkupdate(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final   String DownloadupdateLink = "http://api.mim-app.ir/kntuShahedApp/kntushahed.apk";
        StringRequest sr = new StringRequest(Request.Method.POST, "http://api.mim-app.ir/app_update.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int serverversion  = Integer.parseInt(response);
                if (attributes.version < serverversion){
                    Toast.makeText(getApplicationContext(), "بروز رسانی نرم افزار", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"from servr rercived : "+ response.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(DownloadupdateLink));
                    startActivity(intent);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "مشکل در بروز رسانی نرم افزار ", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(sr);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (eCounter == 1){
            this.finishAffinity();

        }else {
            eCounter ++;
        }

    }

    //here is onItemClickOption for navigation menu :)
    /*--------------------------------------------------------------------------------------------------------------*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent intent = new Intent(getApplicationContext(), EditPersonalInfo.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_newCourse) {

            Intent intent = new Intent(getApplicationContext(), ExtraCourseReq.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_booklet) {
// ---------- To send sms to a phone number using user device :)
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("+980000000", null, "sms msg", null, null);
            //Toast.makeText(getApplicationContext(), "Not working yet :)", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), bookletActivity.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_aboutUs) {

            Intent intent = new Intent(getApplicationContext(), about.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_share) {


                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "دانشجوی عزیز، با سلام\n" +
                            "با توجه به نیاز دیده شده برای درخواست و هماهنگی کلاسهای تقویتی شاهد و ایثارگران برنامه اندرویدی زیر با حمایت استاد بطحایی نوشته و آماده استفاده می باشد.\n" +
                            "http://madamas.ir/kntushahed.apk\n" +
                            "دانشجویان شاهد از این پس میتوانند جهت درخواست کلاس از طریق این برنامه اقدام کنند.\n" +
                            "درصورت هرگونه مشکل یا نیاز به راهنمایی با ایمیل \n" +
                            "info@madamas.ir\n" +
                            "در تماس باشید\n" +
                            "با آرزوی موفقیت شما\n" +
                            "طرح شاهد اوج _ دانشکده برق و کامپیوتر";
                    String shareSub = "طرح شاهد اوج";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));

        } else if (id == R.id.nav_update) {

            //-------------------------------------------------
            //here is a code to make local notification :)
            /*
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivities(getApplicationContext(), 1, new Intent[]{intent}, 0);
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("بررسی برای به روز رسانی..")
                    .setContentText("شما از آخرین نسخه برنامه استفاده می کنید :)")
                    .setContentIntent(pendingIntent)
                    .addAction(android.R.drawable.arrow_down_float, "صفحه اصلی برنامه", pendingIntent)
                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
            */
        return true;

    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
