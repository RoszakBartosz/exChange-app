package service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {
    public BigDecimal multiply(BigDecimal mid, BigDecimal value){
        if (mid==null||value==null) {
           throw new NullPointerException("mid or value are null: "+mid.toString() + " , " +value.toString());
        }
        if (mid.signum()<=0||value.signum()<=0){
            throw new NullPointerException("mid or value is equal to zero, or is negate: "+mid.toString() + " , " +value.toString());
        }
        BigDecimal resultFromDivide = mid.multiply(value);

        if (resultFromDivide.signum()<=0){
            throw new NullPointerException("the result from divide cannot be null, but is: "+resultFromDivide.toString());
        }
        return resultFromDivide;
    }

}
