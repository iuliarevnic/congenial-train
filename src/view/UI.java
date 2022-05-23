package view;

import controller.Controller;
import model.Discount;
import model.Invoice;
import model.Product;
import model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class UI {
    private Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
    }

    public void greetCustomer() {
        System.out.println("\n" +
                "           __      __  __        __\n" +
                "\\        /|   |   |   |  ||\\  /||    |\n" +
                " \\  /\\  / |-- |   |   |  || \\/ ||--  |\n" +
                "  \\/  \\/  |__ |__ |__ |__||    ||__  O\n" +
                "              __           __      __       _          _____     _      __  __  __  O      __\n" +
                "|  | /\\\\    /|      /\\    |  ||   |    /\\  /   /\\ |\\  |  |      /  |  ||  ||  ||  | ||\\  ||  |\n" +
                "|--||__|\\  / |--   |__|   |-- |   |-- |__| \\  |__|| \\ |  |      \\  |--||  ||-- |--  || \\ || _\n" +
                "|  ||  | \\/  |__   |  |   |   |__ |__ |  | __)|  ||  \\|  |      __)|  ||__||   |    ||  \\||__|\n" +
                "                          __      __  __  __  O __       __  __\n" +
                "                         |   \\ / |  ||   |  | ||   |\\  ||   |    |\n" +
                "                         |--  X  |-- |-- |--  ||-- | \\ ||   |--  |\n" +
                "                         |__ / \\ |   |__ |  \\ ||__ |  \\||__ |__  O");
    }

    public void displayOffers() {
        ArrayList<Discount> discounts = this.controller.getOffers();
        System.out.println("\nAvailable special offers:\n");
        discounts.forEach(discount -> System.out.println(discount.getDescription()));
        System.out.println("\n");
    }

    public void displayShoppingCart() {
        ShoppingCart shoppingCart = this.controller.getCart();
        System.out.println("Shopping cart contents\n");
        shoppingCart.getProducts().forEach((name, quantity) -> {
            System.out.println(name + " x " + quantity);
        });
        System.out.println("\n");
    }

    public void showMenu() {
        System.out.println("1. Display list of products");
        System.out.println("2. Display list of offers");
        System.out.println("3. Add product to shopping cart");
        System.out.println("4. Proceed to checkout");
        System.out.println("5. Exit");
        System.out.println("Please select a menu option.\n");
    }

    public void addProductToCart(Integer productNumber) {
        this.controller.addProductToCart(productNumber);
    }

    public void showInvoice() {
        Invoice invoice = this.controller.getInvoice();
        this.displayShoppingCart();
        System.out.println("Invoice\n");
        System.out.println(invoice);

    }

    public void displayProducts() {
        List<Product> products = this.controller.getProducts();
        AtomicReference<Integer> productIndex = new AtomicReference<>(1);
        products.forEach(product -> {
            System.out.println(productIndex.get() + ". " + product.toString());
            productIndex.getAndSet(productIndex.get() + 1);
        });

    }

    public void run() {
        this.greetCustomer();
        this.showMenu();
        Scanner input = new Scanner(System.in);

        Integer menuOption = input.nextInt();
        while (menuOption != 5) {
            switch (menuOption) {
                case 1:
                    this.displayProducts();
                    break;
                case 2:
                    this.displayOffers();
                    break;
                case 3:
                    System.out.println("Give product number:");
                    Integer productNumber = input.nextInt();
                    this.addProductToCart(productNumber);
                    this.displayShoppingCart();
                    break;
                case 4:
                    this.showInvoice();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Please choose a valid menu option.\n");
                    break;
            }
            this.showMenu();
            menuOption = input.nextInt();
        }

    }


}
