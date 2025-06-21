public class FinancialForecastingDemo {

    // Nested FinancialForecaster class
    static class FinancialForecaster {
        private static double[] memo;
        
        public static double calculateFutureValue(double currentValue, double growthRate, int periods) {
            if (periods <= 0) return currentValue;
            return calculateFutureValue(currentValue * (1 + growthRate), growthRate, periods - 1);
        }
        
        public static double calculateFutureValueMemoized(double currentValue, double growthRate, int periods) {
            if (memo == null || memo.length < periods + 1) {
                memo = new double[periods + 1];
            }
            if (memo[periods] != 0) {
                return memo[periods];
            }
            if (periods == 0) {
                memo[0] = currentValue;
                return currentValue;
            }
            memo[periods] = calculateFutureValueMemoized(currentValue, growthRate, periods - 1) * (1 + growthRate);
            return memo[periods];
        }
        
        public static double calculateFutureValueIterative(double currentValue, double growthRate, int periods) {
            double result = currentValue;
            for (int i = 0; i < periods; i++) {
                result *= (1 + growthRate);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        double initialInvestment = 1000.0; // $1000 initial investment
        double annualGrowthRate = 0.07;    // 7% annual growth
        int years = 10;                    // 10 year projection
        
        // Recursive calculation
        double futureValue = FinancialForecaster.calculateFutureValue(
            initialInvestment, annualGrowthRate, years);
        System.out.printf("Recursive calculation: $%.2f after %d years at %.2f%% growth%n",
                         futureValue, years, annualGrowthRate * 100);
        
        // Memoized recursive calculation
        futureValue = FinancialForecaster.calculateFutureValueMemoized(
            initialInvestment, annualGrowthRate, years);
        System.out.printf("Memoized recursive: $%.2f after %d years%n",
                         futureValue, years);
        
        // Iterative calculation
        futureValue = FinancialForecaster.calculateFutureValueIterative(
            initialInvestment, annualGrowthRate, years);
        System.out.printf("Iterative calculation: $%.2f after %d years%n",
                         futureValue, years);
        
        // Performance comparison
        System.out.println("\nPerformance Comparison:");
        comparePerformance(initialInvestment, annualGrowthRate, years);
    }
    
    private static void comparePerformance(double initialValue, double rate, int periods) {
        // Warm up JVM
        for (int i = 0; i < 1000; i++) {
            FinancialForecaster.calculateFutureValue(initialValue, rate, periods);
            FinancialForecaster.calculateFutureValueMemoized(initialValue, rate, periods);
            FinancialForecaster.calculateFutureValueIterative(initialValue, rate, periods);
        }
        
        // Time recursive version
        long startTime = System.nanoTime();
        FinancialForecaster.calculateFutureValue(initialValue, rate, periods);
        long recursiveTime = System.nanoTime() - startTime;
        
        // Time memoized version
        startTime = System.nanoTime();
        FinancialForecaster.calculateFutureValueMemoized(initialValue, rate, periods);
        long memoizedTime = System.nanoTime() - startTime;
        
        // Time iterative version
        startTime = System.nanoTime();
        FinancialForecaster.calculateFutureValueIterative(initialValue, rate, periods);
        long iterativeTime = System.nanoTime() - startTime;
        
        System.out.printf("Basic recursive: %d ns%n", recursiveTime);
        System.out.printf("Memoized recursive: %d ns%n", memoizedTime);
        System.out.printf("Iterative: %d ns%n", iterativeTime);
    }
}