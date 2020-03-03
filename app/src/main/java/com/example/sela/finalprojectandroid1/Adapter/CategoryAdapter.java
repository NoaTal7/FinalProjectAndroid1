package com.example.sela.finalprojectandroid1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sela.finalprojectandroid1.Common.Common;
import com.example.sela.finalprojectandroid1.Model.Category;
import com.example.sela.finalprojectandroid1.QuestionActivity;
import com.example.sela.finalprojectandroid1.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>
{

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories)
    {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_Category_name.setText(categories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView card_category;
        TextView txt_Category_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_category = (CardView) itemView.findViewById(R.id.card_category);
            txt_Category_name = (TextView)itemView.findViewById(R.id.txt_category_name);
            card_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.selectedCategory = categories.get(getAdapterPosition()); //assign the current category
                    Intent intent = new Intent(context, QuestionActivity.class);
                    context.startActivity(intent);

                }
            });


        }
    }
}
