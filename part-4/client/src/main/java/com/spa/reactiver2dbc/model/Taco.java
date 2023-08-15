package com.spa.reactiver2dbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Table("taco")
@NoArgsConstructor
public class Taco {
    @Id
    private Long id;
    private String name;
    @Column("ingredient_ids")
    private String ingredientIds;
    @Transient
    private transient Set<Ingredient> ingredients;

    public Taco(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getStringIngredients() {
        if (ingredients == null) return null;
        return ingredients.stream()
                .map(Ingredient::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public void setStringIngredients() {
        setIngredientIds(getStringIngredients());
    }
}
