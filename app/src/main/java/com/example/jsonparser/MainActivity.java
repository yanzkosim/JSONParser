package com.example.jsonparser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private Button btn_search;
    private EditText e_search;
    private DetailAdapter dAdapter;
    private final String PREFIX_URL = "https://api.jikan.moe/v3/search/anime?q=";

    private final String TITLE = "Title: ";
    private final String STATUS = "Status: ";
    private final String TYPE = "Type: ";
    private final String EPISODES = "Episodes: ";
    private final String SCORE = "Score: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.list_view);
        btn_search = findViewById(R.id.btn_search);
        e_search = findViewById(R.id.search);

        btn_search.setOnClickListener(searchAnime);
    }

    private class getAnime extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Downloading JSON data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making request to url and getting response
            String url = PREFIX_URL + e_search.getText().toString();
            String jsonStr = sh.makeServiceCall(url);
//            Log.e("Main", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray results = jsonObj.getJSONArray("results");

                    for (int i = 0; i<results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);
                        String title = c.getString("title");
                        String status;
                        if (c.getBoolean("airing"))
                            status = "Currently Airing";
                        else
                            status = "Finished";
                        String episodes;
                        if (c.getInt("episodes") == 0)
                            episodes = "Unknown";
                        else
                            episodes = Integer.toString(c.getInt("episodes"));

                        String score = Double.toString(c.getDouble("score"));
                        String type = c.getString("type");

                        Detail detail = new Detail(TITLE + title, STATUS + status,
                                EPISODES + episodes, SCORE + score, TYPE + type);
                        dAdapter.add(detail);
                    }
                } catch (JSONException e) {
                    Log.e("Main", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else {
                Log.e("Main", "Couldn't get json from the server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from the server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);
            lv.setAdapter(dAdapter);
        }
    }

    View.OnClickListener searchAnime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(dAdapter != null)
                if(!dAdapter.isEmpty()) dAdapter.clear();
            ArrayList<Detail> listDetail = new ArrayList<>();
            dAdapter = new DetailAdapter(MainActivity.this, 0, listDetail);
            getAnime exc = new getAnime();
            exc.execute();
        }
    };
}