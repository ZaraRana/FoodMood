package com.example.foodmood;

import android.widget.RatingBar;

public class Recipe {
    public Recipe(int id,String name,String ingredients, String description, String category, String image) {

        this.id = id;
        this.name = name;
        this.ingredients=ingredients;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public Recipe(int id,String name,String ingredients, String description, String category, String image,String link) {

        this.id = id;
        this.name = name;
        this.ingredients=ingredients;
        this.description = description;
        this.category = category;
        this.image = image;
        this.link = link;
    }

    public Recipe(int id,String name,String ingredients) {

        this.id = id;
        this.name = name;
        this.ingredients=ingredients;
        this.description = description;

    }
    int id;
    String name;
    String ingredients;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }

    String description;
    String category;
    String image;
    float ratingBar;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String link;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
