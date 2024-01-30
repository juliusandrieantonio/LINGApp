package com.example.lingapp.utils;
public class HomeFragmentModel {
    private int drawable;
    private String Description, content, title;

    public HomeFragmentModel() {

    }
    public HomeFragmentModel(int drawable, String content, String title) {
        this.drawable = drawable;
        this.content = content;
        this.title =title;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getDescription() {
        return Description;
    }
}
