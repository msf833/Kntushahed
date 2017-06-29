package ir.madamas.kntushahed.kntushahed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import ir.madamas.kntushahed.kntushahed.classes.Booklet;
import ir.madamas.kntushahed.kntushahed.classes.bookletAdapter;

public class bookletActivity extends AppCompatActivity {

    ListView bookletListView;
    bookletAdapter adapter;
    List<Booklet> bookletList;

    ProgressBar ProgressView;

    RequestQueue myrequestqueue;
    String flag;

    String ID;
    String name;
    String level;
    String user_ID;
    String link;
    String size;
    String rate;
    String price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklet);

        bookletListView = (ListView) findViewById(R.id.booklet_listView);
        ProgressView = (ProgressBar) findViewById(R.id.booklet_progressBar);

        bookletList = new ArrayList<>();
        myrequestqueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject booklet = new JSONObject();

        JsonObjectRequest jobrq = new JsonObjectRequest(Request.Method.POST, "http://api.mim-app.ir/SelectValue_BookletList.php", booklet,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray array = response.getJSONArray("bookletQuery");

                            JSONObject jsontemp = array.getJSONObject(0);

                            flag = jsontemp.getString("flag");
                            Log.i("boob", flag);

                            //Toast.makeText(getApplicationContext(), "userID: " + userID, Toast.LENGTH_SHORT).show();
                            Log.i("boob 2", "dare mishe");
                            if (flag.toString().startsWith("t")){
                                Log.i("boob 3", "shod");

                                for (int i = 0;i<array.length();i++){

                                    jsontemp = array.getJSONObject(i);

                                    ID = jsontemp.getString("ID");
                                    name = jsontemp.getString("name");
                                    level = jsontemp.getString("level");
                                    link = jsontemp.getString("link");
                                    size = jsontemp.getString("size");
                                    user_ID = jsontemp.getString("user_ID");
                                    rate = jsontemp.getString("rate");
                                    price = jsontemp.getString("price");

                                    Log.i("boob 4", ID + " " + name);

                                    Booklet booklet= new Booklet(ID,name,level,user_ID,link,size,rate,price);
                                    bookletList.add(booklet);

                                }

                                adapter = new bookletAdapter(getApplicationContext(), bookletList);
                                bookletListView.setAdapter(adapter);
                                ProgressView.setVisibility(View.GONE);

                            }else if (flag.toString().startsWith("f")){
                                Toast.makeText(getApplicationContext(), "ارتباط خود با اینترنت را مجددا چک کنید", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                }


        );
        myrequestqueue.add(jobrq);

        bookletListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view.getTag(R.string.panjomi).toString()

                Uri uri = Uri.parse(view.getTag(R.string.panjomi).toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                /*
                Intent intent = new Intent(getApplicationContext(), bookletDetailActivity.class);
                intent.putExtra("ID", view.getTag(R.string.avali).toString());
                intent.putExtra("ID", view.getTag(R.string.avali).toString());
                intent.putExtra("name", view.getTag(R.string.dovomi).toString());
                intent.putExtra("rate", view.getTag(R.string.sevomi).toString());
                intent.putExtra("level", view.getTag(R.string.chaharomi).toString());
                intent.putExtra("link", view.getTag(R.string.panjomi).toString());
                intent.putExtra("price", view.getTag(R.string.shishomi).toString());
                intent.putExtra("size", view.getTag(R.string.haftomi).toString());
                startActivity(intent);
                */
            }
        });
    }



}
