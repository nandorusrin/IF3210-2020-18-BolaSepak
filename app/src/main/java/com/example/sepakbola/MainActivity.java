package com.example.sepakbola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private JSONArray array;
    private SQLiteDatabaseHandler db;
    String homeTeamId, awayTeamId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://134.209.97.218:5050/api/v1/json/1/eventsseason.php?id=4328&s=1415";
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        db = new SQLiteDatabaseHandler(this);
        final boolean connected = connectivityManager.getActiveNetwork() != null ? true : false;
        if (savedInstanceState == null || array == null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (connected) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    try {

                                        array = response.getJSONArray("events");
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject event = array.getJSONObject(i);
                                            String home = event.getString("strHomeTeam");
                                            String away = event.getString("strAwayTeam");
                                            String homeScore = event.getString("intHomeScore");
                                            String awayScore = event.getString("intAwayScore");
                                            String date = event.getString("dateEvent");
                                            String homeGoalDetails = event.getString("strHomeGoalDetails");
                                            String awayGoalDetails = event.getString("strAwayGoalDetails");
                                            homeTeamId = event.getString("idHomeTeam");
                                            awayTeamId = event.getString("idAwayTeam");
                                            String quer = "http://134.209.97.218:5050//api/v1/json/1/lookupteam.php?id=" + homeTeamId;
                                            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, quer, null, new Response.Listener<JSONObject>() {

                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        JSONArray arr = response.getJSONArray("team");
                                                        homeTeamId = arr.getJSONObject(0).getString("strStadiumLocation");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.d("help", error.getMessage());
                                                        }
                                                    });
                                            Schedule schedule = new Schedule(home, away, homeScore, awayScore, date, homeGoalDetails, awayGoalDetails, homeTeamId, awayTeamId);
                                            db.insertData(schedule);
                                            if (i < 10) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("home", home);
                                                bundle.putString("away", away);
                                                bundle.putString("when", date);
                                                bundle.putString("homeScore", homeScore);
                                                bundle.putString("awayScore", awayScore);
                                                bundle.putString("homeGoalDetails", homeGoalDetails);
                                                bundle.putString("awayGoalDetails", awayGoalDetails);
                                                bundle.putString("idHomeTeam", homeTeamId);
                                                bundle.putString("idAwayTeam", awayTeamId);
                                                MatchFragment fragment = new MatchFragment(queue);
                                                fragment.setArguments(bundle);
                                                fragmentTransaction.add(R.id.layout_home, fragment);
                                            }

                                        }
                                        fragmentTransaction.commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("help", error.getMessage());
                                }
                            }
                    );
                    queue.add(jsonObjectRequest);
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    List<Schedule> schedules = db.allSchedule();
                    if (schedules != null) {
                        int length = Math.min(schedules.size(), 10);
                        for (int i = 0; i < length; i++) {
                            Bundle bundle = new Bundle();
                            bundle.putString("home", schedules.get(i).getHome());
                            bundle.putString("away", schedules.get(i).getAway());
                            bundle.putString("when", schedules.get(i).getDate());
                            bundle.putString("homeScore", schedules.get(i).getHomeScore());
                            bundle.putString("awayScore", schedules.get(i).getAwayScore());
                            bundle.putString("homeGoalDetails", schedules.get(i).getGoalHomeDetails());
                            bundle.putString("awayGoalDetails", schedules.get(i).getGoalAwayDetails());
                            bundle.putString("idHomeTeam", schedules.get(i).getHomeTeamId());
                            bundle.putString("idAwayTeam", schedules.get(i).getAwayTeamId());
                            MatchFragment fragment = new MatchFragment(queue);
                            fragment.setArguments(bundle);
                            fragmentTransaction.add(R.id.layout_home, fragment);
                        }
                        fragmentTransaction.commit();
                    }

                }

            }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                if (connected) {
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    try {
                                        array = response.getJSONArray("events");
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject event = array.getJSONObject(i);
                                            String home = event.getString("strHomeTeam");
                                            String away = event.getString("strAwayTeam");
                                            String homeScore = event.getString("intHomeScore");
                                            String awayScore = event.getString("intAwayScore");
                                            String date = event.getString("dateEvent");
                                            String homeGoalDetails = event.getString("strHomeGoalDetails");
                                            String awayGoalDetails = event.getString("strAwayGoalDetails");
                                            homeTeamId = event.getString("idHomeTeam");
                                            awayTeamId = event.getString("idAwayTeam");
                                            String quer = "http://134.209.97.218:5050//api/v1/json/1/lookupteam.php?id=" + homeTeamId;
                                            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, quer, null, new Response.Listener<JSONObject>() {

                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        JSONArray arr = response.getJSONArray("team");
                                                        homeTeamId = arr.getJSONObject(0).getString("strStadiumLocation");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.d("help", error.getMessage());
                                                        }
                                                    });
                                            Schedule schedule = new Schedule(home, away, homeScore, awayScore, date, homeGoalDetails, awayGoalDetails, homeTeamId, awayTeamId);
                                            db.insertData(schedule);
                                            if (i < array.length()/2) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("home", home);
                                                bundle.putString("away", away);
                                                bundle.putString("when", date);
                                                bundle.putString("homeScore", homeScore);
                                                bundle.putString("awayScore", awayScore);
                                                bundle.putString("homeGoalDetails", homeGoalDetails);
                                                bundle.putString("awayGoalDetails", awayGoalDetails);
                                                bundle.putString("idHomeTeam", homeTeamId);
                                                bundle.putString("idAwayTeam", awayTeamId);
                                                MatchFragment fragment = new MatchFragment(queue);
                                                fragment.setArguments(bundle);
                                                fragmentTransaction.add(R.id.layout_col_left, fragment);
                                            }else{
                                                Bundle bundle = new Bundle();
                                                bundle.putString("home", home);
                                                bundle.putString("away", away);
                                                bundle.putString("when", date);
                                                bundle.putString("homeScore", homeScore);
                                                bundle.putString("awayScore", awayScore);
                                                bundle.putString("homeGoalDetails", homeGoalDetails);
                                                bundle.putString("awayGoalDetails", awayGoalDetails);
                                                bundle.putString("idHomeTeam", homeTeamId);
                                                bundle.putString("idAwayTeam", awayTeamId);
                                                MatchFragment fragment = new MatchFragment(queue);
                                                fragment.setArguments(bundle);
                                                fragmentTransaction.add(R.id.layout_col_right, fragment);
                                            }

                                        }
                                        fragmentTransaction.commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("help", error.getMessage());
                                }
                            }
                    );
                    queue.add(jsonObjectRequest);
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    List<Schedule> schedules = db.allSchedule();
                    if (schedules != null) {
                        int length = Math.min(schedules.size(), 10);
                        for (int i = 0; i < length; i++) {
                            Bundle bundle = new Bundle();
                            bundle.putString("home", schedules.get(i).getHome());
                            bundle.putString("away", schedules.get(i).getAway());
                            bundle.putString("when", schedules.get(i).getDate());
                            bundle.putString("homeScore", schedules.get(i).getHomeScore());
                            bundle.putString("awayScore", schedules.get(i).getAwayScore());
                            bundle.putString("homeGoalDetails", schedules.get(i).getGoalHomeDetails());
                            bundle.putString("awayGoalDetails", schedules.get(i).getGoalAwayDetails());
                            bundle.putString("idHomeTeam", schedules.get(i).getHomeTeamId());
                            bundle.putString("idAwayTeam", schedules.get(i).getAwayTeamId());
                            MatchFragment fragment = new MatchFragment(queue);
                            fragment.setArguments(bundle);
                            if (i < length/2) {
                                fragmentTransaction.add(R.id.layout_col_left, fragment);
                            }else{
                                fragmentTransaction.add(R.id.layout_col_right, fragment);
                            }
                        }
                        fragmentTransaction.commit();
                    }

                }
            }
        }
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.numberOfSteps);
        String txt = "Number of steps: " + Sensor.TYPE_STEP_COUNTER;
        textView.setText(txt);
        final EditText field = (EditText) findViewById(R.id.editText);
        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String text = field.getText().toString().toLowerCase();
                final FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                if (text != "") {
                    if (connected) {
                        try {
                            int leftCol = 0;
                            int rightCol = 1;
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject event = array.getJSONObject(i);
                                String home = event.getString("strHomeTeam");
                                String away = event.getString("strAwayTeam");
                                String date = event.getString("dateEvent");
                                String homeScore = event.getString("intHomeScore");
                                String awayScore = event.getString("intAwayScore");
                                String homeGoalDetails = event.getString("strHomeGoalDetails");
                                String awayGoalDetails = event.getString("strAwayGoalDetails");
                                String homeTeamId = event.getString("idHomeTeam");
                                String awayTeamId = event.getString("idAwayTeam");
                                if (home.toLowerCase().contains(text) || away.toLowerCase().contains(text) || date.toLowerCase().contains(text) || homeScore.toLowerCase().contains(text) || awayScore.toLowerCase().contains(text)) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("home", home);
                                    bundle.putString("away", away);
                                    bundle.putString("when", date);
                                    bundle.putString("homeScore", homeScore);
                                    bundle.putString("awayScore", awayScore);
                                    bundle.putString("homeGoalDetails", homeGoalDetails);
                                    bundle.putString("awayGoalDeails", awayGoalDetails);
                                    bundle.putString("idHomeTeam", homeTeamId);
                                    bundle.putString("idAwayTeam", awayTeamId);
                                    MatchFragment fragment = new MatchFragment(queue);
                                    fragment.setArguments(bundle);
                                    if (rightCol >= leftCol) {
                                        fragmentTransaction.add(R.id.layout_col_left, fragment);
                                    }else if (leftCol > rightCol){
                                        fragmentTransaction.add(R.id.layout_col_right, fragment);
                                    }
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        int leftCol = 0;
                        int rightCol = 1;
                        List<Schedule> schedules = db.allSchedule();
                        for (int i = 0; i < schedules.size(); i++) {
                            String home = schedules.get(i).getHome();
                            String away = schedules.get(i).getAway();
                            String date = schedules.get(i).getDate();
                            String homeScore = schedules.get(i).getHomeScore();
                            String awayScore = schedules.get(i).getAwayScore();
                            String homeGoalDetails = schedules.get(i).getGoalHomeDetails();
                            String awayGoalDetails = schedules.get(i).getGoalHomeDetails();
                            String homeTeamId = schedules.get(i).getHomeTeamId();
                            String awayTeamId = schedules.get(i).getAwayTeamId();
                            if (home.toLowerCase().contains(text) || away.toLowerCase().contains(text) || date.toLowerCase().contains(text) || homeScore.toLowerCase().contains(text) || awayScore.toLowerCase().contains(text)) {
                                Bundle bundle = new Bundle();
                                bundle.putString("home", home);
                                bundle.putString("away", away);
                                bundle.putString("when", date);
                                bundle.putString("homeScore", homeScore);
                                bundle.putString("awayScore", awayScore);
                                bundle.putString("homeGoalDetails", homeGoalDetails);
                                bundle.putString("awayGoalDeails", awayGoalDetails);
                                bundle.putString("idHomeTeam", homeTeamId);
                                bundle.putString("idAwayTeam", awayTeamId);
                                MatchFragment fragment = new MatchFragment(queue);
                                fragment.setArguments(bundle);
                                if (rightCol >= leftCol) {
                                    fragmentTransaction.add(R.id.layout_col_left, fragment);
                                }else if (leftCol > rightCol){
                                    fragmentTransaction.add(R.id.layout_col_right, fragment);
                                }
                            }

                        }

                    }
                }
                fragmentTransaction.commit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

    }


}
