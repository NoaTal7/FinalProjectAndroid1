package com.example.sela.finalprojectandroid1;

import android.net.Uri;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.sela.finalprojectandroid1.Adapter.AnswerSheetAdapter;
import com.example.sela.finalprojectandroid1.Adapter.QuestionFragmentAdapter;
import com.example.sela.finalprojectandroid1.Common.Common;
import com.example.sela.finalprojectandroid1.DBHelper.DBHelper;
import com.example.sela.finalprojectandroid1.Model.CurrentQuestion;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    // Noa Test
    int right_Answer_Count = 0;
    int wrong_Answer_Count = 0;
    String selectedCategoryName = null;
    HashMap<String, Integer> _usersScoresHashMap = new HashMap<String, Integer>();
    // End Noa Test

    int time_play = Common.TOTAL_TIME;
    boolean isAnswerModeView = false;
    CountDownTimer countDownTimer;

    TextView txt_right_answer, txt_timer;

    RecyclerView answer_Sheet_View;
    AnswerSheetAdapter answerSheetAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onDestroy()
    {
        if(Common.countDownTimer != null)
        {
            Common.countDownTimer.cancel();
        }
        super.onDestroy();
    }

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar, "Open navigation drawer", "Close navigation drawer");
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();


//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        // Passing each menu ID as a set of Ids because each
//        //menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_controller_view_tag);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        //first step - take the question for DB
        takeQuestion();


        if (Common.questionList.size() > 0)
        {
            //show textview for the right answer and for the timer
            txt_right_answer = (TextView)findViewById(R.id.txt_question_right);
            txt_timer = (TextView)findViewById(R.id.txt_timer);

            txt_timer.setVisibility(View.VISIBLE);
            txt_right_answer.setVisibility(View.VISIBLE);

            countTimer();


            //View
        answer_Sheet_View = (RecyclerView) findViewById(R.id.grid_answer);
        answer_Sheet_View.setHasFixedSize(true);
        if (Common.questionList.size() > 5)
             //if question list have size > 5, we will separate 2 rows
        {
            answer_Sheet_View.setLayoutManager(new GridLayoutManager(this, Common.questionList.size() / 2));
        }
        answerSheetAdapter = new AnswerSheetAdapter(this, Common.answerSheetList);
        answer_Sheet_View.setAdapter(answerSheetAdapter);


        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.sliding_tabs);


        GenFragmentList();

            QuestionFragmentAdapter questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager(),
                    this,
                    Common.fragmentList);
            viewPager.setAdapter(questionFragmentAdapter);

            tabLayout.setupWithViewPager(viewPager);  //adding the sliding tab to the view

            //Event
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                int SCROLLING_RIGHT = 0;
                int SCROLLING_LEFT = 1;
                int SCROLLING_UNDETERMINED = 2;

                int currentScrollDirection = 2;

                        private void setScrollingDirection(float positionOffSet)
                        {
                            if (1-positionOffSet > 0.5)
                            {
                                this.currentScrollDirection = SCROLLING_RIGHT;
                            }else if (1 - positionOffSet <= 0.5)
                            {
                                this.currentScrollDirection = SCROLLING_LEFT;
                            }
                        }

                        private boolean isScrollDirectionUndetermined()
                        {
                            return currentScrollDirection == SCROLLING_UNDETERMINED;
                        }

                        private boolean isScrollingRight()
                        {
                            return currentScrollDirection == SCROLLING_RIGHT;
                        }

                        private boolean isScrollingLeft()
                        {
                    return currentScrollDirection == SCROLLING_LEFT;
                        }




                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (isScrollDirectionUndetermined())
                    {
                        setScrollingDirection(positionOffset);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                    QuestionFragment questionFragment;
                    int i = 0;
                    if (position > 0) {
                        if (isScrollingRight())
                        {
                            //If user scroll to right, get previous fragment to calculate result;
                            questionFragment = Common.fragmentList.get(position - 1);
                            i = position - 1;

                        } else if (isScrollingLeft())
                        {
                            //If user scroll to left, get next fragment to calculate result;
                            questionFragment = Common.fragmentList.get(position + 1);
                            i = position + 1;

                        } else
                            {
                            questionFragment = Common.fragmentList.get(i);
                            }
                    } else {
                        questionFragment = Common.fragmentList.get(0);
                        i = 0;
                    }

                    //if you want to show correct answer, just call function here
                    CurrentQuestion question_state = questionFragment.getSelectedAnswer();
                    Common.answerSheetList.set(i, question_state); //Set question answer for answersheet
                    answerSheetAdapter.notifyDataSetChanged(); //change color in answer sheet

                    countCorrectAnswer();

                    txt_right_answer.setText(new StringBuilder(String.format("%d", Common.right_Answer_Count))
                            .append("/")
                            .append(String.format("%d", Common.questionList.size())).toString());

                    if (question_state.getType() == Common.ANSWER_TYPE.NO_ANSWER)
                    {
                        questionFragment.showCorrectAnswer();
                        questionFragment.disableAnswer();
                    }



                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE)
                    {
                        this.currentScrollDirection = SCROLLING_UNDETERMINED;
                    }
                }
            });
    }


    }

    private void countCorrectAnswer()
    {
        //reset variable
        Common.right_Answer_Count = Common.wrong_Answer_Count = 0;
        for (CurrentQuestion item:Common.answerSheetList)
        {
            if (item.getType() == Common.ANSWER_TYPE.RIGHT_ANSWER)
            {
                Common.right_Answer_Count++; //USE THE VARIABLE TO THE FIREBASE!!
                right_Answer_Count++;
            }else if(item.getType() == Common.ANSWER_TYPE.WRONG_ANSWER)
            {
                Common.wrong_Answer_Count++;
                wrong_Answer_Count++;
            }
        }





        // End Test Noa

    }


    // Test Noa
    private void userScore()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            _usersScoresHashMap.put(email, right_Answer_Count);
        }
        Toast.makeText(getApplicationContext(), "_" + _usersScoresHashMap.size(), Toast.LENGTH_LONG).show();
    }
    // Test Noa


    private void GenFragmentList()
    {
        for(int i = 0; i < Common.questionList.size(); i++)
        {
            Bundle bundle = new Bundle();   // bundle is a key value structure date
            bundle.putInt("index",i);
            QuestionFragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);


            Common.fragmentList.add(fragment);

        }


    }


    private void countTimer()
    {
        if (Common.countDownTimer == null)
        {
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {
                    txt_timer.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                     TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    time_play-=1000;
                }

                @Override
                public void onFinish()
                {
                    //Finish Game

                }
            }.start();
        }
        else {
            Common.countDownTimer.cancel();
            Common.countDownTimer = new CountDownTimer(Common.TOTAL_TIME, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished) {
                    txt_timer.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS
                                    .toSeconds(millisUntilFinished) - TimeUnit.MINUTES
                                    .toSeconds(TimeUnit.MILLISECONDS
                                            .toMinutes(millisUntilFinished))));
                    time_play-=1000;
                }

                @Override
                public void onFinish()
                {
                    //Finish Game

                }
            }.start();
        }
    }

    private void takeQuestion()
    {
        Common.questionList = DBHelper.getInstance(this).getQuestionByCategory(Common.selectedCategory.getId());
        if (Common.questionList.size() == 0)
        {
            //if no question
            new MaterialStyledDialog.Builder(this).setTitle("אוּפס !").setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp).setDescription("אין כרגע שאלה זמינה ב-" + Common.selectedCategory.getName() + "לקטגוריה")
            .onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                    finish();
                }
            }).show();
        }
        else
        {
            if (Common.answerSheetList.size() > 0)
            {
                Common.answerSheetList.clear();
            }

            //general answerSheet item from question
            //30 question = 30 answer sheet item
            //1 question = 1 answer sheet item

            for (int i = 0; i < Common.questionList.size();i++)
            {
                //since we need to take index of Question in list, so we will use for i
                Common.answerSheetList.add(new CurrentQuestion(i,Common.ANSWER_TYPE.NO_ANSWER));// default answer is no answer
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_view);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
