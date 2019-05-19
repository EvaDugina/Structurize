package com.blabla.structurize.TaskPackage;

public class Task {
    private int id;
    private String name;
    private String description;
    private String date;
    private int idCategory;

    public Task() {
    }

    public Task(int id, String name, String description, String date, int idCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.idCategory = idCategory;
    }

    public Task(String name, String description, String date, int idCategory) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.idCategory = idCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
