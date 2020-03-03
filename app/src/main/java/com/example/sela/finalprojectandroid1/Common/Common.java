package com.example.sela.finalprojectandroid1.Common;

import android.os.CountDownTimer;

import com.example.sela.finalprojectandroid1.DBHelper.Question;
import com.example.sela.finalprojectandroid1.Model.Category;
import com.example.sela.finalprojectandroid1.Model.CurrentQuestion;
import com.example.sela.finalprojectandroid1.QuestionFragment;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Common
{
    public static final int TOTAL_TIME = 20*60*1000; //20 min
    public static List<Question> questionList = new ArrayList<>();
    public static List<CurrentQuestion> answerSheetList = new ArrayList<>();
    public static Category selectedCategory = new Category();

    public static CountDownTimer countDownTimer;

    public static int right_Answer_Count = 0;
    public static int wrong_Answer_Count = 0;
    public static ArrayList<QuestionFragment> fragmentList = new ArrayList<>();
    public static TreeSet<String> selected_values = new TreeSet<>();





    public enum ANSWER_TYPE
    {
        NO_ANSWER,
        WRONG_ANSWER,
        RIGHT_ANSWER
    }
}
