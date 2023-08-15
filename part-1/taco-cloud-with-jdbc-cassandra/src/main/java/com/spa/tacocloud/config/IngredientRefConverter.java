package com.spa.tacocloud.config;

import com.spa.tacocloud.model.IngredientRef;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientRefConverter implements Converter<String, IngredientRef> {
    @Override
    public IngredientRef convert(String source) {
        return new IngredientRef(source);
    }
}
