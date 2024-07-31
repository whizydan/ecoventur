package com.example.ecoventur.ui.ecoeducation;

public class Tutorial{
    private String title;
    private String imageUrl;
    private String tutoUrl;

    // Required default constructor for Firebase
    public Tutorial() {
        // Default constructor required for Firebase
    }

    public Tutorial(String title, String imageUrl, String tutoUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.tutoUrl = tutoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTutoUrl() {
        return tutoUrl;
    }

    public String getTitle() {
        return title;
    }

    // Setters if needed
}

