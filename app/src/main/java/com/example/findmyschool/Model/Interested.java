package com.example.findmyschool.Model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Interested implements Serializable {
    String title;
    String description;
    Drawable drawable;
    boolean isSelected;

    public Interested(String title, String description, Drawable drawable, boolean isSelected) {
        this.title = title;
        this.description = description;
        this.drawable = drawable;
        this.isSelected = isSelected;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
