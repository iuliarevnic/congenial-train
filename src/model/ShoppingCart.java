package model;

import java.util.HashMap;

public class ShoppingCart {
    private HashMap<String, Integer> products;

    public ShoppingCart() {
        products = new HashMap<>();
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    /**
     * Adds a product to the cart
     * If the product already exists, the method increases its quantity by 1
     *
     * @param product A product object
     * @return products, which is the content of the updated shopping cart
     */
    public HashMap<String, Integer> addProductToCart(Product product) {
        String productName = product.getName();
        if (products.containsKey(productName)) {
            products.put(productName, products.get(productName) + 1);
        } else {
            products.put(productName, 1);
        }
        return products;
    }
}
