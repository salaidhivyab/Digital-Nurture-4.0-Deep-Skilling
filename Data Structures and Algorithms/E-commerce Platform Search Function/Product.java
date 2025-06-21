public class Product {
    private String productId;
    private String productName;
    private String category;
    private double price;

    public Product(String productId, String productName, String category, double price) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("Product [ID: %s, Name: %s, Category: %s, Price: $%.2f]",
                productId, productName, category, price);
    }
}