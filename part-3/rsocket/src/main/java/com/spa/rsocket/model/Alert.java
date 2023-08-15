package com.spa.rsocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {
    private Level level;
    private String orderedBy;
    private Instant orderedAt;
    public enum Level {
        YELLOW, ORANGE, RED, BLACK
    }
}
