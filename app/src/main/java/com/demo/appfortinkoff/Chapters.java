package com.demo.appfortinkoff;

public enum Chapters {
    CHAPTER_LATEST("latest"),
    CHAPTER_HOT("hot"),
    CHAPTER_TOP("top");

    private String name;

    Chapters(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
