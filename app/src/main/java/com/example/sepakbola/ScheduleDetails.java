package com.example.sepakbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScheduleDetails extends AppCompatActivity {
    private String home;
    private String away;
    private String when;
    private String homeScore;
    private String awayScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_details);
        Intent intent = getIntent();
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        if (intent != null){
            home = intent.getStringExtra("home");
            away = intent.getStringExtra("away");
            when = intent.getStringExtra("date");
            homeScore = intent.getStringExtra("homeScore");
            awayScore = intent.getStringExtra("awayScore");
            String[] homeGoalDetails = intent.getStringArrayExtra("homeGoalDetails");
            String[] awayGoalDetails = intent.getStringArrayExtra("awayGoalDetails");
            textView6.setText(home);
            textView7.setText(away);
            textView.setText(when);
            textView2.setText(homeScore);
            textView3.setText(awayScore);
            LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.layout_home_score);
            TextView textView4 = new TextView(this);
            textView4.setText(homeScore);
            linearLayout1.addView(textView4);
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout_away_score);
            TextView textView5 = new TextView(this);
            textView5.setText(awayScore);
            linearLayout2.addView(textView5);
            for (int i = 0; i < homeGoalDetails.length; i++){
                TextView textView1 = new TextView(this);
                textView1.setText(homeGoalDetails[i]);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_home_score);
                linearLayout.addView(textView1);
            }
            for (int i = 0; i < awayGoalDetails.length; i++){
                TextView textView1 = new TextView(this);
                textView1.setText(awayGoalDetails[i]);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_away_score);
                linearLayout.addView(textView1);
            }
        }
    }
}
