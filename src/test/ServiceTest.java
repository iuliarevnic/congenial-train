package test;

import model.Discount;
import model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Service;

import java.math.BigDecimal;


class ServiceTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void computeInvoiceNoOffers() {
        //add only one product to cart, 1 x Mouse
        //subtotal: $10.99
        //shipping: $2
        //VAT: $2.08
        //no discounts
        //total: $15.07
        service.addProductToCart(1);
        Invoice invoice = this.service.getInvoice();
        assert invoice.getSubtotal().equals(new BigDecimal("10.99"));
        assert invoice.getShipping().equals(new BigDecimal("2.00"));
        assert invoice.getDiscounts().size() == 0;
        assert invoice.getVat().equals(new BigDecimal("2.08"));
        assert invoice.getTotal().equals(new BigDecimal("15.07"));
    }

    @Test
    void computeInvoiceWithTwoOffers() {
        //add 1 x Keyboard, 2 x Monitor to the cart
        //subtotal: $370.97
        //shipping: $128
        //VAT: $70.48
        //Discounts: 10% off keyboards: -$4.09
        //           $10 off shipping: -$10
        //total: $555.36
        service.addProductToCart(2);
        service.addProductToCart(3);
        service.addProductToCart(3);
        Invoice invoice = this.service.getInvoice();
        assert invoice.getSubtotal().equals(new BigDecimal("370.97"));
        assert invoice.getShipping().equals(new BigDecimal("128.00"));
        assert invoice.getDiscounts().stream().map(Discount::getValue).reduce(BigDecimal::add).get()
                .equals(new BigDecimal("14.09"));
        assert invoice.getVat().equals(new BigDecimal("70.48"));
        assert invoice.getTotal().equals(new BigDecimal("555.36"));
    }

    @Test
    void computeInvoiceNoProductInCart() {
        //add 0 products to the cart
        //subtotal: $0.00
        //shipping: $0.00
        //VAT: $0.00
        //no discounts
        //total: $0.00
        Invoice invoice = this.service.getInvoice();
        assert invoice.getSubtotal().stripTrailingZeros().equals(BigDecimal.ZERO);
        assert invoice.getShipping().stripTrailingZeros().equals(BigDecimal.ZERO);
        assert invoice.getDiscounts().size() == 0;
        assert invoice.getVat().stripTrailingZeros().equals(BigDecimal.ZERO);
        assert invoice.getTotal().stripTrailingZeros().equals(BigDecimal.ZERO);
    }

    @Test
    void computeInvoiceWithAllOffers() {
        //add 1 x Keyboard, 2 x Monitor, 2 x Desk Lamp to the cart
        //subtotal: $550.95
        //shipping: $180
        //VAT: $104.68
        //Discounts: 10% off keyboards: -$4.09
        //           $10 off shipping: -$10
        //           1 half-priced lamp: -$44.99
        //total: $776.55
        service.addProductToCart(2);
        service.addProductToCart(3);
        service.addProductToCart(3);
        service.addProductToCart(6);
        service.addProductToCart(6);
        Invoice invoice = this.service.getInvoice();
        assert invoice.getSubtotal().equals(new BigDecimal("550.95"));
        assert invoice.getShipping().equals(new BigDecimal("180.00"));
        assert invoice.getDiscounts().stream().map(Discount::getValue).reduce(BigDecimal::add).get()
                .equals(new BigDecimal("59.08"));
        assert invoice.getVat().equals(new BigDecimal("104.68"));
        assert invoice.getTotal().equals(new BigDecimal("776.55"));
    }
}
