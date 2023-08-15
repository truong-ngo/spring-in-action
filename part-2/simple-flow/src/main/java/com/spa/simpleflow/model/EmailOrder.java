package com.spa.simpleflow.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailOrder {
    private String email;
    private List<Taco> tacos = new ArrayList<>();

    public EmailOrder(String email) {
        this.email = email;
    }

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
