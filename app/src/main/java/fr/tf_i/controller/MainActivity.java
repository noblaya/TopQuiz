package fr.tf_i.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.tf_i.R;
import fr.tf_i.model.User;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private EditText mNumberOfQuestionsInput;
    private String mNumberOfQuestionsString;
    private int mNumberOfQuestionsInt;
    private Button mPlayButton;
    private Button mLeadButton;
    private User mUser;
    protected SharedPreferences mPreferences;

    private GameActivity mGameActivity = new GameActivity();

    public static final int GAME_ACTIVITY_REQUEST_CODE = 1;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_NUMBEROFQUESTIONS = "PREF_KEY_NUMBEROFQUESTIONS";
    public static final String PREF_KEY_PERCENTAGE = "PREF_KEY_PERCENTAGE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            int thescorepercentage = data.getIntExtra(GameActivity.BUNDLE_EXTRA_PERCENTAGE, 0);

            mPreferences.edit().putInt(PREF_KEY_PERCENTAGE, thescorepercentage).apply();

            greetingPlayer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate()");

        mUser = new User();

        mPreferences = getPreferences(MODE_PRIVATE);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_nameinput);
        mNumberOfQuestionsInput = (EditText) findViewById(R.id.activity_main_numberofquestionsinput);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mLeadButton = (Button) findViewById(R.id.activity_main_leaderboard_btn);
        int mScore = mUser.getScore();

        //Disable Play Button At Strat
        mPlayButton.setEnabled(false);

        //START
        greetingPlayer();

        //Name input field
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Set IsNameSetup true.
                mNumberOfQuestionsInput.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNumberOfQuestionsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Activate button if Name and Number of Questions are setup
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Button "Play" Pressed
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set user name in model/user.class
                String firstname = mNameInput.getText().toString();
                mUser.setFirstname(firstname);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstname()).apply();

                //Set Number Of Questions to INT then set it in model/user.class
                mNumberOfQuestionsString = mNumberOfQuestionsInput.getText().toString();
                mNumberOfQuestionsInt = Integer.parseInt(mNumberOfQuestionsString);
                mPreferences.edit().putInt(PREF_KEY_NUMBEROFQUESTIONS, mNumberOfQuestionsInt).apply();

                //Misc
                mGameActivity.setName(firstname);


                //Launch Game
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                gameActivityIntent.putExtra("NUMBER_OF_QUESTIONS", mNumberOfQuestionsInt);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

        //Button "Leaderbord" Pressed
        mLeadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leadActivityIntent = new Intent(MainActivity.this,LeaderboardActivity.class);
                startActivityForResult(leadActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });



    }

    private void greetingPlayer() {
        String firstName = mPreferences.getString(PREF_KEY_FIRSTNAME, null);
        int percent = mPreferences.getInt(PREF_KEY_PERCENTAGE,0);

        if (percent != -1) {
            String greetingText = "Bienvenu à nouveau " + firstName + "\n"
                    + "La dernière fois ton score était de " + percent + "%" + "\n"
                    + "Ferras-tu mieux cette fois ? ;-) ";

            mGreetingText.setText(greetingText);
            mNameInput.setText(firstName);
            mNameInput.setSelection(firstName.length());
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        greetingPlayer();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }
}
