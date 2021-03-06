package ir.madamas.kntushahed.kntushahed.fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
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

import java.util.ArrayList;

import ir.madamas.kntushahed.kntushahed.R;
import ir.madamas.kntushahed.kntushahed.classes.course;
import ir.madamas.kntushahed.kntushahed.classes.courseAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class coursesListFragment extends Fragment {

    int courseSelectedCounter = 0;
    TextView textView4;
    Button btn_sendReq;
    SharedPreferences sharedPreferences;
    RequestQueue myrequestqueue;
    String tempMF;
    courseAdapter cadapter;
    ListView listView;
    GridView gridView;
    ArrayList<String> selectedCourses;
    SearchView searchView;
    public coursesListFragment() {
        // Required empty public constructor
    }

    ProgressBar progressBar_courseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses_list, container, false);
        progressBar_courseList = (ProgressBar) view.getRootView().findViewById(R.id.progressBar_courseList);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        //

        String name = sharedPreferences.getString("name", "کاربر");
        TextView navUserName = (TextView) header.findViewById(R.id.usernameNavbar);
        name += " عزیز وقت بخیر";
        navUserName.setText(name);

        TextView userIDNavBar = (TextView) header.findViewById(R.id.userIDNavBar);
        userIDNavBar.setText(sharedPreferences.getString("stdID", ""));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        JSONObject jsob = new JSONObject();
        Animation anim_m_right = AnimationUtils.loadAnimation(getContext(),R.anim.move_right);
        Animation anim_m_left = AnimationUtils.loadAnimation(getContext(),R.anim.move_left);

        textView4 = (TextView) getView().findViewById(R.id.textView4);

        selectedCourses = new ArrayList<>();
        cadapter = new courseAdapter(getContext(),R.layout.row_course);
        searchView = (SearchView) getView().findViewById(R.id.searchView);
        searchView.setQueryHint("جستجو...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cadapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cadapter.getFilter().filter(newText);
                cadapter.notifyDataSetChanged();
                return false;
            }
        });

       //listView = (ListView) getView().findViewById(R.id.listView_fragment_courseList);
       gridView = (GridView)  getView().findViewById(R.id.gridView_fragment);

        gridView.setAnimation(anim_m_left);
        gridView.startAnimation(anim_m_right);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(anim_m_right, .2f, .2f);
        gridView.setLayoutAnimation(controller);
//      gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
//          @Override
//          public void onScrollStateChanged(AbsListView view, int scrollState) {
//              Toast.makeText(getContext(), "yeah mother fucker", Toast.LENGTH_SHORT).show();
//          }
//
//          @Override
//          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//          }
//      });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                TextView tv_courseName = (TextView) view.findViewById(R.id.tv_courseName);
                String stv_courseName =tv_courseName.getText().toString().trim();
                TextView tv_coureID = (TextView) view.findViewById(R.id.tv_coureID);
                String stv_coureID  =tv_coureID.getText().toString();
                Log.i("position",position +"");
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.background_layout);



                if (selectedCourses.contains(stv_courseName) ){

                    selectedCourses.remove(stv_courseName);
                    cadapter.getItem(position).setSelected(false);
                    cadapter.getItemFilter(position).setSelected(false);

                    if (selectedCourses.size() == 0){
                        btn_sendReq.setVisibility(View.GONE);
                    }

                }else {
                  // tv_courseName.setBackgroundColor(Color.RED);
                   // relativeLayout.setBackgroundColor(Color.parseColor("#FFFFECB8"));
                    selectedCourses.add(stv_courseName);
                    cadapter.getItem(position).setSelected(true);
                    cadapter.getItemFilter(position).setSelected(true);

                    if (selectedCourses.size()  > 0){
                        btn_sendReq.setVisibility(View.VISIBLE);
                    }
                }

                cadapter.notifyDataSetChanged();
                Log.i("log",selectedCourses.toString());
            }
        });


//        NavigationView navigationView = (NavigationView) getView().findViewById(R.id.nav_view);
//        View hView =  navigationView.getHeaderView(0);



        btn_sendReq = (Button) getView().findViewById(R.id.btn_getData);
        btn_sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar_courseList.setVisibility(View.VISIBLE);


                AlertDialog.Builder alert_builder = new AlertDialog.Builder(getContext());
                alert_builder.setIcon(R.drawable.ic_notifications_black_24dp);

                alert_builder.setTitle("لیست دروس انتخاب شده : ");
                alert_builder.setMessage(selectedCourses.toString() );
                alert_builder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String Temp="";
                        btn_sendReq.setEnabled(false);
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        JSONObject courses = new JSONObject();
                        try {
                            for (int i =0;i < selectedCourses.size();i++){
                                Temp += selectedCourses.get(i)+":";
                            }
                            // Toast.makeText(getContext(), Temp, Toast.LENGTH_SHORT).show();
                            courses.put("courseslist",Temp);
                            courses.put("studentID",sharedPreferences.getString("userID",""));
                            courses.put("rFlag", "0");

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

                                           // Toast.makeText(getContext(), "درخواست شما ثبت شد ، با شما تماس خواهیم گرفت ", Toast.LENGTH_LONG).show();
                                            AlertDialog.Builder alert_builder = new AlertDialog.Builder(getContext());
                                            alert_builder.setIcon(R.drawable.ic_notifications_black_24dp);

                                            alert_builder.setTitle("اطلاعیه: ");
                                            alert_builder.setMessage("درخواست شما ثبت شد ، با شما تماس خواهیم گرفت " );
                                            alert_builder.setNeutralButton("تایید" , null);
                                            alert_builder.show();
                                            progressBar_courseList.setVisibility(View.GONE);
                                            btn_sendReq.setEnabled(true);


                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }





                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getContext(), "ثبت درخواست با مشکل مواجه شد ، بعدا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                                        progressBar_courseList.setVisibility(View.GONE);
                                    }
                                }


                        );
                        myrequestqueue.add(jsonObjectRequest);








                    }
                })
                        .setNegativeButton("رد", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert_builder.show();



            }
        });
        myrequestqueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST,"http://api.mim-app.ir/SelectValue_coursesList.php" , jsob,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String courseID , courseName , courseImageUrl;
                        try {
                            JSONArray array = response.getJSONArray("lessons_list");
                            Log.i("request","on responsed listener");

                            for (int i =0;i<array.length();i++){
                                JSONObject jsontemp = array.getJSONObject(i);
                                Log.i("log"," courseName : "+jsontemp.getString("courseName") +"courseID "+jsontemp.getString("courseID"));
                                courseName = jsontemp.getString("courseName");
                                courseID =  jsontemp.getString("courseID");
                                courseImageUrl = jsontemp.getString("courseImageUrl");
                                course ocourse= new course(courseID,courseName,courseImageUrl);
                               cadapter.add(ocourse);

                            }
                            //listView.setAdapter(cadapter);
                            progressBar_courseList.setVisibility(View.GONE);
                            searchView.setVisibility(View.VISIBLE);
                            textView4.setVisibility(View.VISIBLE);
                            //btn_sendReq.setVisibility(View.VISIBLE);
                            gridView.setAdapter(cadapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("request","on error listener");
                    }
                }


        );
        myrequestqueue.add(jobrq);

    }

}
