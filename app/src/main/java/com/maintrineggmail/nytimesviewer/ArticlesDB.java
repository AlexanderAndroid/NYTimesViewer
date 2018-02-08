package com.maintrineggmail.nytimesviewer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticlesDB {
    private static ArticlesDB sArticlesDB;
    private List<Articles> mArticles;

    public static ArticlesDB get(Context context) {
        if (sArticlesDB == null) {
            sArticlesDB = new ArticlesDB(context);
        }
        return sArticlesDB;
    }

    private ArticlesDB(Context context) {
        mArticles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Articles article = new Articles();
            article.setArticle("Статья #" + i);
            mArticles.add(article);
        }
    }

    public List<Articles> getArticles() {
        return mArticles;
    }

    public Articles getArticle(UUID id) {
        for (Articles article : mArticles) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }
}
