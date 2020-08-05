package com.example.foodmood;

public class feedbackTemplate {
String user,comment;
float rating;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public feedbackTemplate( String category, int count) {
        this.count = count;
        this.category = category;
    }

    int count;
String category;



    public feedbackTemplate(String user, String comment, float rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
