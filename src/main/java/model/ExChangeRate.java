package model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
public class ExChangeRate {
    private long id;
    private String currency;
    private String code;
    private BigDecimal mid;
}
