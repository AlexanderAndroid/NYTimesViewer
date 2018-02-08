package com.maintrineggmail.nytimesviewer;


import java.util.UUID;

public class Articles {
    private String mName;
    private UUID mId;

    public Articles() {
        mId = UUID.randomUUID();
    }

    public String getArticle() {
        return mName;
    }

    public UUID getId() {
        return mId;
    }

    public void setArticle(String name) {
        mName = name;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
