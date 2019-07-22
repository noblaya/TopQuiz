package fr.tf_i.controller;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.tf_i.R;

import static java.lang.System.out;

public class LeaderboardActivity extends AppCompatActivity {
    private List<String> mLeaderboard = new ArrayList<String>(Arrays.asList("0,Personne","0,Personne","0,Personne","0,Personne","0,Personne"));

    private TextView mLead1TextView;
    private TextView mLead2TextView;
    private TextView mLead3TextView;
    private TextView mLead4TextView;
    private TextView mLead5TextView;

    private String mLead1Text;
    private String mLead2Text;
    private String mLead3Text;
    private String mLead4Text;
    private String mLead5Text;

    //Add to Leaderboard
    private String mTempLeadString;

    //Spliter
    private String[] parts;
    private String[] mSplited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        System.out.println("LeaderboardActivity::onCreate()");

        mLead1TextView = (TextView) findViewById(R.id.activity_leaderboard_text1);
        mLead2TextView = (TextView) findViewById(R.id.activity_leaderboard_text2);
        mLead3TextView = (TextView) findViewById(R.id.activity_leaderboard_text3);
        mLead4TextView = (TextView) findViewById(R.id.activity_leaderboard_text4);
        mLead5TextView = (TextView) findViewById(R.id.activity_leaderboard_text5);

        setLead1Text(mLeaderboard.get(0));
        setLead2Text(mLeaderboard.get(1));
        setLead3Text(mLeaderboard.get(2));
        setLead4Text(mLeaderboard.get(3));
        setLead5Text(mLeaderboard.get(4));

        mLead1TextView.setText(mLead1Text);
        mLead2TextView.setText(mLead2Text);
        mLead3TextView.setText(mLead3Text);
        mLead4TextView.setText(mLead4Text);
        mLead5TextView.setText(mLead5Text);

        setLeaderboard();

    }


    /*
    Add score + name to the Leaderboard
     */
    public void addScoreNameToLeaderboard (int mPercentage ,String name) {

        mTempLeadString = mPercentage + "," + name;

        mLeaderboard.add(mTempLeadString);

    }

    /*
    Print the leaderboard
     */
    public void setLeaderboard() {
        Collections.sort(mLeaderboard, new NumericalStringComparator().reversed());
        System.out.println(Arrays.toString(mLeaderboard.toArray()));

    }
    //Setters

    public void setLead1Text(String lead1Text) {
        mSplited = Spliter(lead1Text);

        String num = "1. ";
        int score = Integer.valueOf(mSplited[0]);
        String name = mSplited[1];

        lead1Text = num + "     " + score + "%" + "     " + name;

        mLead1Text = lead1Text;
    }

    public void setLead2Text(String lead2Text) {
        mSplited = Spliter(lead2Text);

        String num = "2. ";
        int score = Integer.valueOf(mSplited[0]);
        String name = mSplited[1];

        lead2Text = num + "     " + score + "%" + "     " + name;

        mLead2Text = lead2Text;
    }

    public void setLead3Text(String lead3Text) {
        mSplited = Spliter(lead3Text);

        String num = "3. ";
        int score = Integer.valueOf(mSplited[0]);
        String name = mSplited[1];

        lead3Text = num + "     " + score + "%" + "     " + name;

        mLead3Text = lead3Text;
    }

    public void setLead4Text(String lead4Text) {
        mSplited = Spliter(lead4Text);

        String num = "4. ";
        int score = Integer.valueOf(mSplited[0]);
        String name = mSplited[1];

        lead4Text = num + "     " + score + "%" + "     " + name;

        mLead4Text = lead4Text;
    }

    public void setLead5Text(String lead5Text) {
        mSplited = Spliter(lead5Text);

        String num = "5. ";
        int score = Integer.valueOf(mSplited[0]);
        String name = mSplited[1];

        lead5Text = num + "     " + score + "%" + "     " + name;

        mLead5Text = lead5Text;
    }

    private String[] Spliter(String textToParse) {

        parts = textToParse.split(",");

        return parts;
    }


    @Override
    protected void onStart() {
        super.onStart();

        out.println("LeaderboardActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("LeaderboardActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("LeaderboardActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("LeaderboardActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("LeaderboardActivity::onDestroy()");
    }
}
