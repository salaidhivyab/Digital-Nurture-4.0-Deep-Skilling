import java.util.Arrays;
import java.util.Comparator;

public class SearchAlgorithms {

    // Linear Search - O(n)
    public static Product linearSearchByName(Product[] products, String targetName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(targetName)) {
                return product;
            }
        }
        return null;
    }

    // Binary Search - O(log n) - requires sorted array
    public static Product binarySearchByName(Product[] sortedProducts, String targetName) {
        int left = 0;
        int right = sortedProducts.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = sortedProducts[mid].getProductName()
                                .compareToIgnoreCase(targetName);

            if (comparison == 0) {
                return sortedProducts[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // Helper method to sort products by name
    public static void sortProductsByName(Product[] products) {
        Arrays.sort(products, Comparator.comparing(Product::getProductName));
    }

    // Additional search by ID using linear search
    public static Product linearSearchById(Product[] products, String targetId) {
        for (Product product : products) {
            if (product.getProductId().equalsIgnoreCase(targetId)) {
                return product;
            }
        }
        return null;
    }
}