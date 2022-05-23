package controller;

import model.Discount;
import model.Invoice;
import model.Product;
import model.ShoppingCart;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public List<Product> getProducts() {
        return this.service.getProducts();
    }

    public void addProductToCart(Integer productNumber) {
        this.service.addProductToCart(productNumber);
    }

    public ShoppingCart getCart() {
        return this.service.getCart();
    }

    public ArrayList<Discount> getOffers() {
        return this.service.getOffers();
    }

    public Invoice getInvoice() {
        return this.service.getInvoice();
    }
}
