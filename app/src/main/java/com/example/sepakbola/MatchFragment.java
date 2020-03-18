package com.example.sepakbola;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MatchFragment extends Fragment {
    private String home;
    private String away;
    private String when;
    private String homeScore;
    private String awayScore;
    private String homeGoalDetails;
    private String awayGoalDetails;
    private String idHomeTeam;
    private String idAwayTeam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.match_fragment, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), ScheduleDetails.class);
                String[] homeGoals = homeGoalDetails.split(";");
                String[] awayGoals = awayGoalDetails.split(";");
                intent.putExtra("homeGoalDetails", homeGoals);
                intent.putExtra("awayGoalDetails",awayGoals);
                intent.putExtra("home", home);
                intent.putExtra("away",away);
                intent.putExtra("homeScore", homeScore);
                intent.putExtra("awayScore",awayScore);
                intent.putExtra("date", when);
                intent.putExtra("idHomeTeam", idHomeTeam);
                intent.putExtra("idAwayTeam", idAwayTeam);
                startActivity(intent);
            }
        });
        TextView textView6 = (TextView) view.findViewById(R.id.textView6);
        TextView textView7 = (TextView) view.findViewById(R.id.textView7);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);
        final ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        final ImageView imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        if (getArguments() != null) {
            home = getArguments().getString("home");
            away = getArguments().getString("away");
            when = getArguments().getString("when");
            homeScore = getArguments().getString("homeScore");
            awayScore = getArguments().getString("awayScore");
            homeGoalDetails = getArguments().getString("homeGoalDetails");
            awayGoalDetails = getArguments().getString("awayGoalDetails");
            idHomeTeam = getArguments().getString("idHomeTeam");
            idAwayTeam = getArguments().getString("idAwayTeam");
            textView6.setText(home);
            textView7.setText(away);
            textView.setText(when);
            textView2.setText(homeScore);
            textView3.setText(awayScore);
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                final boolean connected = (connectivityManager.getActiveNetwork() != null);
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                if (connected) {
                    String urlHome = "https://thesportsdb.com/api/v1/json/1/lookupteam.php?id=" + idHomeTeam;
                    String urlAway = "https://thesportsdb.com/api/v1/json/1/lookupteam.php?id=" + idAwayTeam;
                    JsonObjectRequest homeTeamObject = new JsonObjectRequest(Request.Method.GET, urlHome, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray teamArr = response.getJSONArray("teams");
                                Picasso.get().load(teamArr.getJSONObject(0).getString("strTeamLogo")).into(imageView3);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VolleyError", "onErrorResponse: " + error);
                        }
                    });
                    JsonObjectRequest awayTeamObject = new JsonObjectRequest(Request.Method.GET, urlAway, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray teamArr = response.getJSONArray("teams");
                                Picasso.get().load(teamArr.getJSONObject(0).getString("strTeamLogo")).into(imageView4);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VolleyError", "onErrorResponse: " + error);
                        }
                    });
                    requestQueue.add(homeTeamObject);
                    requestQueue.add(awayTeamObject);
                }
            }
        }
        return view;
    }
}
