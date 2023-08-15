package com.example.tacoadmin.service;

import com.example.tacoadmin.model.Ingredient;

public interface IngredientService {
    Iterable<Ingredient> findAll();
    Ingredient addIngredient(Ingredient ingredient);
}
