package com.example.ToDoList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    ListView listView;
    myadapter adapter;
    RecyclerView recyclerView;
    String jsonURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = (ListView) findViewById(R.id.listView);

        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.
                Builder<Model>().setQuery(FirebaseDatabase.getInstance().getReference().child("ToDo"),
                Model.class).build();

        adapter = new myadapter(options);

        jsonURL = "https://my-json-server.typicode.com/smrakibul/db.json";

        ArrayList<Model> modelArrayList=new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        JsonArrayRequest arrayReq = new JsonArrayRequest(jsonURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject colorObj = response.getJSONObject(0);
                    JSONArray colorArray = colorObj.getJSONArray("Movies");

                    for (int i = 0; i < colorArray.length(); i++) {
                        JSONObject jsonObject = colorArray.getJSONObject(i);

                        String title = jsonObject.getString("movieName");
                        String artist = jsonObject.getString("actorName");
                        String albumArt = jsonObject.getString("urlPoster");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}