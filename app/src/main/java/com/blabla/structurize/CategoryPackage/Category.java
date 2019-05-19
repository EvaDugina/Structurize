package com.blabla.structurize.CategoryPackage;

public class Category {
    private int id;
    private String name;
    private String important;
    private String color;

    public Category() {
    }

    public Category(String name, String important, String color) {
        this.name = name;
        this.important = important;
        this.color = color;
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

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }
}
