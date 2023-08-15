package com.spa.reactiver2dbc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Table("taco_order")
public class TacoOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    @Column("taco_ids")
    private String tacoIds;

    @Transient
    private transient List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }

    public String getStringTacos() {
        if (tacos == null) return null;
        return tacos.stream()
                .map(Taco::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public void setStringTacos() {
        setTacoIds(getStringTacos());
    }

    public List<Long> getTacoIdList() {
        if (tacoIds == null) return null;
        return Arrays.stream(tacoIds.split(",")).map(Long::valueOf).toList();
    }
}
