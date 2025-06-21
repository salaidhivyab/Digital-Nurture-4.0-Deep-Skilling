public class ECommercePlatform {
    public static void main(String[] args) {
        // Initialize product catalog
        Product[] products = {
            new Product("P100", "Laptop", "Electronics", 999.99),
            new Product("P200", "Smartphone", "Electronics", 699.99),
            new Product("P150", "Headphones", "Electronics", 149.99),
            new Product("P300", "Desk Chair", "Furniture", 199.99),
            new Product("P250", "Keyboard", "Electronics", 79.99)
        };

        System.out.println("=== E-Commerce Platform Search Demo ===");
        System.out.println("Available Products:");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println();

        // Linear Search Demo
        System.out.println("=== Linear Search Results ===");
        String searchTerm = "Headphones";
        Product result = SearchAlgorithms.linearSearchByName(products, searchTerm);
        System.out.println("Searching for '" + searchTerm + "': " + 
                          (result != null ? result : "Not found"));

        searchTerm = "Monitor";
        result = SearchAlgorithms.linearSearchByName(products, searchTerm);
        System.out.println("Searching for '" + searchTerm + "': " + 
                          (result != null ? result : "Not found"));

        // Binary Search Demo (requires sorting first)
        System.out.println("\n=== Binary Search Results ===");
        SearchAlgorithms.sortProductsByName(products);
        System.out.println("Products after sorting by name:");
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println();

        searchTerm = "Desk Chair";
        result = SearchAlgorithms.binarySearchByName(products, searchTerm);
        System.out.println("Searching for '" + searchTerm + "': " + 
                          (result != null ? result : "Not found"));

        searchTerm = "Tablet";
        result = SearchAlgorithms.binarySearchByName(products, searchTerm);
        System.out.println("Searching for '" + searchTerm + "': " + 
                          (result != null ? result : "Not found"));

        // Search by ID
        System.out.println("\n=== Search by Product ID ===");
        String searchId = "P200";
        result = SearchAlgorithms.linearSearchById(products, searchId);
        System.out.println("Searching for ID '" + searchId + "': " + 
                          (result != null ? result : "Not found"));
    }
}