package com.example.sela.finalprojectandroid1.Interface;

import com.example.sela.finalprojectandroid1.Model.CurrentQuestion;

public interface IQuestion
{
    CurrentQuestion getSelectedAnswer(); //get the selected answer from user select
    void showCorrectAnswer(); //boldCorrectAnswerText
    void disableAnswer(); //disable all check Box
    void resetQuestion(); //reset all function on question

}
