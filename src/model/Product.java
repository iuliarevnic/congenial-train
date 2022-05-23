package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    private String name;
    private BigDecimal price;
    private String country;
    private BigDecimal weight;

    public Product(String name, BigDecimal price, String country, BigDecimal weight) {
        this.name = name;
        this.price = price;
        this.country = country;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCountry() {
        return country;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name + " - $" + price.setScale(2, RoundingMode.DOWN) + "\n";
    }
}
