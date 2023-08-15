package com.spa.simpleflow.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private String name;
    private List<String> ingredients;

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public Taco(String name) {
        this.name = name;
    }
}
