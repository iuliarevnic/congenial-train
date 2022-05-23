package model;

import java.math.BigDecimal;

public class Discount {
    private String description;
    private BigDecimal value;


    public Discount(String description, BigDecimal value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return description + ": -$" + value + "\n";
    }
}
