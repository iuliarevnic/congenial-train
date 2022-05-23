package service;

import model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Service {
    private StoreInformation storeInformation;
    private CustomerSession customerSession;

    public Service() {
        this.storeInformation = new StoreInformation();
        this.customerSession = new CustomerSession();
    }

    public List<Product> getProducts() {
        return this.storeInformation.getProducts();
    }

    public void addProductToCart(Integer productNumber) {
        Product newProduct = this.storeInformation.getProducts().get(productNumber - 1);
        ShoppingCart shoppingCart = this.customerSession.getShoppingCart();
        shoppingCart.addProductToCart(newProduct);
        this.customerSession.setShoppingCart(shoppingCart);
    }

    public ShoppingCart getCart() {
        return this.customerSession.getShoppingCart();
    }


    public ArrayList<Discount> getOffers() {
        return this.storeInformation.getDiscounts();
    }

    /**
     * Computes the invoice details:
     * - subtotal
     * - shipping
     * - VAT
     * - applied discounts
     * - total
     * based on the current state of the shopping cart
     */
    public void computeInvoice() {
        HashMap<String, Integer> cartProducts = this.customerSession.getShoppingCart().getProducts();
        List<Discount> discounts = this.customerSession.getInvoice().getDiscounts();
        //check to see if there are at least 2 items, required for the $10 off shipping special offer
        if (cartProducts.keySet().size() >= 2 || (cartProducts.keySet().size() == 1 &&
                cartProducts.get(cartProducts.keySet().iterator().next()) >= 2)) {
            discounts.add(new Discount("$10 off shipping for 2 or more items purchased", new BigDecimal(10)));
        }
        //check the number of purchased monitors to know how many half-priced lamps the store can offer
        Integer numberOfHalfPricedDeskLampsOffered = 0;
        if (cartProducts.containsKey("Monitor") && cartProducts.get("Monitor") >= 2) {
            numberOfHalfPricedDeskLampsOffered = cartProducts.get("Monitor") / 2;
        }
        AtomicReference<BigDecimal> subtotal = new AtomicReference<>(new BigDecimal(0));
        AtomicReference<BigDecimal> shipping = new AtomicReference<>(new BigDecimal(0));
        BigDecimal vat = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);


        List<Product> products = this.storeInformation.getProducts();
        Integer finalNumberOfHalfPricedDeskLampsOffered = numberOfHalfPricedDeskLampsOffered;
        //go through each product type and update the subtotal, the shipping cost and the potential discount offers
        cartProducts.forEach((name, quantity) -> {
            Product currentProduct = products.stream().filter(product -> product.getName().equals(name)).findFirst().get();

            //add current product price to subtotal
            BigDecimal productValue = currentProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
            AtomicReference<BigDecimal> productValueAuxiliary = new AtomicReference<>(productValue.add(subtotal.get()));
            subtotal.updateAndGet(v -> productValueAuxiliary.get().setScale(2, RoundingMode.DOWN));

            //add current product fee to shipping
            String productCountry = currentProduct.getCountry();
            BigDecimal countryFee = this.storeInformation.getShippingRates()
                    .stream().filter(product -> product.getCountry().equals(productCountry)).findFirst().get().getFee();
            BigDecimal productFee = currentProduct.getWeight().divide(BigDecimal.valueOf(0.1))
                    .setScale(2, RoundingMode.HALF_UP).multiply(countryFee).multiply(BigDecimal.valueOf(quantity));
            AtomicReference<BigDecimal> productFeeAuxiliary = new AtomicReference<>(productFee.add(shipping.get()));
            shipping.updateAndGet(v -> productFeeAuxiliary.get().setScale(2, RoundingMode.DOWN));

            //apply discounts, if eligible
            if (currentProduct.getName().equals("Keyboard")) {
                discounts.add(new Discount("10% off keyboards",
                        new BigDecimal(0).add(currentProduct.getPrice().multiply(new BigDecimal(0.1))
                                .multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.DOWN))));
            }

            if (currentProduct.getName().equals("Desk Lamp") && finalNumberOfHalfPricedDeskLampsOffered > 0) {
                BigDecimal priceOfADeskLamp = this.storeInformation.getProducts().stream().filter(product -> product.getName()
                        .equals("Desk Lamp")).findFirst().get().getPrice();
                if (quantity <= finalNumberOfHalfPricedDeskLampsOffered) {
                    //discount for entire quantity
                    discounts.add(new Discount("2 x monitors => desk lamp at half price",
                            new BigDecimal(0).add(priceOfADeskLamp.divide(new BigDecimal("2"))
                                    .multiply(new BigDecimal(quantity)
                                            .setScale(2, RoundingMode.DOWN)))));
                } else {
                    //discount for part of the quantity
                    discounts.add(new Discount("2 x monitors => desk lamp at half price",
                            new BigDecimal(0).add(priceOfADeskLamp.divide(new BigDecimal("2")).multiply(
                                    new BigDecimal(finalNumberOfHalfPricedDeskLampsOffered))
                                    .setScale(2, RoundingMode.DOWN))));
                }
            }


        });

        //compute VAT based on subtotal
        vat = vat.add(subtotal.get().multiply(new BigDecimal(0.19)).setScale(2, RoundingMode.DOWN));
        AtomicReference<BigDecimal> sumOfDiscounts = new AtomicReference<>(new BigDecimal(0));
        discounts.forEach(discountType -> {
            AtomicReference<BigDecimal> sumOfDiscountsAuxiliary = new AtomicReference<>(sumOfDiscounts.get()
                    .add(discountType.getValue()));
            sumOfDiscounts.updateAndGet(v -> sumOfDiscountsAuxiliary.get().setScale(2, RoundingMode.DOWN));
        });

        //compute total based on previously computed subtotal, shipping, VAT and discounts
        total = total.add(subtotal.get()).add(shipping.get()).add(vat).subtract(sumOfDiscounts.get())
                .setScale(2, RoundingMode.DOWN);

        Invoice updatedInvoice = new Invoice(subtotal.get(), shipping.get(), vat, (ArrayList<Discount>) discounts, total);
        this.customerSession.setInvoice(updatedInvoice);

    }

    public Invoice getInvoice() {
        this.computeInvoice();
        return this.customerSession.getInvoice();
    }
}
