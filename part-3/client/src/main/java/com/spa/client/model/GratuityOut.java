package com.spa.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GratuityOut {
    private BigDecimal billTotal;
    private int percent;
    private BigDecimal gratuity;
}
