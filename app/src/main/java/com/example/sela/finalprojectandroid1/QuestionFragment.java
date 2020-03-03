package com.example.sela.finalprojectandroid1;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sela.finalprojectandroid1.Common.Common;
import com.example.sela.finalprojectandroid1.DBHelper.Question;
import com.example.sela.finalprojectandroid1.Interface.IQuestion;
import com.example.sela.finalprojectandroid1.Model.CurrentQuestion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment implements IQuestion
{
    TextView txt_question_text;
    CheckBox checkBox1, checkBox2, checkBox3, checkbox4;
    FrameLayout layout_Image;
    ProgressBar progressBar;

    Question question;
    int questionIndex = -1;



    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_question, container, false);


        //get Question
        questionIndex = getArguments().getInt("index", -1);
        question = Common.questionList.get(questionIndex);


        if (question != null)
        {
            layout_Image = (FrameLayout) itemView.findViewById(R.id.layout_image);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar);
            if (question.isImageQuestion())
            {
                ImageView img_question = (ImageView)itemView.findViewById(R.id.img_question);
                Picasso.get().load(question.getQuestionImage()).into(img_question, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else
            {
                layout_Image.setVisibility(View.GONE);
            }

            //view
            txt_question_text = (TextView) itemView.findViewById(R.id.txt_question_text);
            txt_question_text.setText(question.getQuestionText());

            checkBox1 = (CheckBox) itemView.findViewById(R.id.checkbox1);
            checkBox1.setText(question.getAnswerA());
            checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true)
                    {
                        Common.selected_values.add(checkBox1.getText().toString());
                    }
                    else
                    {
                        Common.selected_values.remove(checkBox1.getText().toString());
                    }
                }
            });
            checkBox2 = (CheckBox) itemView.findViewById(R.id.checkbox2);
            checkBox2.setText(question.getAnswerB());
            checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true)
                    {
                        Common.selected_values.add(checkBox2.getText().toString());
                    }
                    else
                    {
                        Common.selected_values.remove(checkBox2.getText().toString());
                    }
                }
            });

            checkBox3 = (CheckBox) itemView.findViewById(R.id.checkbox3);
            checkBox3.setText(question.getAnswerC());
            checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true)
                    {
                        Common.selected_values.add(checkBox3.getText().toString());
                    }
                    else
                    {
                        Common.selected_values.remove(checkBox3.getText().toString());
                    }
                }
            });
            checkbox4 = (CheckBox) itemView.findViewById(R.id.checkbox4);
            checkbox4.setText(question.getAnswerD());
            checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true)
                    {
                        Common.selected_values.add(checkbox4.getText().toString());
                    }
                    else
                    {
                        Common.selected_values.remove(checkbox4.getText().toString());
                    }
                }
            });


        }
            return itemView;
        }


    @Override
    public CurrentQuestion getSelectedAnswer()
    {
        //This function will return the state of an answer
        //Right, Wrong or normal
        CurrentQuestion currentQuestion = new CurrentQuestion(questionIndex,Common.ANSWER_TYPE.NO_ANSWER); // default no answer
        StringBuilder result = new StringBuilder();
        if (Common.selected_values.size() > 1)
        {
            //if multi choice
            //split answer to array
            //ex: arr[0] = A. new york
            //ex: arr[1] = B. paris
            Object[] arrayAnswer = Common.selected_values.toArray();
            for (int i = 0; i<arrayAnswer.length; i++)
            {
                if (i < arrayAnswer.length-1)
                {
                    result.append(new StringBuilder(((String)arrayAnswer[i]).substring(0,1)).append(","));

                }
                else
                {
                    result.append(new StringBuilder((String)arrayAnswer[i]).substring(0,1));
                }
            }
        }else if (Common.selected_values.size() == 1)
        {
            //if only one choice
            Object[] arrayAnswer = Common.selected_values.toArray();
            result.append((String)arrayAnswer[0]).substring(0,1);
        }


        if (question != null)
        {
           //compare user answer to the correct answer
           if (!TextUtils.isEmpty(result))
           {
               // Test Noa
               String correctChoice = question.getCorrectAnswer().toString();
               char choice = result.toString().charAt(0);

               if (choice == correctChoice.charAt(0))
               {
                   currentQuestion.setType(Common.ANSWER_TYPE.RIGHT_ANSWER);
                   // Test noa - count how many right answer user got
                   Common.right_Answer_Count++;
                   //suggestion to add variable score to the FireBase;
               }else {
                   currentQuestion.setType(Common.ANSWER_TYPE.WRONG_ANSWER);
               }
           }
           else
           {
               currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
           }
        }else{
            Toast.makeText(getContext(), "Cannot Get Question", Toast.LENGTH_SHORT).show();
            currentQuestion.setType(Common.ANSWER_TYPE.NO_ANSWER);
        }
        Common.selected_values.clear(); //Always clear selected_value when compare done.

        return currentQuestion;
    }

    @Override
    public void showCorrectAnswer()
    {
        //Bold Correct Answer
        //pattern A,B
        String[] correctAnswer = question.getCorrectAnswer().split(",");
        for (String answer:correctAnswer)
        {
            if (answer.equals("A"))
            {
                checkBox1.setTypeface(null, Typeface.BOLD);
                checkBox1.setTextColor(Color.RED);
            }
            else if (answer.equals("B"))
            {
                checkBox2.setTypeface(null, Typeface.BOLD);
                checkBox2.setTextColor(Color.RED);
            }
            else if (answer.equals("C"))
            {
                checkBox3.setTypeface(null, Typeface.BOLD);
                checkBox3.setTextColor(Color.RED);
            }
            else if (answer.equals("D"))
            {
                checkbox4.setTypeface(null, Typeface.BOLD);
                checkbox4.setTextColor(Color.RED);
            }

        }
    }

    @Override
    public void disableAnswer()
    {
        checkBox1.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        checkbox4.setEnabled(false);
    }

    @Override
    public void resetQuestion   ()
    {

        //enable all checkboxes
        checkBox1.setEnabled(true);
        checkBox2.setEnabled(true);
        checkBox3.setEnabled(true);
        checkbox4.setEnabled(true);
        //reset all checked checkboxes
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkbox4.setChecked(false);
        //remove the bold text back to default
        checkBox1.setTypeface(null, Typeface.NORMAL);
        checkBox1.setTextColor(Color.BLACK);
        checkBox2.setTypeface(null, Typeface.NORMAL);
        checkBox2.setTextColor(Color.BLACK);
        checkBox3.setTypeface(null, Typeface.NORMAL);
        checkBox3.setTextColor(Color.BLACK);
        checkbox4.setTypeface(null, Typeface.NORMAL);
        checkbox4.setTextColor(Color.BLACK);
    }
}
