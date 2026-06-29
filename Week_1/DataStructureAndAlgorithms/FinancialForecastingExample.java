package Week_1.DataStructureAndAlgorithms;

import java.util.HashMap;
import java.util.Map;

public class FinancialForecastingExample {
    public static void main(String[] args) {
        double presentValue = 10000.0;
        double growthRate = 0.08;
        int years = 10;

        System.out.println("=== Naive Recursive Forecast ===");
        for (int year = 0; year <= years; year++) {
            double value = futureValueRecursive(presentValue, growthRate, year);
            System.out.printf("Year %2d: %.2f%n", year, value);
        }

        System.out.println("\n=== Comparing naive vs memoized for a heavier recursive calc ===");
        int n = 40;

        long start1 = System.nanoTime();
        long naive = compoundedGrowthSteps(n);
        long end1 = System.nanoTime();
        System.out.println("Naive recursive result for n=" + n + ": " + naive
                + " (took " + (end1 - start1) / 1_000_000 + " ms)");

        Map<Integer, Long> memo = new HashMap<>();
        long start2 = System.nanoTime();
        long optimized = compoundedGrowthStepsMemoized(n, memo);
        long end2 = System.nanoTime();
        System.out.println("Memoized recursive result for n=" + n + ": " + optimized
                + " (took " + (end2 - start2) / 1_000_000 + " ms)");
    }

    static double futureValueRecursive(double presentValue, double growthRate, int year) {
        if (year == 0) {
            return presentValue;
        }
        return futureValueRecursive(presentValue, growthRate, year - 1) * (1 + growthRate);
    }

    static long compoundedGrowthSteps(int n) {
        if (n <= 1) return n;
        return compoundedGrowthSteps(n - 1) + compoundedGrowthSteps(n - 2);
    }

    static long compoundedGrowthStepsMemoized(int n, Map<Integer, Long> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long result = compoundedGrowthStepsMemoized(n - 1, memo)
                + compoundedGrowthStepsMemoized(n - 2, memo);
        memo.put(n, result);
        return result;
    }
}
