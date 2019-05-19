package com.blabla.structurize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blabla.structurize.CategoryPackage.Category;
import com.blabla.structurize.TaskPackage.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "category.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "id";
    public static final String COLUMN_CATEGORY_NAME = "name";
    public static final String COLUMN_CATEGORY_IMPORTANT = "important";
    public static final String COLUMN_CATEGORY_COLOR = "color";

    public static final String TABLE_TASK = "task";
    public static final String COLUMN_TASK_ID = "id";
    public static final String COLUMN_TASK_NAME = "name";
    public static final String COLUMN_TASK_DESCRIPTION = "description";
    public static final String COLUMN_TASK_DATE = "date";
    public static final String COLUMN_TASK_ID_CATEGORY = "id_category";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategory = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY," +
                COLUMN_CATEGORY_NAME + " TEXT," +
                COLUMN_CATEGORY_IMPORTANT + " TEXT," +
                COLUMN_CATEGORY_COLOR + " TEXT)";
        db.execSQL(createCategory);
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMPORTANT + ", " + COLUMN_CATEGORY_COLOR + ") VALUES ('Settings','Important','#b71c1c')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMPORTANT + ", " + COLUMN_CATEGORY_COLOR + ") VALUES ('Page','Important','#004d40')");
        db.execSQL("INSERT INTO " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_NAME + ", " + COLUMN_CATEGORY_IMPORTANT + ", " + COLUMN_CATEGORY_COLOR + ") VALUES ('Bla-bla','Unimportant','#e65100')");

        String createTask = "CREATE TABLE " + TABLE_TASK + "(" +
                COLUMN_TASK_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TASK_NAME + " TEXT," +
                COLUMN_TASK_DESCRIPTION + " TEXT," +
                COLUMN_TASK_DATE + " TEXT," +
                COLUMN_TASK_ID_CATEGORY + " INTEGER)";
        db.execSQL(createTask);
        db.execSQL("INSERT INTO " + TABLE_TASK + "(" + COLUMN_TASK_NAME + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DATE + ", " + COLUMN_TASK_ID_CATEGORY + ") VALUES ('Adam','Текст – группа предложений, объединённых в одно целое темой и основной мыслью. Предложения в тексте связаны по смыслу и при помощи языковых средств связи (повтор, местоимения, синонимы и др.). Тема – то, о чём говориться в тексте, часто отражена в его заголовке. Основная мысль – обычно передаёт отношение автора к предмету речи, его оценку изображаемого. В художественном тексте часто используются предложения со значением оценки.','02-01-0001',2)");
        db.execSQL("INSERT INTO " + TABLE_TASK + "(" + COLUMN_TASK_NAME + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DATE + ", " + COLUMN_TASK_ID_CATEGORY + ") VALUES ('Jesus','Текст – группа предложений, объединённых в одно целое темой и основной мыслью. Предложения в тексте связаны по смыслу и при помощи языковых средств связи (повтор, местоимения, синонимы и др.). Тема – то, о чём говориться в тексте, часто отражена в его заголовке. Основная мысль – обычно передаёт отношение автора к предмету речи, его оценку изображаемого. В художественном тексте часто используются предложения со значением оценки.','02-11-0002',3)");
        db.execSQL("INSERT INTO " + TABLE_TASK + "(" + COLUMN_TASK_NAME + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DATE + ", " + COLUMN_TASK_ID_CATEGORY + ") VALUES ('Eva','Текст – группа предложений, объединённых в одно целое темой и основной мыслью. Предложения в тексте связаны по смыслу и при помощи языковых средств связи (повтор, местоимения, синонимы и др.). Тема – то, о чём говориться в тексте, часто отражена в его заголовке. Основная мысль – обычно передаёт отношение автора к предмету речи, его оценку изображаемого. В художественном тексте часто используются предложения со значением оценки.','03-01-0001',2)");
        db.execSQL("INSERT INTO " + TABLE_TASK + "(" + COLUMN_TASK_NAME + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DATE + ", " + COLUMN_TASK_ID_CATEGORY + ") VALUES ('P*tin','Bla-bla','04-01-0001',3)");
        db.execSQL("INSERT INTO " + TABLE_TASK + "(" + COLUMN_TASK_NAME + ", " + COLUMN_TASK_DESCRIPTION + ", " + COLUMN_TASK_DATE + ", " + COLUMN_TASK_ID_CATEGORY + ") VALUES ('Durov','Bla-bla','05-01-0001',1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Task getTask(int id){
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM "+TABLE_TASK+ " WHERE id = "+id,null);
        if(cursor.moveToNext()){
            Task task = new Task();
            task.setId(cursor.getInt(0));
            task.setName(cursor.getString(1));
            task.setDescription(cursor.getString(2));
            task.setDate(cursor.getString(3));
            task.setIdCategory(cursor.getInt(4));
            return task;
        }
        return null;
    }

    public List<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_TASK, null);
        if (cursor.moveToNext()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDate(cursor.getString(3));
                task.setIdCategory(cursor.getInt(4));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        return Arrays.asList(sortAllTasks(tasks));
    }

    public Task[] sortAllTasks(ArrayList<Task> tasks1){
        Task[] tasks = tasks1.toArray(new Task[0]);
        for (int i = 0; i < tasks.length; i++) {
            for (int j = i; j < tasks.length; j++) {
                if(getCategoryById(tasks[i].getIdCategory()).getId()>=getCategoryById(tasks[j].getIdCategory()).getId()){
                    Task t = tasks[i];
                    tasks[i] = tasks[j];
                    tasks[j]=t;
                }
            }
        }
        return tasks;
    }

    public long addTask(Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK_NAME,task.getName());
        contentValues.put(COLUMN_TASK_DESCRIPTION,task.getDescription());
        contentValues.put(COLUMN_TASK_DATE,task.getDate());
        contentValues.put(COLUMN_TASK_ID_CATEGORY,task.getIdCategory());
        return this.getWritableDatabase().insert(TABLE_TASK, null, contentValues);
    }

    public Category getCategoryById(int id){
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE id = " + id, null);
        if(cursor.moveToNext()){
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            category.setImportant(cursor.getString(2));
            category.setColor(cursor.getString(3));
            return category;
        }
        return null;
    }

    public long addCategory(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, category.getName());
        contentValues.put(COLUMN_CATEGORY_IMPORTANT, category.getImportant());
        contentValues.put(COLUMN_CATEGORY_COLOR, category.getColor());
        return this.getWritableDatabase().insert(TABLE_CATEGORY, null, contentValues);
    }

    public List<Category> getAllCategories(){
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM "+TABLE_CATEGORY ,null);
        ArrayList<Category> categories = new ArrayList<>();
        if (cursor.moveToNext()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setImportant(cursor.getString(2));
                category.setColor(cursor.getString(3));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;
    }

}
