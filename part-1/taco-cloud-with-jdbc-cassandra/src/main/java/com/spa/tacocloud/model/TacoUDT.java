package com.spa.tacocloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("taco")
public class TacoUDT {
    private String name;
    private List<IngredientUDT> ingredients;
}
