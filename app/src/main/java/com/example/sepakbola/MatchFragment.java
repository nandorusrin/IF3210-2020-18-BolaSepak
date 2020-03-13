package com.example.sepakbola;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class MatchFragment extends Fragment {

    private String home;
    private String away;
    private String when;
    private String homeScore;
    private String awayScore;
    private String homeGoalDetails;
    private String awayGoalDetails;
    private String idHomeTeam;

    public void OnClick(View view){

    }

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
                startActivity(intent);
            }
        });
        TextView textView6 = (TextView) view.findViewById(R.id.textView6);
        TextView textView7 = (TextView) view.findViewById(R.id.textView7);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);
        if (getArguments() != null) {
            home = getArguments().getString("home");
            away = getArguments().getString("away");
            when = getArguments().getString("when");
            homeScore = getArguments().getString("homeScore");
            awayScore = getArguments().getString("awayScore");
            homeGoalDetails = getArguments().getString("homeGoalDetails");
            awayGoalDetails = getArguments().getString("awayGoalDetails");
            idHomeTeam = getArguments().getString("idHomeTeam");
            textView6.setText(home);
            textView7.setText(away);
            textView.setText(when);
            textView2.setText(homeScore);
            textView3.setText(awayScore);
        }
        return view;
    }
}
