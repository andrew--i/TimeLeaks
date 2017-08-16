package org.ai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TimeLeak {
    private String host;
    private BigDecimal duration;
    private LocalDateTime timestamp;
}
