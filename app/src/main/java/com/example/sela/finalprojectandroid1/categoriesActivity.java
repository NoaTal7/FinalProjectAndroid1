package com.example.sela.finalprojectandroid1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.sela.finalprojectandroid1.Adapter.CategoryAdapter;
import com.example.sela.finalprojectandroid1.Common.spaceDecoration;
import com.example.sela.finalprojectandroid1.DBHelper.DBHelper;

public class categoriesActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_Category;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        toolbar = (Toolbar   )findViewById(R.id.toolBar);
        toolbar.setTitle("פרוייקט סופי אנדרואיד 1 אצל אפי פרופוס");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        setSupportActionBar(toolbar); find out why app is crashing in this line


        recycler_Category = (RecyclerView)findViewById(R.id.recyclerCategory);
        recycler_Category.setHasFixedSize(true);
        recycler_Category.setLayoutManager(new GridLayoutManager(this, 2));

        //now we will edit the screen
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels / 8;
        CategoryAdapter adapter = new CategoryAdapter(categoriesActivity.this,DBHelper.getInstance(this).getAllCategories());
        int spaceInPixel = 4;
        recycler_Category.addItemDecoration(new spaceDecoration(spaceInPixel));
        recycler_Category.setAdapter(adapter);
    }
}
