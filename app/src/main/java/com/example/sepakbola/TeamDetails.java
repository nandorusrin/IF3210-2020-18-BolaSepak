package com.example.sepakbola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final ImageView teamLogo = (ImageView) findViewById(R.id.team_logo);
        TextView teamName = (TextView) findViewById(R.id.team_name);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.team_tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.team_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Intent intent = getIntent();
        teamName.setText(intent.getStringExtra("home"));
        String idHomeTeam = intent.getStringExtra("idHomeTeam");
        if (intent != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final boolean connected = (connectivityManager.getActiveNetwork() != null);
            if (connected) {
                String urlTeamDetail = "http://134.209.97.218:5050/api/v1/json/1/lookupteam.php?id=" + intent.getStringExtra("idHomeTeam");
                JsonObjectRequest homeTeamObject = new JsonObjectRequest(Request.Method.GET, urlTeamDetail, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Picasso.get().load(response.getJSONArray("teams").getJSONObject(0).getString("strTeamLogo")).into(teamLogo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(homeTeamObject);
            }
        }
        // Add fragment.
        Bundle bundle = new Bundle();
        bundle.putString("idHomeTeam", idHomeTeam);
        UpcomingFragment upcomingFragment = new UpcomingFragment();
        RecentFragment recentFragment = new RecentFragment();
        upcomingFragment.setArguments(bundle);
        recentFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(upcomingFragment, "UPCOMING");
        viewPagerAdapter.addFragment(recentFragment, "RECENT");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
