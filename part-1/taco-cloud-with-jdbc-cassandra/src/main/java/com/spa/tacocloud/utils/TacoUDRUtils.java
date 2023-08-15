package com.spa.tacocloud.utils;

import com.spa.tacocloud.model.Ingredient;
import com.spa.tacocloud.model.IngredientUDT;
import com.spa.tacocloud.model.Taco;
import com.spa.tacocloud.model.TacoUDT;

import java.util.List;
import java.util.stream.Collectors;

public class TacoUDRUtils {
    public static TacoUDT toTacoUDT(Taco taco) {
        return new TacoUDT(taco.getName(), taco.getIngredients());
    }

    public static List<IngredientUDT> toIngredientUDTs(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(TacoUDRUtils::toIngredientUDT)
                .collect(Collectors.toList());
    }

    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }
}
