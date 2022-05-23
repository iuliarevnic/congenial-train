package model;


public class CustomerSession {
    private ShoppingCart shoppingCart;
    private Invoice invoice;

    public CustomerSession() {
        shoppingCart = new ShoppingCart();
        invoice = new Invoice();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

}
