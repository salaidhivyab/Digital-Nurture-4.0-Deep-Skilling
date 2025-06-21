public class SearchPerformanceAnalysis {
    public static void analyzePerformance(Product[] products) {
        // Warm up JVM
        for (int i = 0; i < 1000; i++) {
            SearchAlgorithms.linearSearchByName(products, "Laptop");
            SearchAlgorithms.binarySearchByName(products, "Laptop");
        }

        // Linear search timing
        long startTime = System.nanoTime();
        SearchAlgorithms.linearSearchByName(products, "Desk Chair");
        long linearTime = System.nanoTime() - startTime;

        // Binary search timing (requires sorted array)
        SearchAlgorithms.sortProductsByName(products);
        startTime = System.nanoTime();
        SearchAlgorithms.binarySearchByName(products, "Desk Chair");
        long binaryTime = System.nanoTime() - startTime;

        System.out.println("\n=== Performance Analysis ===");
        System.out.println("Linear Search Time: " + linearTime + " ns");
        System.out.println("Binary Search Time: " + binaryTime + " ns");
        System.out.println("Array Size: " + products.length + " products");
        System.out.printf("Binary search was %.1f times faster%n", 
                         (double)linearTime/binaryTime);
    }
}