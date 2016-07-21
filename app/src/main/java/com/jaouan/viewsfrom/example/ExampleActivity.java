package com.jaouan.viewsfrom.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaouan.viewsfrom.ViewIteration;
import com.jaouan.viewsfrom.Views;

import java.util.List;

public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        final ViewGroup group1 = (ViewGroup) findViewById(R.id.group1);
        final ViewGroup group2 = (ViewGroup) findViewById(R.id.group2);

        // List views.
        List<View> views = Views.from(group1)
                .includingFromViews()
                .find();
        Toast.makeText(this, "Count : " + views.size(), Toast.LENGTH_LONG).show();

        // Iterate views.
        Views.from(group1)
                .includingFromViews()
                .andFrom(group2)
                .forEach(new ViewIteration() {
                    @Override
                    public void onView(final View view, final int viewIndex, final int viewsCount) {
                        Log.i("ViewsFromLibExample", (viewIndex + 1) + " of " + viewsCount + " : " + view.getClass().getSimpleName());
                    }
                });

        // Animate views.
        Views.from(group1, group2)
                .withVisibility(View.VISIBLE)
                .includingFromViews()
                .animateWith(ExampleActivity.this, R.anim.fadein)
                .withDelayBetweenEachChild(250)
                .withViewsVisibilityBeforeAnimation(View.INVISIBLE)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("ViewsFromLibExample", "It's finally over.");
                    }
                })
                .start();
    }
}
