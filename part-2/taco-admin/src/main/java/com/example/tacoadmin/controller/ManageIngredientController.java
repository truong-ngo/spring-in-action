package com.example.tacoadmin.controller;

import com.example.tacoadmin.model.Ingredient;
import com.example.tacoadmin.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/ingredients")
@RequiredArgsConstructor
public class ManageIngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public String ingredientsAdmin(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredientsAdmin";
    }

    @PostMapping
    public String addIngredient(Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return "redirect:/admin/ingredients";
    }
}
