package com.blabla.structurize.TaskPackage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blabla.structurize.CategoryPackage.Category;
import com.blabla.structurize.DataBaseHandler;
import com.blabla.structurize.R;

public class TaskActivity extends AppCompatActivity {
    private int id;
    private Task task;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initComponents();

    }

    private void initComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getExtras();
        initTextView();
    }

    private void initTextView() {
        TextView textViewName = findViewById(R.id.content_task_text_view_name);
        TextView textViewDescription = findViewById(R.id.content_task_text_view_description);
        TextView textViewDate = findViewById(R.id.content_task_text_view_date);
        TextView textViewCategory = findViewById(R.id.content_task_text_view_category);

        textViewName.setText(task.getName());
        textViewDescription.setText(task.getDescription());
        textViewDate.setText(task.getDate());
        textViewCategory.setText(category.getName());
        textViewCategory.setTextColor(Color.parseColor(category.getColor()));
    }

    private void getExtras() {
        id = this.getIntent().getIntExtra(TaskAdapter.EXTRA_TASK_ID, -1);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        task = dataBaseHandler.getTask(id);
        category = dataBaseHandler.getCategoryById(task.getIdCategory());
    }


}
