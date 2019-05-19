package com.blabla.structurize;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blabla.structurize.CategoryPackage.Category;


public class AddCategoryActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler;
    private EditText editTextName;
    private EditText editTextImportant;
    private EditText editTextColor;
    private TextInputLayout textInputLayoutColor;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initComponents();
    }

    private void initComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHandler = new DataBaseHandler(this);
        initText();
        initButton();
        //initColorPicker();
    }

    /*private void initColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(AddCategoryActivity.this);
        colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int pickedColor) {
                color = String.valueOf(pickedColor);
            }

            @Override
            public void onCancel(){
            }
        })
                .show();

    }*/

    private void initButton() {
        Button buttonSubmit = findViewById(R.id.content_add_category_button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String important = editTextImportant.getText().toString();
                textInputLayoutColor.setErrorEnabled(false);
                Category category = new Category(name, important, color);
                dataBaseHandler.addCategory(category);
                Toast.makeText(AddCategoryActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initText() {
        editTextName = findViewById(R.id.content_add_category_name);
        editTextImportant = findViewById(R.id.content_add_category_important);
        editTextColor = findViewById(R.id.content_add_category_color);
        textInputLayoutColor = findViewById(R.id.content_add_category_text_input_layout_color);

    }

}
