package com.example.tacoadmin.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

  private String id;
  private String name;
  private Type type;

  public enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

}