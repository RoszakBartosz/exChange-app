package model.history;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class ExChangeHistoryLog {
    private BigDecimal amountFromOperation;
    private BigDecimal midInTheseTime;
    private String choosenCurrency;
    private LocalDateTime dateTimeFromOperation;
}
