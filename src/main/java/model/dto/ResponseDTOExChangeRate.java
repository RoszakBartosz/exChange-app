package model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@Builder
public class ResponseDTOExChangeRate {
    private long id;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String codeFrom;
    private String codeTo;
    private BigDecimal mid;

}
