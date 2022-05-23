package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Invoice {
    private BigDecimal subtotal;
    private BigDecimal shipping;
    private BigDecimal vat;
    private ArrayList<Discount> discounts;
    private BigDecimal total;

    public Invoice() {
        discounts = new ArrayList<>();
    }

    public Invoice(BigDecimal subtotal, BigDecimal shipping, BigDecimal vat, ArrayList<Discount> discounts, BigDecimal total) {
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.vat = vat;
        this.discounts = discounts;
        this.total = total;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public String discountsAsString() {
        return discounts.stream().map(Discount::toString).collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return "Subtotal: $" + subtotal + "\nShipping: $" + shipping + "\nVAT: $"
                + vat + "\n\nDiscounts:\n" + discountsAsString() + "\nTotal: $" + total + "\n";
    }
}
