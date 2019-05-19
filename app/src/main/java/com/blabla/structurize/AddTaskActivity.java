package com.blabla.structurize;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blabla.structurize.CategoryPackage.Category;
import com.blabla.structurize.TaskPackage.Task;

import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private List<Category> masCategories;
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextDate;
    private Spinner spinnerCategory;
    private DataBaseHandler dataBaseHandler;
    private TextInputLayout textInputLayoutDate;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponents();
    }

    private void initComponents() {
        initEditText();
        dataBaseHandler = new DataBaseHandler(this);
        masCategories = dataBaseHandler.getAllCategories();
        initSpinner();
        initButtons();
    }

    private void initButtons() {
        Button buttonSubmit = findViewById(R.id.content_add_task_button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString(), description = editTextDescription.getText().toString(), date = editTextDate.getText().toString();
                int dateLength = date.length();
                boolean flag = dateLength != 10 || date.equals("") || !(date.contains(".") || date.contains("-")) || name.equals("") || description.equals("");
                if (dateLength != 10 || date.equals("") || !(date.contains(".") || date.contains("-"))) {
                    textInputLayoutDate.setError("Incorrect date");
                    textInputLayoutDate.setErrorEnabled(true);
                }
                if (name.equals("")) {
                    textInputLayoutName.setError("Incorrect name");
                    textInputLayoutName.setErrorEnabled(true);
                }
                if (description.equals("")) {
                    textInputLayoutDescription.setError("Incorrect description");
                    textInputLayoutDescription.setErrorEnabled(true);
                }
                if (!flag) {
                    textInputLayoutName.setErrorEnabled(false);
                    textInputLayoutDescription.setErrorEnabled(false);
                    String[] str = new String[3];
                    if(date.contains("-")) {
                        str = date.split("-");
                    }
                    if(date.contains(".")) {
                        str = date.split(".");
                    }
                    if(str[0].length()==2 && str[1].length()==2 && str[2].length()==4) {
                        textInputLayoutDate.setErrorEnabled(false);
                        Category category = (Category) spinnerCategory.getSelectedItem();
                        Task task = new Task(name, description, date, category.getId());
                        dataBaseHandler.addTask(task);
                        Toast.makeText(AddTaskActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    textInputLayoutDate.setError("Incorrect date");
                    textInputLayoutDate.setErrorEnabled(true);
                }
            }
        });
    }

    private void initSpinner() {
        spinnerCategory = findViewById(R.id.content_add_task_spinner_category);
        spinnerCategory.setAdapter(new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, masCategories));
    }

    private void initEditText() {
        editTextName = findViewById(R.id.content_add_task_edit_text_name);
        editTextDescription = findViewById(R.id.content_add_task_edit_text_description);
        editTextDate = findViewById(R.id.content_add_task_edit_text_date);
        textInputLayoutDate = findViewById(R.id.content_add_task_text_input_layout_date);
        textInputLayoutName = findViewById(R.id.content_add_task_text_input_layout_name);
        textInputLayoutDescription = findViewById(R.id.content_add_task_text_input_layout_description);
    }

}
