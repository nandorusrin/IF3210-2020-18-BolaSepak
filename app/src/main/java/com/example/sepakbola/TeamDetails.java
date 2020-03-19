package com.example.sepakbola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class TeamDetails extends AppCompatActivity {
    private ImageView teamLogo;
    private TextView teamName;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        teamLogo = (ImageView) findViewById(R.id.team_logo);
        teamName = (TextView) findViewById(R.id.team_name);
        tabLayout = (TabLayout) findViewById(R.id.team_tablayout);
        viewPager = (ViewPager) findViewById(R.id.team_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragment
        viewPagerAdapter.addFragment(new UpcomingFragment(), "UPCOMING");
        viewPagerAdapter.addFragment(new RecentFragment(), "RECENT");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
