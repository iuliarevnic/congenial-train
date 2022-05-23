package model;

import java.math.BigDecimal;

public class ShippingRate {
    private String country;
    private BigDecimal fee;

    public ShippingRate(String country, BigDecimal fee) {
        this.country = country;
        this.fee = fee;
    }

    public String getCountry() {
        return country;
    }

    public BigDecimal getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return country + " $" + fee;
    }
}
