package com.example.sepakbola;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {
    private List<Schedule> scheduleList;

    public RecentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recent_recyclerview);
        RecentFragment.RecyclerViewAdapter recyclerViewAdapter = new RecentFragment.RecyclerViewAdapter(getContext(), scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleList = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());

        Bundle bundle = getArguments();
        String idHomeTeam = bundle.getString("idHomeTeam");
        String urlRecentEvents = "http://134.209.97.218:5050/api/v1/json/1/eventslast.php?id=" + idHomeTeam;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final boolean connected = (connectivityManager.getActiveNetwork() != null);
        if (connected) {
            JsonObjectRequest recentEventsObject = new JsonObjectRequest(Request.Method.GET, urlRecentEvents, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    try {
                        JSONArray recentEventsArr = response.getJSONArray("results");
                        for (int i = 0; i < recentEventsArr.length(); i++) {
                            JSONObject event = recentEventsArr.getJSONObject(i);
                            System.out.println(event);
                            String home = event.getString("strHomeTeam");
                            String away = event.getString("strAwayTeam");
                            String date = event.getString("dateEvent");
                            String homeScore = event.getString("intHomeScore");
                            String awayScore = event.getString("intAwayScore");
                            String homeGoalDetails = event.getString("strHomeGoalDetails");
                            String awayGoalDetails = event.getString("strAwayGoalDetails");
                            String homeTeamId = event.getString("idHomeTeam");
                            String awayTeamId = event.getString("idAwayTeam");
                            scheduleList.add(new Schedule(home, away, homeScore, awayScore, date, homeGoalDetails, awayGoalDetails, homeTeamId, awayTeamId));
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
                            MatchFragment fragment = new MatchFragment();
                            fragment.setArguments(bundle);
//                            fragmentTransaction.add(R.id.recent_recyclerview, fragment);
                        }
//                        fragmentTransaction.commit();
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
            requestQueue.add(recentEventsObject);
        }
        System.out.println(scheduleList);
    }

    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecentViewHolder> {
        Context mContext;
        List<Schedule> mData;

        public RecyclerViewAdapter(Context mContext, List<Schedule> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @NonNull
        @Override
        public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.match_fragment, parent, false);
            return new RecentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecentViewHolder holder, int position) {
            holder.home.setText(mData.get(position).getHome());
            holder.away.setText(mData.get(position).getAway());
            holder.when.setText(mData.get(position).getDate());
            if (mData.get(position).getHomeScore() != null && mData.get(position).getAwayScore() != null) {
                holder.homeScore.setText(mData.get(position).getHomeScore());
                holder.awayScore.setText(mData.get(position).getAwayScore());
            } else {
                holder.homeScore.setText("-");
                holder.awayScore.setText("-");
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            final boolean connected = (connectivityManager.getActiveNetwork() != null);
            if (connected) {
                final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                String urlHome = "https://thesportsdb.com/api/v1/json/1/lookupteam.php?id=" + mData.get(position).getHomeTeamId();
                String urlAway = "https://thesportsdb.com/api/v1/json/1/lookupteam.php?id=" + mData.get(position).getAwayTeamId();
                JsonObjectRequest homeTeamObject = new JsonObjectRequest(Request.Method.GET, urlHome, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray homeTeamArr = response.getJSONArray("teams");
                            Picasso.get().load(homeTeamArr.getJSONObject(0).getString("strHomeLogo")).into(holder.homeLogo);
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
                JsonObjectRequest awayTeamObject = new JsonObjectRequest(Request.Method.GET, urlAway, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray awayTeamArr = response.getJSONArray("teams");
                            Picasso.get().load(awayTeamArr.getJSONObject(0).getString("strAwayLogo")).into(holder.awayLogo);
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
                requestQueue.add(awayTeamObject);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class RecentViewHolder extends RecyclerView.ViewHolder {
            private TextView home;
            private TextView away;
            private TextView when;
            private TextView homeScore;
            private TextView awayScore;
            private TextView vs;
            private ImageView homeLogo;
            private ImageView awayLogo;

            public RecentViewHolder(@NonNull View itemView) {
                super(itemView);
                home = (TextView) itemView.findViewById(R.id.textView6);
                away = (TextView) itemView.findViewById(R.id.textView7);
                when = (TextView) itemView.findViewById(R.id.textView);
                homeScore = (TextView) itemView.findViewById(R.id.textView2);
                awayScore = (TextView) itemView.findViewById(R.id.textView3);
                vs = (TextView) itemView.findViewById(R.id.textView4);
                homeLogo = (ImageView) itemView.findViewById(R.id.imageView3);
                awayLogo = (ImageView) itemView.findViewById(R.id.imageView4);
            }
        }
    }
}
