package com.example.exchange_app.model.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
@Builder
public class ResponseDTOExChangeRate implements Serializable {
    @Id
    private long id;
    @NotNull(message = "amountFrom cannot be null")
    @Positive(message = "amountFrom must be greater than 0")
    private BigDecimal amountFrom;
    @NotNull(message = "amountTo cannot be null")
    @Positive(message = "amountTo must be greater than 0")
    private BigDecimal amountTo;
    @NotBlank(message = "codeFrom cannot be empty! ")
    private String codeFrom;
    @NotBlank(message = "codeTo cannot be empty! ")
    private String codeTo;
    private BigDecimal mid;

}
