package com.example.sela.finalprojectandroid1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sela.finalprojectandroid1.Common.Common;
import com.example.sela.finalprojectandroid1.Model.CurrentQuestion;
import com.example.sela.finalprojectandroid1.R;

import java.util.List;

public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.MyViewHolder> {

    Context context;
    List<CurrentQuestion> currentQuestionList; //contain index of question and a state of answer (wrong, right, no answer).

    public AnswerSheetAdapter(Context context, List<CurrentQuestion> currentQuestionList)
    {
        this.context = context;
        this.currentQuestionList = currentQuestionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println(parent);
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_grid_answer_sheet_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (currentQuestionList.get(position).getType() == Common.ANSWER_TYPE.RIGHT_ANSWER)
        {
            holder.question_Item.setBackgroundResource(R.drawable.grid_question_right_answer);
        }else if(currentQuestionList.get(position).getType() == Common.ANSWER_TYPE.WRONG_ANSWER)
        {
            holder.question_Item.setBackgroundResource(R.drawable.grid_question_wrong_answer);
        }else if (currentQuestionList.get(position).getType() == Common.ANSWER_TYPE.NO_ANSWER)
        {
            System.out.println(holder.question_Item);
            holder.question_Item.setBackgroundResource(R.drawable.grid_question_no_answer);
        }

    }

    @Override
    public int getItemCount() {
        return currentQuestionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        View question_Item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            question_Item = itemView.findViewById(R.id.question_item);
        }
    }

}
