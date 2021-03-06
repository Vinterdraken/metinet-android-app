package com.example.lp.coursandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lp.coursandroid.Activities.Category.CategoryListActivity;
import com.example.lp.coursandroid.Activities.Post.PostListActivity;
import com.example.lp.coursandroid.R;

public class MainActivity extends AppCompatActivity{

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeView();
        setContentView(linearLayout);
    }

    private void makeView() {

        Button getPostsButton = makeButton(
                R.string.get_posts_button_text,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPosts();
                    }
                }
        );

        Button getCategoriesButton = makeButton(
                R.string.get_categories_button_text,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCategories();
                    }
                }
        );

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(getPostsButton);
        linearLayout.addView(getCategoriesButton);
    }
    private Button makeButton(int buttonText, View.OnClickListener onClickListener){
        Button newButton = new Button(this);
        newButton.setText(buttonText);
        newButton.setOnClickListener(onClickListener);

        return newButton;
    }

    private void getPosts(){
        Intent intent = new Intent(this, PostListActivity.class);
        startActivity(intent);
    }
    public void getCategories(){
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivity(intent);
    }

}
