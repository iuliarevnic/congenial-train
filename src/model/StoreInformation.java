package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreInformation {
    private final List<Product> products = new ArrayList<>(Arrays.asList(
            new Product("Mouse", new BigDecimal("10.99"), "RO", new BigDecimal("0.2")),
            new Product("Keyboard", new BigDecimal("40.99"), "UK", new BigDecimal("0.7")),
            new Product("Monitor", new BigDecimal("164.99"), "US", new BigDecimal("1.9")),
            new Product("Webcam", new BigDecimal("84.99"), "RO", new BigDecimal("0.2")),
            new Product("Headphones", new BigDecimal("59.99"), "US", new BigDecimal("0.6")),
            new Product("Desk Lamp", new BigDecimal("89.99"), "UK", new BigDecimal("1.3"))));
    private final ArrayList<ShippingRate> shippingRates = new ArrayList<>(Arrays.asList(
            new ShippingRate("RO", new BigDecimal("1")),
            new ShippingRate("UK", new BigDecimal("2")),
            new ShippingRate("US", new BigDecimal("3"))));
    private final ArrayList<Discount> discounts = new ArrayList<>(Arrays.asList(
            new Discount("10% off keyboards", new BigDecimal("0")),
            new Discount("2 x monitors => desk lamp at half price", new BigDecimal("0")),
            new Discount("$10 off shipping for 2 or more items purchased", new BigDecimal("10"))));

    public List<Product> getProducts() {
        return products;
    }

    public ArrayList<ShippingRate> getShippingRates() {
        return shippingRates;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

}
