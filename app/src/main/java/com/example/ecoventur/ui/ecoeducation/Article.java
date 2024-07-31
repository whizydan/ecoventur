package com.example.ecoventur.ui.ecoeducation;

public class Article {
    private String title;
    private String imageUrl;
    private String articleUrl;

    // Required default constructor for Firebase
    public Article() {
        // Default constructor required for Firebase
    }

    public Article(String title, String imageUrl, String articleUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getTitle() {
        return title;
    }

    // Setters if needed
}



