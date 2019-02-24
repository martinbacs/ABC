package com.android.project.abcappen.words;


public class WordImage {
    private String name;
    private int resources;

    public WordImage(String name, int resources){
        this.name = name;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }
}
